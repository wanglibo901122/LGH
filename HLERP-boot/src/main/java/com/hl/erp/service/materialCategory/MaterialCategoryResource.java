package com.hl.erp.service.materialCategory;

import com.hl.erp.service.ResourceInfo;

import java.lang.annotation.*;

/**
 * @author lyuguohua 華隆ERP  2018-10-7 15:26:27
 */
@ResourceInfo(value = "materialCategory")
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaterialCategoryResource {
}
