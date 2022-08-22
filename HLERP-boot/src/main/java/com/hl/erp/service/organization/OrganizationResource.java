package com.hl.erp.service.organization;

import com.hl.erp.service.ResourceInfo;

import java.lang.annotation.*;

/**
 * Description
 *  机构
 * @Author: cjl
 * @Date: 2019/3/6 15:10
 */
@ResourceInfo(value = "organization")
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrganizationResource {
}
