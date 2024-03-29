package io.github.benzwreck.wykop4j;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.benzwreck.wykop4j.links.Link;
import io.github.benzwreck.wykop4j.links.LinkDraft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class LinkDraftMappingModule extends SimpleModule {

    LinkDraftMappingModule() {
        this.addDeserializer(LinkDraft.class, new LinkDraftDeserializer());
    }

    private class LinkDraftDeserializer extends JsonDeserializer<LinkDraft> {

        @Override
        public LinkDraft deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            ObjectNode nodes = p.getCodec().readTree(p);
            JsonNode data = nodes.hasNonNull("data") ? nodes.get("data") : nodes;
            String key = data.get("key").asText();
            String title = data.hasNonNull("title") ? data.get("title").asText() : null;
            String description = data.hasNonNull("description") ? data.get("description").asText() : null;
            String tags = data.hasNonNull("tags") ? data.get("tags").asText() : null;
            String sourceUrl = data.get("source_url").asText();
            List<Link> duplicates = new ArrayList<>();
            if (nodes.hasNonNull("duplicates")) {
                for (Iterator<JsonNode> it = nodes.get("duplicates").elements(); it.hasNext(); ) {
                    JsonNode node = it.next();
                    JsonParser parser = node.traverse(p.getCodec());
                    parser.nextToken();
                    Link link = ctxt.readValue(parser, Link.class);
                    duplicates.add(link);
                }
            }
            return new LinkDraft(key, sourceUrl, title, description, tags, duplicates);
        }
    }
}
