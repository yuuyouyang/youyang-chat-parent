package com.nf.yy.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

/**
 * 基于Jackson的JSON转换工具类
 * @author smile
 */
public class JsonUtils {

    /**
     * json转换工具
     */
    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 该特性决定了当遇到未知属性（没有映射到属性，没有任何setter或者任何可以处理它的handler），是否应该抛出一个JsonMappingException异常。
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 传入一个对象转化为json字符串并写入字节输出流
     */
    public static void write(OutputStream outputStream, Object param) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, param);
    }

    /**
     * 将对象转为json字符串
     */
    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * 将json字符串反序列化为一个Bean对象
     */
    public static <T> T toBean(String jsonString, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, valueType);
    }

    /**
     * 将json字符串数组反序列化为一个List对象
     */
    public static <T> List<T> toList(String jsonString, Class<T> valueType) throws JsonProcessingException {
        JavaType javaType = getCollectionType(ArrayList.class, valueType);
        return objectMapper.readValue(jsonString, javaType);
    }

    /**
     * 获取泛型的Collection Type
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
