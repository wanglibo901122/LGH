package com.hl.erp.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by lyuguohua 華隆ERP 2018-5-11 09:48:08
 *
 * @author jishenghua
 */
public class JsonUtils {

    public static JSONObject ok(){
        JSONObject obj = new JSONObject();
        JSONObject tmp = new JSONObject();
        tmp.put("message", "成功");
        obj.put("code", 200);
        obj.put("data", tmp);
        return obj;
    }

}
