package com.hl.erp.service;

import java.lang.annotation.*;

/**
 * @author lyuguohua 華隆ERP 2018-10-7 15:25:39
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ResourceInfo {
    String value();
}