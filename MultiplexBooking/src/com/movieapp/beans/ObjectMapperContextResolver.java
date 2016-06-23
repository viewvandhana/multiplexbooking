package com.movieapp.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper>  {

    final ObjectMapper mapper = new ObjectMapper();

    public ObjectMapperContextResolver() {
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }  
}