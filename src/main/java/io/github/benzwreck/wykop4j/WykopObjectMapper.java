package io.github.benzwreck.wykop4j;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.github.benzwreck.wykop4j.exceptions.ArchivalContentException;
import io.github.benzwreck.wykop4j.exceptions.LimitExceededException;
import io.github.benzwreck.wykop4j.exceptions.UnableToDeleteCommentException;
import io.github.benzwreck.wykop4j.exceptions.UnableToModifyEntryException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                .registerModule(new ProfileMappingModule());
    }

    public <T> T map(String payload, Class<T> clazz) {
        try {
            JsonNode jsonNode = objectMapper.readTree(payload);
            JsonNode data = handleResponse(jsonNode);
            return objectMapper.readValue(objectMapper.treeAsTokens(data), clazz);
        } catch (IOException e) {
            throw new WykopException(0, e.getMessage(), e.getMessage()); //todo magic numbers
        }
    }

    public <T> T map(String payload, TypeReference<T> typeReference) {
        try {
            //todo: proper response handling -> data, error
            JsonNode jsonNode = objectMapper.readTree(payload);
            JsonNode node = handleResponse(jsonNode);
            return objectMapper.readValue(objectMapper.treeAsTokens(node), typeReference);
        } catch (IOException e) {
            throw new WykopException(0, e.getMessage(), e.getMessage()); //todo magic numbers
        }
    }

    private JsonNode handleResponse(JsonNode node) {
        int entryDoesNotExistCode = 61;
        if (node.hasNonNull("error")) {
            if (node.get("error").get("code").intValue() == entryDoesNotExistCode)
                return node.get("data");
        }
        if (node.hasNonNull("data")) {
            return node.get("data");
        }
        JsonNode error = node.get("error");
        int errorCode = error.get("code").intValue();
        String messageEn = error.get("message_en").asText();
        String messagePl = error.get("message_pl").asText();
        switch (errorCode) {
            case 552:
            case 24:
                throw new ArchivalContentException(errorCode, messageEn, messagePl);
            case 35:
                throw new UnableToModifyEntryException(errorCode, messageEn, messagePl);
            case 37:
                throw new UnableToDeleteCommentException(errorCode, messageEn, messagePl);
            case 506:
                throw new LimitExceededException(errorCode, messageEn, messagePl);
        }
        throw new WykopException(errorCode, messageEn, messagePl);
    }
}