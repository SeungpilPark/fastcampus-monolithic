package com.fastcampus.mobility.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public abstract class AbstractDto implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final String CUSTOM_PROPERTIES_NAME_FILTER = "customPropertiesNameFilter";
  private final String[] ignoredProperties = {"createDate", "updateDate"};

  public <T> T createNewEntity(final Class<T> entityType) {
    T entityInstance = BeanUtils.instantiateClass(entityType);
    copyToEntity(entityInstance);
    return entityInstance;
  }

  public <T> void copyToEntity(final T entity) {
    ObjectMapper objectMapper = createObjectMapper();
    objectMapper.addMixIn(Object.class, PropertyFilterMixIn.class);
    FilterProvider filters
        = new SimpleFilterProvider().addFilter(
        CUSTOM_PROPERTIES_NAME_FILTER,
        SimpleBeanPropertyFilter.serializeAllExcept(ignoredProperties));
    try {
      String json = objectMapper.writer(filters).writeValueAsString(this);
      objectMapper.readerForUpdating(entity).readValue(json);
    } catch (IOException ioe) {
      throw new IllegalArgumentException(ioe);
    }
  }

  @JsonFilter(CUSTOM_PROPERTIES_NAME_FILTER)
  private static class PropertyFilterMixIn {
    /* nothing to do */
  }

  public static <T extends AbstractDto> T fromEntity(final Class<T> type, final Object entity) {
    ObjectMapper objectMapper = createObjectMapper();
    try {
      String json = objectMapper.writeValueAsString(entity);
      return objectMapper.readValue(json, type);
    } catch (IOException jpe) {
      throw new IllegalArgumentException(jpe);
    }
  }

  public static <T extends AbstractDto> Page<T> fromPageEntities(final Class<T> dtoType,
      final Page<?> page) {
    List<T> dtoList = fromListEntities(dtoType, page.getContent());
    return new PageImpl<>(dtoList, page.getPageable(), page.getTotalElements());
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
