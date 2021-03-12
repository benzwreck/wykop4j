package io.github.benzwreck.wykop4j;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.benzwreck.wykop4j.links.Link;

import java.io.IOException;

class LinkInfoColorModule extends SimpleModule {

    LinkInfoColorModule() {
        this.addDeserializer(Link.Info.Color.class, new LinkInfoColorDeserializer());
    }

    private class LinkInfoColorDeserializer extends JsonDeserializer<Link.Info.Color> {

        @Override
        public Link.Info.Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = p.getCodec().readTree(p);
            String color = node.textValue().toLowerCase();
            switch (color) {
                case "red":
                    return Link.Info.Color.RED;
                case "yellow":
                    return Link.Info.Color.YELLOW;
                case "green":
                    return Link.Info.Color.GREEN;
                default:
                    return Link.Info.Color.UNDEFINED;
            }
        }
    }
}
