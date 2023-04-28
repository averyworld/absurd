package com.okeeah.reggie.filter;

/**
 * @Description TODO
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/27
 */
public class SecurityContext {
    public static  final  ThreadLocal<Long> CURRENT_ID_THREAD_LOCAL= new ThreadLocal<>();


    public static  Long currentId(){
        return  CURRENT_ID_THREAD_LOCAL.get();
    }
    public static void setCurrentId(Long id){
        CURRENT_ID_THREAD_LOCAL.set(id);
    }
    public  static void removeCurrentId(){
        CURRENT_ID_THREAD_LOCAL.remove();
    }
}
