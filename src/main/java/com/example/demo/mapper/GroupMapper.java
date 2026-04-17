package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface GroupMapper extends BaseMapper<Group> {
    
    @Select("SELECT groups, groupscode FROM acd_ryzd")
    List<Group> selectAllGroups();
}