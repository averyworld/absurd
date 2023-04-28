package com.okeeah.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.okeeah.reggie.constants.Global;
import com.okeeah.reggie.dto.EmployeeDTO;
import com.okeeah.reggie.entity.Employee;
import com.okeeah.reggie.service.EmployeeService;
import com.okeeah.reggie.util.MD5Util;
import com.okeeah.reggie.vo.R;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * @Description TODO
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/24
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 登陆实现
     * @param employeeDTO
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody EmployeeDTO employeeDTO, HttpSession session) {
        //获取前端传递的 username password
        String username = employeeDTO.getUsername();
        String password = employeeDTO.getPassword();

        //根据用户名在数据库查询
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, username);
        Employee employee = employeeService.getOne(queryWrapper);
        if (employee == null) {
            return R.error("用户名/密码错误");
        }
        //比对前进行加密
        String encode = MD5Util.encode(password, username);
        //对比密码
        if (!encode.equals(employee.getPassword())) {
            return R.error("用户名/密码错误");
        }
        //对比状态
        if (employee.getStatus() == Global.STATUS_DISABLE) {
            return R.error("该账户已经被禁用");
        }
        //保存用户信息到session
        session.setAttribute(Global.BACKEND_SESSION_SER_KEY, employee);
        //返回成功登陆人名字
        return R.success(employee);
    }

    @PostMapping("/logout")
    public R logout(HttpSession session) {
        //注销用户
        session.invalidate();
        //不返回任何数据
        return R.success();
    }

    /**
     * 添加员工
     * @param employee
     * @param session
     * @return
     */
    @PostMapping
    public R addEmployee(@RequestBody Employee employee, HttpSession session) {
        //获取当前登陆人信息
        Employee currentUser = (Employee) session.getAttribute(Global.BACKEND_SESSION_SER_KEY);
        //判断当前登陆人是否为空 在filter中统一校验
        //if (currentUser == null) {
        //    return R.error(Global.EMPLOYEE_NOT_LOGIN);
        //}
        //保存员工信息到数据库
        //缺少密码信息，需要默认密码信息 密码：123456
        String encode = MD5Util.encode(Global.EMPLOYEE_DEFAULT_PASSWORD, employee.getUsername());
        employee.setPassword(encode);

        //设置状态
        employee.setStatus(Global.STATUS_ENABLE);

        //补全创建人，创建时间，更新人，更新时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //更新人就是当前登陆用户，取的信息则从session中获取
        employee.setCreateUser(currentUser.getId());
        employee.setUpdateUser(currentUser.getUpdateUser());

        //执行保存结果
        employeeService.save(employee);

        return R.success("保存员工信息成功");

    }

    /**
     * 查询员工管理页面
     * params  page =1 pageSize=10
     * @return code =1  data{total :总个数 records :当前页吗}
     */
    @GetMapping("/page")
    public R Page( @RequestParam(required = false) String name, int page ,int pageSize ){
        //获取当前登陆人信息
        //Employee currentUser = (Employee) session.getAttribute(Global.BACKEND_SESSION_SER_KEY);
        //判断当前登陆人是否为空 在filter中统一校验
        //if (currentUser == null) {
        //    return R.error(Global.EMPLOYEE_NOT_LOGIN);
        //}
        //  做分页查询
        Page<Employee> pageQuery = new Page<>(page, pageSize);
        //根据用户名条件判断
        if (StringUtils.hasText(name)){
            LambdaQueryWrapper<Employee> employeeLambdaQuery = new LambdaQueryWrapper<>();
            employeeLambdaQuery.like(Employee::getName,name);
            //根据用户名分页查询
            Page<Employee> result = employeeService.page(pageQuery,employeeLambdaQuery);
            return  R.success(result);
        }
        //没有传参 普通全部查询
        Page<Employee> result = employeeService.page(pageQuery);

        return  R.success(result);
    }

    /**
     * 根据id回显修改员工信息
     * @param id
     * @param session
     * @return
     */
    @GetMapping("/{id}")
    public  R findById(@PathVariable ("id")long id){
        //获取当前登陆人信息
        //Employee currentUser = (Employee) session.getAttribute(Global.BACKEND_SESSION_SER_KEY);
        //判断当前登陆人是否为空 在filter中统一校验
        //if (currentUser == null) {
        //    return R.error(Global.EMPLOYEE_NOT_LOGIN);
        //}
        Employee employee = employeeService.getById(id);
        return  R.success(employee);
    }


    @PutMapping
    public R updateById(@RequestBody Employee employee,HttpSession session){
        //获取当前登陆人信息
        Employee currentUser = (Employee) session.getAttribute(Global.BACKEND_SESSION_SER_KEY);
        //判断当前登陆人是否为空 在filter中统一校验
        //if (currentUser == null) {
        //    return R.error(Global.EMPLOYEE_NOT_LOGIN);
        //}
        //更新时候 更新时间和更新人设置
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(currentUser.getUpdateUser());
        employeeService.updateById(employee);
        return R.success("更新员工信息成功🏅");
    }

}
