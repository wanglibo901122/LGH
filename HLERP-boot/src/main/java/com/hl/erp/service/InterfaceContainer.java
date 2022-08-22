package com.hl.erp.service;

import com.hl.erp.utils.AnnotationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jishenghua 2018-10-7 15:25:09
 */
@Service
public class InterfaceContainer {
    private final Map<String, ICommonQuery> configComponentMap = new HashMap<>();

    @Autowired(required = false)
    private synchronized void init(ICommonQuery[] configComponents) {
        for (ICommonQuery configComponent : configComponents) {
            ResourceInfo info = AnnotationUtils.getAnnotation(configComponent, ResourceInfo.class);
            if (info != null) {
                configComponentMap.put(info.value(), configComponent);
            }
        }
    }

    public ICommonQuery getCommonQuery(String apiName) {
        return configComponentMap.get(apiName);
    }
}
