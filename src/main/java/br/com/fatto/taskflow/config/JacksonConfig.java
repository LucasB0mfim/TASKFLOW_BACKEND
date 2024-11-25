package br.com.fatto.taskflow.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe de configuração para personalizar o comportamento do ObjectMapper.
 * Aqui é feita a serialização e deserialização de datas no formato "dd/MM/yyyy".
 * 
 * @author Lucas Bomfim
 */

@Configuration
public class JacksonConfig {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        // Serializa LocalDate no formato definido.
        module.addSerializer(LocalDate.class, new LocalDateSerializer(FORMATTER));
        
        // Desserializa LocalDate no formato definido.
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(FORMATTER));

        // Registra o módulo no ObjectMapper.
        mapper.registerModule(module);
        return mapper;
    }
}