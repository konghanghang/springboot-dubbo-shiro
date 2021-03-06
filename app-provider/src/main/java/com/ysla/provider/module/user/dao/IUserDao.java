package com.ysla.provider.module.user.dao;

import com.alibaba.fastjson.JSONObject;
import com.ysla.api.auto.mapper.UserMapper;
import com.ysla.api.auto.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * 用户mapper扩展类
 * @author konghang
 */
@Mapper
public interface IUserDao extends UserMapper {

    /**
     * 查询用户
     * @param user
     * @return
     */
    @SelectProvider(type= UserDaoSqlProvider.class, method="selectUserBy")
    User selectUserBy(User user);

    /**
     * 获取用户基础信息
     * @param userId
     * @return
     */
    @Select({"select ref_user_id userId, username, nickname from t_user where ref_user_id = #{userId}"})
    JSONObject simpleInfo(String userId);

}
