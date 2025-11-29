package com.arnold.msg;

import com.arnold.msg.exceptions.JsonRuntimeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Failed to serialize object to JSON: {}", obj, e);
            throw new JsonRuntimeException("JSON serialization failed", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            log.warn("Failed to deserialize JSON to object: {}", json, e);
            throw new JsonRuntimeException("JSON deserialization failed", e);
        }
    }

    public static byte[] toJsonBytes(Object obj) {
        try {
            return MAPPER.writeValueAsBytes(obj);
        } catch (Exception e) {
            log.warn("Failed to serialize object to JSON bytes: {}", obj, e);
            throw new JsonRuntimeException("JSON bytes serialization failed", e);
        }
    }

    public static <T> T fromJsonBytes(byte[] data, Class<T> clazz) {
        try {
            return MAPPER.readValue(data, clazz);
        } catch (Exception e) {
            log.debug("Failed to deserialize JSON bytes to object", e);
            throw new JsonRuntimeException("JSON bytes deserialization failed", e);
        }
    }

}
