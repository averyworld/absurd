package com.okeeah.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

//分类
@Data
public class Category implements Serializable {

    public static final Integer TYPE_DISH = 1; //菜品
    public static final Integer TYPE_SETMEAL = 2;//套餐

    private Long id;//主键

    private Integer type;//类型 1 菜品分类 2 套餐分类

    private String name;//分类名称

    private Integer sort; //顺序

    // @TableField(fill= FieldFill.INSERT)//fill声明当前字段在什么时候进行填充
    // FieldFill.INSERT(insert语句)
    // INSERT_UPDATE(insert和update语句)

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;//创建用户

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;//更新用户

}