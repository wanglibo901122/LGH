package com.hl.erp.service.platformConfig;

import com.hl.erp.service.ResourceInfo;

import java.lang.annotation.*;

/**
 * @author lyuguohua 華隆ERP  2020-10-16 22:26:27
 */
@ResourceInfo(value = "platformConfig")
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlatformConfigResource {
}
