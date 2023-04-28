package com.okeeah.reggie.advice;

import com.okeeah.reggie.vo.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Description TODO
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/24
 */
@ControllerAdvice
@ResponseBody
public class MyGlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R  catchException(SQLIntegrityConstraintViolationException e) {
        SQLIntegrityConstraintViolationException cause=(SQLIntegrityConstraintViolationException) e.getCause();
        String message = cause.getMessage();
        String name = message.split("")[2];
        return  R.error("该用户名"+name+"已存在");
    }

    @ExceptionHandler(Exception.class)
    public R  catchException(Exception e){
        e.printStackTrace();
        return R.error(e.getMessage());
    }
}
