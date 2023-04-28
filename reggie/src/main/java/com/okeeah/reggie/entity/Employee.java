package com.okeeah.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

// 员工
@Data
@EqualsAndHashCode(callSuper = false)
public class Employee implements Serializable {

    private  static  final long SerialVersionUID = 1L;

    //@JsonSerialize(using = ToStringSerializer.class)
    private Long id;//主键

    private String username;//用户名

    private String name;//姓名

    private String password;//密码

    private String phone;//手机号

    private String sex;//性别

    private String idNumber;//身份证号

    private Integer status;//状态 0:禁用 1:正常

    /**
     * //状态 0:普通用户 1:管理员
     */
    private  boolean administrator;

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