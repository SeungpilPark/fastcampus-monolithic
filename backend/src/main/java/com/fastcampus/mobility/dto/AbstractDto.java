package com.fastcampus.mobility.dto;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractDto implements Serializable {

  private static final long serialVersionUID = 1L;


  public static <T extends AbstractDto> T fromEntity(final Class<T> type, final Object entity) {
    ObjectMapper objectMapper = createObjectMapper();
    try {
      String json = objectMapper.writeValueAsString(entity);
      return objectMapper.readValue(json, type);
    } catch (IOException jpe) {
      throw new IllegalArgumentException(jpe);
    }
  }

  public static <T extends AbstractDto> List<T> fromListEntities(final Class<T> dtoType,
      final List<?> entities) {
    return entities.stream().sequential()
        .map(entity -> AbstractDto.fromEntity(dtoType, entity))
        .collect(Collectors.toList());
  }

  private static ObjectMapper createObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return objectMapper;
  }
}
