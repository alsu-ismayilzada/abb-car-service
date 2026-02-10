package com.abbtech.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface SpringTransactionAnnotation {

    String value() default "";
}
