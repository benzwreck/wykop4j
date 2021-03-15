package io.github.benzwreck.wykop4j;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.benzwreck.wykop4j.profiles.Color;
import io.github.benzwreck.wykop4j.profiles.Sex;

import java.io.IOException;

class ProfileMappingModule extends SimpleModule {

    public ProfileMappingModule() {
        this.addDeserializer(Sex.class, new ProfileSexDeserializer());
        this.addDeserializer(Color.class, new ProfileColorDeserializer());
    }

    private class ProfileSexDeserializer extends JsonDeserializer<Sex> {
        @Override
        public Sex deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            return node.textValue().equals("male")
                    ? Sex.MALE
                    : Sex.FEMALE;
        }
    }

    private class ProfileColorDeserializer extends JsonDeserializer<Color> {
        @Override
        public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            int value = node.intValue();
            switch (value) {
                case 0:
                    return Color.GREEN;
                case 1:
                case 997:
                    return Color.ORANGE;
                case 2:
                    return Color.CLARET;
                case 5:
                    return Color.ADMIN;
                case 998:
                    return Color.PLAY;
                case 999:
                    return Color.GOLD;
                case 1001:
                    return Color.BANNED;
                case 1002:
                    return Color.DELETED;
                case 2001:
                    return Color.CLIENT;
                default:
                    return Color.UNDEFINED;
            }
        }
    }
}
