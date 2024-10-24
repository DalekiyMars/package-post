package com.e_mail.item_post.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);

    private static final ObjectReader READER = MAPPER.reader();
    private static final ObjectWriter WRITER = MAPPER.writerWithDefaultPrettyPrinter();


    public static <T> String convertJsonFromFileToString(String filePath, Class<T> clazz) throws IOException {
        return WRITER.writeValueAsString(convertJsonFromFileToObject(filePath,clazz));
    }

    public static <T> T convertJsonFromFileToObject(String filePath, Class<T> clazz) throws IOException {
        return READER.readValue(new File(filePath), clazz);
    }

    public static <T> String convertJsonFromObjectToString(T object) throws IOException {
        return WRITER.writeValueAsString(object);
    }
}
