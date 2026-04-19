package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("acd_dangri_gzl_group")
public class CurGzlTableGroup {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("comcode")
    private String comCode;

    @TableField("comname")
    private String comName;

    @TableField("groups")
    private String groups;

    @TableField("groupscode")
    private String groupsCode;

    @TableField("ck_jsl")
    private Integer ckJsl;

    @TableField("ck_jsl_wcl")
    private Integer ckJslWcl;

    @TableField("ck_wcl")
    private Integer ckWcl;

    @TableField("ds_tjl")
    private Integer dsTjl;

    @TableField("ds_wcl")
    private Integer dsWcl;

    @TableField("ds_zfl")
    private Integer dsZfl;

    @TableField("shougen")
    private Integer shouGen;

    @TableField("hougen")
    private Integer houGen;

    @TableField("tiaojie")
    private Integer tiaoJie;

    @TableField("ja")
    private Integer ja;

    @TableField("zl")
    private Integer zl;

    @TableField("tjdate")
    private String tjDate;

    @TableField("tjtime")
    private String maxTjTime;
}