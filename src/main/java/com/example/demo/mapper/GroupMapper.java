package com.example.demo.mapper;

import com.example.demo.entity.Group;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupMapper {

    int insert(Group group);

    Group selectByGroupscode(String groupscode);

    int updateByGroupscode(Group group);

    int deleteByGroupscode(String groupscode);

    List<Group> selectAllGroups();
}