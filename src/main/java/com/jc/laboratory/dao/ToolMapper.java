package com.jc.laboratory.dao;

import com.jc.laboratory.entity.Tool;
import com.jc.laboratory.entity.ToolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ToolMapper {
    int countByExample(ToolExample example);

    int deleteByExample(ToolExample example);

    int deleteByPrimaryKey(Integer boxId);

    int insert(Tool record);

    int insertSelective(Tool record);

    List<Tool> selectByExample(ToolExample example);

    Tool selectByPrimaryKey(Integer boxId);

    int updateByExampleSelective(@Param("record") Tool record, @Param("example") ToolExample example);

    int updateByExample(@Param("record") Tool record, @Param("example") ToolExample example);

    int updateByPrimaryKeySelective(Tool record);

    int updateByPrimaryKey(Tool record);
}