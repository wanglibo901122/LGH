package com.hl.erp.service.msg;

import com.hl.erp.service.ResourceInfo;

import java.lang.annotation.*;

/**
 * @author lyuguohua 華隆ERP  2019-9-7 22:52:35
 */
@ResourceInfo(value = "msg")
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MsgResource {
}
