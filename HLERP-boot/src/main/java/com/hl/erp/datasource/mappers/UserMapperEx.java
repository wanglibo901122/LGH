package com.hl.erp.datasource.mappers;

import com.hl.erp.datasource.entities.User;
import com.hl.erp.datasource.vo.TreeNodeEx;
import com.hl.erp.datasource.entities.UserEx;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapperEx {

    List<UserEx> selectByConditionUser(
            @Param("userName") String userName,
            @Param("loginName") String loginName,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByUser(
            @Param("userName") String userName,
            @Param("loginName") String loginName);

    List<User> getUserListByUserNameOrLoginName(@Param("userName") String userName,
                                                @Param("loginName") String loginName);

    int batDeleteOrUpdateUser(@Param("ids") String ids[], @Param("status") byte status);

    List<TreeNodeEx> getNodeTree();
    List<TreeNodeEx> getNextNodeTree(Map<String, Object> parameterMap);
}