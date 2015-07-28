package com.buswe.base.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.internal.ws.processor.model.java.JavaType;
import com.sun.xml.internal.ws.developer.SerializationFeature;

public class JsonMapper
{
  private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);
  private ObjectMapper mapper;
  
  public JsonMapper()
  {
    this(null);
  }
  
  public JsonMapper(JsonSerialize.Inclusion  include)
  {
    this.mapper = new ObjectMapper();
    if (include != null) {
      this.mapper.setSerializationInclusion(include);
    }
    this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }
  
  public static JsonMapper nonEmptyMapper()
  {
    return new JsonMapper(JsonInclude.Include.NON_EMPTY);
  }
  
  public static JsonMapper nonDefaultMapper()
  {
    return new JsonMapper(JsonInclude.Include.NON_DEFAULT);
  }
  
  public String toJson(Object object)
  {
    try
    {
      return this.mapper.writeValueAsString(object);
    }
    catch (IOException e)
    {
      logger.warn("write to json string error:" + object, e);
    }
    return null;
  }
  
  public <T> T fromJson(String jsonString, Class<T> clazz)
  {
    if (StringUtils.isEmpty(jsonString)) {
      return null;
    }
    try
    {
      return this.mapper.readValue(jsonString, clazz);
    }
    catch (IOException e)
    {
      logger.warn("parse json string error:" + jsonString, e);
    }
    return null;
  }
  
  public <T> T fromJson(String jsonString, JavaType javaType)
  {
    if (StringUtils.isEmpty(jsonString)) {
      return null;
    }
    try
    {
      return this.mapper.readValue(jsonString, javaType);
    }
    catch (IOException e)
    {
      logger.warn("parse json string error:" + jsonString, e);
    }
    return null;
  }
  
  public JavaType contructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass)
  {
    return this.mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
  }
  
  public JavaType contructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass)
  {
    return this.mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
  }
  
  public <T> T update(String jsonString, T object)
  {
    try
    {
      return this.mapper.readerForUpdating(object).readValue(jsonString);
    }
    catch (JsonProcessingException e)
    {
      logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
    }
    catch (IOException e)
    {
      logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
    }
    return null;
  }
  
  public String toJsonP(String functionName, Object object)
  {
    return toJson(new JSONPObject(functionName, object));
  }
  
  public void enableEnumUseToString()
  {
    this.mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    this.mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
  }
  
  public void enableJaxbAnnotation()
  {
    JaxbAnnotationModule module = new JaxbAnnotationModule();
    this.mapper.registerModule(module);
  }
  
  public ObjectMapper getMapper()
  {
    return this.mapper;
  }
}
