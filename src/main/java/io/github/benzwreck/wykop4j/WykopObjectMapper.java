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
            return objectMapper.readValue(objectMapper.treeAsTokens(jsonNode.get("data")), clazz);
        } catch (IOException e) {
            throw new WykopException(e);
        }
    }

    public <T> T map(String payload, TypeReference<T> typeReference) {
        try {
            //todo: proper response handling -> data, error
            JsonNode jsonNode = objectMapper.readTree(payload);
            JsonNode node = jsonNode.get("data");
            return objectMapper.readValue(objectMapper.treeAsTokens(node), typeReference);
        } catch (IOException e) {
            throw new WykopException(e);
        }
    }
}