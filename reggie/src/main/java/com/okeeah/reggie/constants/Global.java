package com.okeeah.reggie.constants;

/**
 * @Description 全局常量类
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/24
 */
public interface Global {
    /**
     * 表示用户登陆功能，存储用户的session的key名字
     */

    String BACKEND_SESSION_SER_KEY = "backend_session_user";

    /**
     * 状态标识常量
     */
    Integer STATUS_DISABLE = 0;
    Integer STATUS_ENABLE = 1;

    /**
     * 用户默认密码
     */
    String EMPLOYEE_DEFAULT_PASSWORD="123456";


    String EMPLOYEE_NOT_LOGIN="NOTLOGIN";
}
