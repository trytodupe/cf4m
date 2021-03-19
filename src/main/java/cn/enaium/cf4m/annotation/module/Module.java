package cn.enaium.cf4m.annotation.module;

import cn.enaium.cf4m.module.Category;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Project: cf4m
 * Author: Enaium
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {
    String value();

    boolean enable() default false;

    int key() default 0;

    Category category() default Category.NONE;

    String description() default "";
}
