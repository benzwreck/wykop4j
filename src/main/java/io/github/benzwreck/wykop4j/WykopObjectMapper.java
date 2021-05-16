package io.github.benzwreck.wykop4j;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.github.benzwreck.wykop4j.exceptions.ActionForbiddenException;
import io.github.benzwreck.wykop4j.exceptions.ArchivalContentException;
import io.github.benzwreck.wykop4j.exceptions.AuthorizationException;
import io.github.benzwreck.wykop4j.exceptions.BodyContainsOnlyPmException;
import io.github.benzwreck.wykop4j.exceptions.CannotEditCommentsWithAnswerException;
import io.github.benzwreck.wykop4j.exceptions.CannotReplyOnDeletedObjectsException;
import io.github.benzwreck.wykop4j.exceptions.CommentDoesNotExistException;
import io.github.benzwreck.wykop4j.exceptions.DailyRequestLimitExceededException;
import io.github.benzwreck.wykop4j.exceptions.InvalidAPIKeyException;
import io.github.benzwreck.wykop4j.exceptions.InvalidUserCredentialsException;
import io.github.benzwreck.wykop4j.exceptions.InvalidValueException;
import io.github.benzwreck.wykop4j.exceptions.LimitExceededException;
import io.github.benzwreck.wykop4j.exceptions.LinkAlreadyExistsException;
import io.github.benzwreck.wykop4j.exceptions.LinkCommentNotExistException;
import io.github.benzwreck.wykop4j.exceptions.NiceTryException;
import io.github.benzwreck.wykop4j.exceptions.UnableToDeleteCommentException;
import io.github.benzwreck.wykop4j.exceptions.UnableToModifyEntryException;
import io.github.benzwreck.wykop4j.exceptions.UserBlockedByAnotherUserException;
import io.github.benzwreck.wykop4j.exceptions.UserCannotObserveThemselfException;
import io.github.benzwreck.wykop4j.exceptions.UserNotFoundException;
import io.github.benzwreck.wykop4j.exceptions.WykopException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class WykopObjectMapper {
    private final ObjectMapper objectMapper;

    public WykopObjectMapper() {
        LocalDateTimeDeserializer localDateTimeDeserializer =
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        SimpleModule javaTimeModule = new JavaTimeModule()
                .addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
                .registerModule(new Jdk8Module())
                .registerModule(javaTimeModule)
                .registerModule(new EntryMappingModule())
                .registerModule(new ProfileMappingModule())
                .registerModule(new ConversationMessageModule())
                .registerModule(new LinkInfoColorModule())
                .registerModule(new ProfileActionsModule())
                .registerModule(new RelatedLinkVoteDataModule())
                .registerModule(new LinkDraftMappingModule());
    }

    @SuppressWarnings("unchecked")
    public <T> T map(String payload, Class<T> clazz) {
        try {
            if (isWykopConnectLoginHtmlPage(payload)) {
                return (T) objectMapper.writeValueAsString(payload);
            }
            JsonNode data = handleResponse(payload);
            return objectMapper.readValue(objectMapper.treeAsTokens(data), clazz);
        } catch (IOException e) {
            throw new WykopException(0, e.getMessage(), e.getMessage()); //todo magic numbers
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T map(String payload, TypeReference<T> typeReference) {
        try {
            payload = handleNotFoundAsEmptyResponse(payload);
            payload = handleLinkPrepareImageResponse(payload);
            JsonNode node = handleResponse(payload);
            T t = objectMapper.readValue(objectMapper.treeAsTokens(node), typeReference);
            if (t instanceof List) {
                return (T) Collections.unmodifiableList((List<T>) t);
            }
            return t;
        } catch (IOException e) {
            throw new WykopException(0, e.getMessage(), e.getMessage()); //todo magic numbers
        }
    }

    private JsonNode handleResponse(String payload) throws JsonProcessingException {
        payload = handleServerErrorHtmlResponse(payload);
        payload = handleWykopConnectResponse(payload);
        if (conversationDeletedOrLinksFavoriteToggle(payload)) {
            return BooleanNode.valueOf(true);
        }
        JsonNode node = objectMapper.readTree(payload);
        if (node.hasNonNull("error")) {
            if (emptyEntryResponse(node))
                return node.get("data");
        }
        if (node.hasNonNull("duplicates")) {
            return node;
        }
        if (node.hasNonNull("data")) {
            JsonNode data = node.get("data");
            data = handleUserFavorite(data);
            data = handleNotificationCount(data);
            handleNotificationIndex(data);
            return data;
        }
        JsonNode error = node.get("error");
        int errorCode = error.get("code").intValue();
        switch (errorCode) {
            case 1:
                throw new InvalidAPIKeyException();
            case 5:
                throw new DailyRequestLimitExceededException();
            case 7:
                throw new AuthorizationException();
            case 13:
                throw new UserNotFoundException();
            case 14:
                throw new InvalidUserCredentialsException();
            case 24:
                throw new ArchivalContentException();
            case 33:
                throw new UserCannotObserveThemselfException();
            case 35:
                throw new UnableToModifyEntryException();
            case 37:
                throw new UnableToDeleteCommentException();
            case 81:
                throw new CommentDoesNotExistException();
            case 102:
                throw new UserBlockedByAnotherUserException();
            case 506:
                throw new LimitExceededException();
            case 515:
                throw new LinkCommentNotExistException();
            case 517:
                throw new CannotReplyOnDeletedObjectsException();
            case 522:
                throw new LinkAlreadyExistsException();
            case 529:
                throw new CannotEditCommentsWithAnswerException();
            case 530:
                throw new InvalidValueException();
            case 552:
                throw new ActionForbiddenException();
            case 602:
                throw new BodyContainsOnlyPmException();
            case 999:
                throw new NiceTryException();
        }
        String messageEn = error.get("message_en").asText();
        String messagePl = error.get("message_pl").asText();
        throw new WykopException(errorCode, messageEn, messagePl);
    }

    private String handleWykopConnectResponse(String payload) {
        if (payload.contains("{\"appkey\":")) {
            return "{\"data\":" + payload + "}";
        }
        return payload;
    }

    private boolean isWykopConnectLoginHtmlPage(String payload) {
        return payload.contains("<title>Połącz konto z");
    }

    private String handleNotFoundAsEmptyResponse(String payload) {
        if (payload.startsWith("{\"data\":null,\"error\":{\"code\":13")
                || payload.startsWith("{\"data\":null,\"error\":{\"code\":41")) {
            return "{\"data\":[]}";
        }
        return payload;
    }

    private String handleLinkPrepareImageResponse(String payload) {
        if (payload.startsWith("{\"data\":[{\"key\":")) {
            return payload.replace("{\"data\":[{\"key\":", "{\"data\":{\"key\":")
                    .replace("}]}", "}}");
        }
        return payload;
    }

    private boolean conversationDeletedOrLinksFavoriteToggle(String payload) {
        return payload.equals("{\"data\":[true]}");
    }

    private String handleServerErrorHtmlResponse(String payload) {
        if (payload.contains("<title>Ups...</title>")) {
            return "{\"data\": []}";
        }
        return payload;
    }

    private JsonNode handleNotificationCount(JsonNode data) {
        if (data.hasNonNull("count")) {
            int value = 0;
            if (data.hasNonNull("hastagcount")) {
                value += data.get("hastagcount").intValue();
            }
            value += data.get("count").intValue();
            data = IntNode.valueOf(value);
        }
        return data;
    }

    private boolean emptyEntryResponse(JsonNode node) {
        return node.get("error").get("code").intValue() == 61;
    }

    private void handleNotificationIndex(JsonNode data) {
        Iterator<JsonNode> iterator = data.elements();
        while (iterator.hasNext()) {
            JsonNode next = iterator.next();
            if (!next.hasNonNull("new")) break;
            ((ObjectNode) next).replace("isNew", BooleanNode.valueOf(next.get("new").asBoolean()));
            ((ObjectNode) next).remove("new");
        }
    }

    private JsonNode handleUserFavorite(JsonNode data) {
        if (data.hasNonNull("user_favorite") && data.size() == 1) {
            data = BooleanNode.valueOf(data.get("user_favorite").booleanValue());
        }
        return data;
    }
}