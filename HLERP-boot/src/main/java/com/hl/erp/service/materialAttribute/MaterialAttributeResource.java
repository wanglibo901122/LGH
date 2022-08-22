package com.hl.erp.service.materialAttribute;

import com.hl.erp.service.ResourceInfo;

import java.lang.annotation.*;

/**
 * @author lyuguohua 華隆ERP  2021-07-21 22:26:27
 */
@ResourceInfo(value = "materialAttribute")
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaterialAttributeResource {
}
