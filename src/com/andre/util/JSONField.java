package com.andre.util;

import java.lang.reflect.Field;

public class JSONField {

    private Field field;

    public JSONField(Field field) {
        if (field.getAnnotation(com.andre.json.annotations.JSONField.class) == null) {
            throw new IllegalStateException("Cannot create ColumnField object! Provided field, " + getName() + "is not annotated with @Column");
        }
        this.field = field;
    }

    public String getName() {
        return field.getName();
    }

    public Class<?> getType() {
        return field.getType();
    }

    public String getJsonFieldName() {
        return field.getAnnotation(com.andre.json.annotations.JSONField.class).jsonFieldName();
    }
    
    public boolean isEnrichment() {
    	return field.getAnnotation(com.andre.json.annotations.JSONField.class).isEnrichment();
    }

}
