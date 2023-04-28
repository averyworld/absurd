package com.okeeah.reggie.advice;

import com.okeeah.reggie.entity.Employee;

/**
 * @Description TODO
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/25
 */

//跟员工相关,操作ThreadLocal的工具类
public class EmployeeHolder {

    //声明ThreadLocal
    private static ThreadLocal<Employee> th = new ThreadLocal<Employee>();

    //存储
    public static void set(Employee employee) {
        th.set(employee);
    }

    //获取
    public static Employee get() {
        return th.get();
    }

    //移除
    public static void remove() {
        th.remove();
    }
}