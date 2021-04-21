package com.dev.assessment.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;


public class SerDeUtil {

    public static <T> T readAsObject(String fileName, Class<T> type) throws IOException{
        T result = null;
        try(InputStream resource = type.getClassLoader().getResourceAsStream(fileName)){
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(resource, type);
        }
        return result;
    }

    public static String writeAsString(Object obj) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
