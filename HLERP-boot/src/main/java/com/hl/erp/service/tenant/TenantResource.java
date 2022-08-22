package com.hl.erp.service.tenant;

import com.hl.erp.service.ResourceInfo;

import java.lang.annotation.*;

/**
 * @author lyuguohua 華隆ERP  2019-6-27 22:56:56
 */
@ResourceInfo(value = "tenant")
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TenantResource {
}
