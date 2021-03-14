package io.github.benzwreck.wykop4j;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.benzwreck.wykop4j.shared.UserVote;

import java.io.IOException;

class EntryMappingModule extends SimpleModule {

    public EntryMappingModule(){
        this.addDeserializer(UserVote.class, new UserVoteDeserializer());
    }
    private class UserVoteDeserializer extends JsonDeserializer<UserVote>{

        @Override
        public UserVote deserialize(JsonParser p, DeserializationContext ctxt) throws IOException{
            JsonNode node = p.getCodec().readTree(p);
            return node.intValue() == 0
                    ? UserVote.NOT_VOTED
                    : UserVote.VOTED;
        }
    }
}
