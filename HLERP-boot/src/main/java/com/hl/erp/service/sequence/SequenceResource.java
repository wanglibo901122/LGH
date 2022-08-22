package com.hl.erp.service.sequence;

import com.hl.erp.service.ResourceInfo;

import java.lang.annotation.*;

/**
 * Description
 *
 * @Author: lyuguohua 華隆ERP
 * @Date: 2021/3/16 16:33
 */
@ResourceInfo(value = "sequence")
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SequenceResource {
}
