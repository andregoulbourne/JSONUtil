package com.andre.util;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import com.andre.json.annotations.JSON;
import com.andre.json.annotations.JSONField;

public class MetaModel<T> {

    private Class<T> clazz;
    private List<com.andre.util.JSONField> jsonFields;

    public static <T> MetaModel<T> of(Class<T> clazz) {
        if (clazz.getAnnotation(JSON.class) == null) {
            throw new IllegalStateException("Cannot create MetaModel object! Provided class, " + clazz.getName() + "is not annotated with @Entity");
        }
        return new MetaModel<>(clazz);
    }

    public MetaModel(Class<T> clazz) {
        this.clazz = clazz;
        this.jsonFields = new LinkedList<>();
    }

    public String getClassName() {
        return clazz.getName();
    }

    public String getSimpleClassName() {
        return clazz.getSimpleName();
    }

    public List<com.andre.util.JSONField> getJSONFields() {

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
        	JSONField column = field.getAnnotation(JSONField.class);
            if (column != null) {
                jsonFields.add(new com.andre.util.JSONField(field));
            }
        }

        if (jsonFields.isEmpty()) {
            throw new IllegalStateException("No columns found in: " + clazz.getName());
        }

        return jsonFields;
    }
    
}
