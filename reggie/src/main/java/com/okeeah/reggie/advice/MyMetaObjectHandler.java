package com.okeeah.reggie.advice;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.okeeah.reggie.dto.User;
import com.okeeah.reggie.dto.UserHolder;
import com.okeeah.reggie.entity.Employee;
import com.okeeah.reggie.filter.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * @Description TODO
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/25
 */
//自定义元数据对象处理器
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    //MetaObject 代表的就是你要填充的对象

    //插入操作，自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
            //根据SecurityContext工具类中获取当前session中的用户id
        Long id = SecurityContext.currentId();
        metaObject.setValue("createUser", id);
        metaObject.setValue("updateUser", id);
        }


    //更新操作，自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        //根据SecurityContext工具类中获取当前session中的用户id
        Long id = SecurityContext.currentId();
        metaObject.setValue("updateUser", id);

    }
}