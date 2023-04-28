package com.okeeah.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.okeeah.reggie.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

    ////根据用户名查询员工信息
    //Employee findByUsername(String username);
    //
    ////根据name模糊查询
    //List<Employee> findByName(String name);
    //
    ////保存
    //void save(Employee employee);
    //
    ////根据id查询员工信息
    //Employee findById(Long id);
    //
    ////更新
    //void update(Employee employee);
}
