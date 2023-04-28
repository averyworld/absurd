package com.okeeah.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description TODO
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/27
 */
@Data
public class Setmeal implements Serializable {

    private Long id;//主键

    private Long setmealId;//套餐id

    private Long dishId;//菜品id

    private String name;//菜品名称 （冗余字段）

    private BigDecimal price;//菜品原价

    private Integer copies;//份数

    private Integer sort;//排序

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    //是否删除
    private Integer isDeleted;
}
