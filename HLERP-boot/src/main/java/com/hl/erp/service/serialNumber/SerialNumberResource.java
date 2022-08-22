package com.hl.erp.service.serialNumber;

import com.hl.erp.service.ResourceInfo;

import java.lang.annotation.*;

/**
 * Description
 *
 * @Author: lyuguohua 華隆ERP
 * @Date: 2019/1/21 16:33
 */
@ResourceInfo(value = "serialNumber")
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SerialNumberResource {
}
