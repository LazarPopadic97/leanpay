package org.leanpay.project;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
public class TestUtils {



    public static <T> T readJson(Class<T> type, String path) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readerFor(type).readValue(TestUtils.class.getResourceAsStream(path));
        } catch (Exception e) {
            log.error("I/O exception parsing, path: {} ", path);
            throw new IllegalStateException(e);
        }
    }

}
