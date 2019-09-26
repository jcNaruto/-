package com.jc.laboratory.dao;

import com.jc.laboratory.entity.Manipulation;
import com.jc.laboratory.entity.ManipulationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ManipulationMapper {
    int countByExample(ManipulationExample example);

    int deleteByExample(ManipulationExample example);

    int deleteByPrimaryKey(Integer manipulationId);

    int insert(Manipulation record);

    int insertSelective(Manipulation record);

    List<Manipulation> selectByExample(ManipulationExample example);

    Manipulation selectByPrimaryKey(Integer manipulationId);

    int updateByExampleSelective(@Param("record") Manipulation record, @Param("example") ManipulationExample example);

    int updateByExample(@Param("record") Manipulation record, @Param("example") ManipulationExample example);

    int updateByPrimaryKeySelective(Manipulation record);

    int updateByPrimaryKey(Manipulation record);
}