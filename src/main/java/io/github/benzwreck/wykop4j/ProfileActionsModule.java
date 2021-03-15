package io.github.benzwreck.wykop4j;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.benzwreck.wykop4j.entries.Entry;
import io.github.benzwreck.wykop4j.links.Link;
import io.github.benzwreck.wykop4j.profiles.Actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ProfileActionsModule extends SimpleModule {

    ProfileActionsModule() {
        this.addDeserializer(Actions.class, new ProfileActionsDeserializer());
    }

    private class ProfileActionsDeserializer extends JsonDeserializer<Actions> {

        @Override
        public Actions deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            ArrayNode nodes = p.getCodec().readTree(p);
            List<Entry> entries = new ArrayList<>();
            List<Link> links = new ArrayList<>();
            for (JsonNode node : nodes) {
                String type = node.get("type").textValue();
                if ("entry".equals(type)) {
                    JsonNode entryNode = node.get("entry");
                    JsonParser entryParser = entryNode.traverse(p.getCodec());
                    entryParser.nextToken();
                    Entry entry = ctxt.readValue(entryParser, Entry.class);
                    entries.add(entry);
                } else if ("link".equals(type)) {
                    JsonNode linkNode = node.get("link");
                    JsonParser linkParser = linkNode.traverse(p.getCodec());
                    linkParser.nextToken();
                    Link link = ctxt.readValue(linkParser, Link.class);
                    links.add(link);
                }
            }
            return new Actions(entries, links);
        }
    }
}
