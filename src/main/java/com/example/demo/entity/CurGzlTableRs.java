package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("acd_dangri_gzl_rs")
public class CurGzlTableRs {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("comcode")
    private String comCode;   // 部门编码

    @TableField("comname")
    private String comName;   // 部门名称

    @TableField("shougen")
    private Integer shouGen;        // 首跟

    @TableField("hougen")
    private Integer houGen;         // 后跟

    @TableField("tiaojie_zhuyuan")
    private Integer tiaojieZhuyuan; // 调解-住院

    @TableField("tiaojie_menzheng")
    private Integer tiaojieMenzheng; // 调解-门诊

    @TableField("jiean_zhuyuan")
    private Integer jieanZhuyuan;  // 结案-住院

    @TableField("jiean_mengzheng")
    private Integer jieanMengzheng; // 结案-门诊

    @TableField("tjdate")
    private String tjDate;    // 统计日期

    @TableField("tjtime")
    private String maxTjTime; // 统计时间
}