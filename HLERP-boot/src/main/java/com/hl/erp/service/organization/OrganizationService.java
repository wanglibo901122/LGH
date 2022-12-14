package com.hl.erp.service.organization;

import com.alibaba.fastjson.JSONObject;
import com.hl.erp.datasource.vo.TreeNode;
import com.hl.erp.constants.BusinessConstants;
import com.hl.erp.constants.ExceptionConstants;
import com.hl.erp.datasource.entities.Organization;
import com.hl.erp.datasource.entities.OrganizationExample;
import com.hl.erp.datasource.entities.User;
import com.hl.erp.datasource.mappers.OrganizationMapper;
import com.hl.erp.datasource.mappers.OrganizationMapperEx;
import com.hl.erp.exception.BusinessRunTimeException;
import com.hl.erp.exception.JshException;
import com.hl.erp.service.log.LogService;
import com.hl.erp.service.user.UserService;
import com.hl.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description
 *
 * @Author: cjl
 * @Date: 2019/3/6 15:10
 */
@Service
public class OrganizationService {
    private Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    @Resource
    private OrganizationMapper organizationMapper;
    @Resource
    private OrganizationMapperEx organizationMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;

    public Organization getOrganization(long id) throws Exception {
        return organizationMapper.selectByPrimaryKey(id);
    }

    public List<Organization> getOrganizationListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<Organization> list = new ArrayList<>();
        try{
            OrganizationExample example = new OrganizationExample();
            example.createCriteria().andIdIn(idList);
            list = organizationMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertOrganization(JSONObject obj, HttpServletRequest request)throws Exception {
        Organization organization = JSONObject.parseObject(obj.toJSONString(), Organization.class);
        organization.setCreateTime(new Date());
        organization.setUpdateTime(new Date());
        int result=0;
        try{
            result=organizationMapper.insertSelective(organization);
            logService.insertLog("??????",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(organization.getOrgAbr()).toString(),request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateOrganization(JSONObject obj, HttpServletRequest request)throws Exception {
        Organization organization = JSONObject.parseObject(obj.toJSONString(), Organization.class);
        organization.setUpdateTime(new Date());
        int result=0;
        try{
            result=organizationMapper.updateByPrimaryKeySelective(organization);
            logService.insertLog("??????",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(organization.getOrgAbr()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteOrganization(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteOrganizationByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteOrganization(String ids, HttpServletRequest request)throws Exception {
        return batchDeleteOrganizationByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteOrganizationByIds(String ids) throws Exception{
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        List<Organization> list = getOrganizationListByIds(ids);
        for(Organization organization: list){
            sb.append("[").append(organization.getOrgAbr()).append("]");
        }
        logService.insertLog("??????", sb.toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int result=0;
        List <Organization> organList = organizationMapperEx.getOrganizationByParentIds(idArray);
        if(organList!=null && organList.size()>0) {
            //????????????????????????????????????
            logger.error("?????????[{}],????????????[{}]",
                    ExceptionConstants.ORGANIZATION_CHILD_NOT_ALLOWED_DELETE_CODE,ExceptionConstants.ORGANIZATION_CHILD_NOT_ALLOWED_DELETE_MSG);
            throw new BusinessRunTimeException(ExceptionConstants.ORGANIZATION_CHILD_NOT_ALLOWED_DELETE_CODE,
                    ExceptionConstants.ORGANIZATION_CHILD_NOT_ALLOWED_DELETE_MSG);
        } else {
            result=organizationMapperEx.batchDeleteOrganizationByIds(
                    new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        OrganizationExample example = new OrganizationExample();
        example.createCriteria().andIdNotEqualTo(id).andOrgAbrEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Organization> list=null;
        try{
            list= organizationMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int addOrganization(Organization org) throws Exception{
        logService.insertLog("??????",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(org.getOrgAbr()).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        //????????????
        Date date=new Date();
        User userInfo=userService.getCurrentUser();
        org.setCreateTime(date);
        //????????????
        org.setUpdateTime(date);
        /**
         *????????????????????????????????????????????????
         * */
        if(StringUtil.isNotEmpty(org.getOrgNo())){
            checkOrgNoIsExists(org.getOrgNo(),null);
        }
        /**
         * ????????????????????????????????????????????????
         * */
        if(org.getParentId()!=null){
            org.setParentId(null);
        }
        int result=0;
        try{
            result=organizationMapperEx.addOrganization(org);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int editOrganization(Organization org)throws Exception {
        logService.insertLog("??????",
               new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(org.getOrgAbr()).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        //????????????
        org.setUpdateTime(new Date());
        User userInfo=userService.getCurrentUser();
        /**
         * ????????????????????????????????????????????????
         * */
        if(StringUtil.isNotEmpty(org.getOrgNo())){
            checkOrgNoIsExists(org.getOrgNo(),org.getId());
        }
        /**
         * ????????????????????????????????????????????????
         * */
        if(org.getParentId()!=null){
            org.setParentId(null);
        }
        int result=0;
        try{
            result=organizationMapperEx.editOrganization(org);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public List<TreeNode> getOrganizationTree(Long id)throws Exception {
        List<TreeNode> list=null;
        try{
            list=organizationMapperEx.getNodeTree(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Organization> findById(Long id) throws Exception{
        OrganizationExample example = new OrganizationExample();
        example.createCriteria().andIdEqualTo(id);
        List<Organization> list=null;
        try{
            list=organizationMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Organization> findByParentId(Long parentId)throws Exception {
        List<Organization> list=null;
        if(parentId!=null){
            OrganizationExample example = new OrganizationExample();
            example.createCriteria().andIdEqualTo(parentId).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
            try{
                list=organizationMapper.selectByExample(example);
            }catch(Exception e){
                JshException.readFail(logger, e);
            }
        }
        return list;
    }

    public List<Organization> findByOrgNo(String orgNo)throws Exception {
        OrganizationExample example = new OrganizationExample();
        example.createCriteria().andOrgNoEqualTo(orgNo).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Organization> list=null;
        try{
            list=organizationMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }
    /**
     * create by: cjl
     * description:
     *  ????????????????????????????????????
     * create time: 2019/3/7 10:01
     * @Param: orgNo
     * @return void
     */
    public void checkOrgNoIsExists(String orgNo,Long id)throws Exception {
        List<Organization> orgList=findByOrgNo(orgNo);
        if(orgList!=null&&orgList.size()>0){
            if(orgList.size()>1){
                logger.error("?????????[{}],????????????[{}],??????,orgNo[{}]",
                        ExceptionConstants.ORGANIZATION_NO_ALREADY_EXISTS_CODE,ExceptionConstants.ORGANIZATION_NO_ALREADY_EXISTS_MSG,orgNo);
                //???????????????????????????1????????????????????????
                throw new BusinessRunTimeException(ExceptionConstants.ORGANIZATION_NO_ALREADY_EXISTS_CODE,
                        ExceptionConstants.ORGANIZATION_NO_ALREADY_EXISTS_MSG);
            }
            if(id!=null){
                if(!orgList.get(0).getId().equals(id)){
                    //??????????????????1??????????????????????????????id?????????
                    logger.error("?????????[{}],????????????[{}],??????,orgNo[{}],id[{}]",
                            ExceptionConstants.ORGANIZATION_NO_ALREADY_EXISTS_CODE,ExceptionConstants.ORGANIZATION_NO_ALREADY_EXISTS_MSG,orgNo,id);
                    throw new BusinessRunTimeException(ExceptionConstants.ORGANIZATION_NO_ALREADY_EXISTS_CODE,
                            ExceptionConstants.ORGANIZATION_NO_ALREADY_EXISTS_MSG);
                }
            }else{
                logger.error("?????????[{}],????????????[{}],??????,orgNo[{}]",
                        ExceptionConstants.ORGANIZATION_NO_ALREADY_EXISTS_CODE,ExceptionConstants.ORGANIZATION_NO_ALREADY_EXISTS_MSG,orgNo);
                //??????????????????1?????????????????????
                throw new BusinessRunTimeException(ExceptionConstants.ORGANIZATION_NO_ALREADY_EXISTS_CODE,
                        ExceptionConstants.ORGANIZATION_NO_ALREADY_EXISTS_MSG);
            }
        }

    }

    /**
     * ????????????id????????????????????????id
     * @return
     */
    public List<Long> getOrgIdByParentId(Long orgId) {
        List<Long> idList = new ArrayList<>();
        OrganizationExample example = new OrganizationExample();
        example.createCriteria().andIdEqualTo(orgId).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Organization> orgList = organizationMapper.selectByExample(example);
        if(orgList!=null && orgList.size()>0) {
            idList.add(orgId);
            getOrgIdByParentNo(idList, orgList.get(0).getId());
        }
        return idList;
    }

    /**
     * ??????????????????????????????????????????
     * @param id
     * @return
     */
    public void getOrgIdByParentNo(List<Long> idList,Long id) {
        OrganizationExample example = new OrganizationExample();
        example.createCriteria().andParentIdEqualTo(id).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Organization> orgList = organizationMapper.selectByExample(example);
        if(orgList!=null && orgList.size()>0) {
            for(Organization o: orgList) {
                idList.add(o.getId());
                getOrgIdByParentNo(idList, o.getId());
            }
        }
    }
}
