package com.jc.laboratory.dao;

import com.jc.laboratory.entity.UserActivity;
import com.jc.laboratory.entity.UserActivityExample;
import com.jc.laboratory.entity.UserActivityKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserActivityMapper {
    int countByExample(UserActivityExample example);

    int deleteByExample(UserActivityExample example);

    int deleteByPrimaryKey(UserActivityKey key);

    int insert(UserActivity record);

    int insertSelective(UserActivity record);

    List<UserActivity> selectByExample(UserActivityExample example);

    UserActivity selectByPrimaryKey(UserActivityKey key);

    int updateByExampleSelective(@Param("record") UserActivity record, @Param("example") UserActivityExample example);

    int updateByExample(@Param("record") UserActivity record, @Param("example") UserActivityExample example);

    int updateByPrimaryKeySelective(UserActivity record);

    int updateByPrimaryKey(UserActivity record);
}