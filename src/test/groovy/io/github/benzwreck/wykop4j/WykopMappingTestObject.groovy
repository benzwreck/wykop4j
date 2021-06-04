package io.github.benzwreck.wykop4j

import com.fasterxml.jackson.core.type.TypeReference

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class WykopMappingTestObject {
    ExecutorService executorService = Executors.newCachedThreadPool()
    WykopObjectMapper wykopObjectMapper = new WykopObjectMapper(executorService)

    def <T> T map(String json, Class<T> clazz){
        return wykopObjectMapper.map(json, clazz)
    }
    def <T> T map(String json, TypeReference<T> typeReference){
        return wykopObjectMapper.map(json, typeReference)
    }
}
