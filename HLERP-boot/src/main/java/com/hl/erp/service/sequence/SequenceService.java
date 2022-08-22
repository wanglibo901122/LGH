package com.hl.erp.service.sequence;

import com.alibaba.fastjson.JSONObject;
import com.hl.erp.constants.BusinessConstants;
import com.hl.erp.datasource.entities.SerialNumber;
import com.hl.erp.datasource.entities.SerialNumberEx;
import com.hl.erp.datasource.mappers.SequenceMapperEx;
import com.hl.erp.datasource.entities.*;
import com.hl.erp.datasource.mappers.*;
import com.hl.erp.exception.JshException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description
 *
 * @Author: Hualong
 * @Date: 2021/3/16 16:33
 */
@Service
public class SequenceService {
    private Logger logger = LoggerFactory.getLogger(SequenceService.class);

    @Resource
    private SequenceMapperEx sequenceMapperEx;

    public SerialNumber getSequence(long id)throws Exception {
        return null;
    }

    public List<SerialNumberEx> select(String name, Integer offset, Integer rows)throws Exception {
        return null;
    }

    public Long countSequence(String name)throws Exception {
        return null;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertSequence(JSONObject obj, HttpServletRequest request)throws Exception {
        return 0;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateSequence(JSONObject obj, HttpServletRequest request) throws Exception{
        return 0;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteSequence(Long id, HttpServletRequest request)throws Exception {
        return 0;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSequence(String ids, HttpServletRequest request)throws Exception {
        return 0;
    }

    public int checkIsNameExist(Long id, String serialNumber)throws Exception {
        return 0;
    }

    /**
     * 创建一个唯一的序列号
     * */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public String buildOnlyNumber()throws Exception{
        Long buildOnlyNumber=null;
        synchronized (this){
            try{
                sequenceMapperEx.updateBuildOnlyNumber(); //编号+1
                buildOnlyNumber= sequenceMapperEx.getBuildOnlyNumber(BusinessConstants.DEPOT_NUMBER_SEQ);
            }catch(Exception e){
                JshException.writeFail(logger, e);
            }
        }
        if(buildOnlyNumber<BusinessConstants.SEQ_TO_STRING_MIN_LENGTH){
            StringBuffer sb=new StringBuffer(buildOnlyNumber.toString());
            int len=BusinessConstants.SEQ_TO_STRING_MIN_LENGTH.toString().length()-sb.length();
            for(int i=0;i<len;i++){
                sb.insert(0,BusinessConstants.SEQ_TO_STRING_LESS_INSERT);
            }
            return sb.toString();
        }else{
            return buildOnlyNumber.toString();
        }
    }
}
