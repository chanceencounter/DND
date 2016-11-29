package com.ericarao.dnd.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@Value.Style(
        get = {"is*", "get*"},
        init = "set*",
        typeAbstract = {"Abstract*", "*IF"},
        typeImmutable = "*",
        optionalAcceptNullable = true,
        forceJacksonPropertyNames = false,
        visibility = Value.Style.ImplementationVisibility.SAME)
@JsonSerialize
public @interface DndStyle {
}
