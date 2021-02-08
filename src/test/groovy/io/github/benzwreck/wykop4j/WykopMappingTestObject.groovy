package io.github.benzwreck.wykop4j

import com.fasterxml.jackson.core.type.TypeReference

class WykopMappingTestObject {
    WykopObjectMapper wykopObjectMapper = new WykopObjectMapper()

    def <T> T map(String json, Class<T> clazz){
        return wykopObjectMapper.map(json, clazz)
    }
    def <T> T map(String json, TypeReference<T> typeReference){
        return wykopObjectMapper.map(json, typeReference)
    }
}
