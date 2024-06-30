package com.andre.json.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Target(ElementType.TYPE) // for class
@Target(ElementType.FIELD)
@Retention (RetentionPolicy.RUNTIME)
public @interface JSONField {
	String jsonFieldName();
	boolean isEnrichment() default false;
}
