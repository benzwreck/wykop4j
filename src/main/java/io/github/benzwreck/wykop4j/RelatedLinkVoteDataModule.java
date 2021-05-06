package io.github.benzwreck.wykop4j;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.benzwreck.wykop4j.links.RelatedLinkVoteData;

import java.io.IOException;

class RelatedLinkVoteDataModule extends SimpleModule {

    RelatedLinkVoteDataModule() {
        this.addDeserializer(RelatedLinkVoteData.class, new RelatedLinkVoteDataDeserializer());
    }

    private class RelatedLinkVoteDataDeserializer extends JsonDeserializer<RelatedLinkVoteData> {

        @Override
        public RelatedLinkVoteData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            int value = node.get("vote_count").intValue();
            return new RelatedLinkVoteData(value);
        }
    }
}
