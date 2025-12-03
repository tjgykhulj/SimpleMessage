package com.arnold.msg;

import com.arnold.msg.exceptions.JsonRuntimeException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

@Slf4j
public class JsonUtils {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
    }

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

    public static Map<String, String> fromJsonToMap(String json) {
        try {
            return MAPPER.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            log.warn("Failed to deserialize JSON to map: {}", json, e);
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

    public static <T> T fromMapToObj(Map<String, String> map, Class<T> clazz) {
        try {
            return MAPPER.convertValue(map != null ? map : Collections.emptyMap(), clazz);
        } catch (Exception e) {
            log.debug("Failed to convert map to object", e);
            throw new JsonRuntimeException("convertValue from map to object failed", e);
        }
    }

    public static Map<String, String> fromObjToMap(Object obj) {
        try {
            return MAPPER.convertValue(obj, new TypeReference<>() {});
        } catch (Exception e) {
            log.debug("Failed to convert map to object", e);
            throw new JsonRuntimeException("convertValue from map to object failed", e);
        }
    }
}
