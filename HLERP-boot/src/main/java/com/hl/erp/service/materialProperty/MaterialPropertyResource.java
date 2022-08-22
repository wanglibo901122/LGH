package com.hl.erp.service.materialProperty;

import com.hl.erp.service.ResourceInfo;

import java.lang.annotation.*;

/**
 * @author lyuguohua 華隆ERP  2018-10-7 15:26:27
 */
@ResourceInfo(value = "materialProperty")
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaterialPropertyResource {
}
