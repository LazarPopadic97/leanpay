package org.leanpay.project.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.leanpay.project.entity.TotalPayment;
import org.postgresql.util.PGobject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableJdbcAuditing
public class JdbcConfig {

    @Bean
    public JdbcCustomConversions jdbcCustomConversions(ObjectMapper objectMapper) {
        return new JdbcCustomConversions(List.of(
            new PgJsonToObjectConverter<TotalPayment>(objectMapper){},
            new ObjectToPgJsonConverter<TotalPayment>(objectMapper){}
        ));
    }

    @RequiredArgsConstructor
    @ReadingConverter
    public abstract static class PgJsonToObjectConverter<T> extends TypeReference<T> implements Converter<PGobject, T> {

        private final ObjectMapper objectMapper;


        @Override
        @SneakyThrows
        public T convert(PGobject source) {
            return objectMapper.readValue(source.getValue(), this);
        }

    }

    @RequiredArgsConstructor
    @WritingConverter
    public abstract static class ObjectToPgJsonConverter<T> implements Converter<T, PGobject> {

        private final ObjectMapper objectMapper;


        @Override
        @SneakyThrows
        public PGobject convert(T source) {
            PGobject pGobject = new PGobject();
            pGobject.setType("json");
            pGobject.setValue(objectMapper.writeValueAsString(source));
            return pGobject;
        }

    }
}
