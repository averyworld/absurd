package com.okeeah.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.okeeah.reggie.entity.Employee;
import com.okeeah.reggie.mapper.EmployeeMapper;
import com.okeeah.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/24
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
