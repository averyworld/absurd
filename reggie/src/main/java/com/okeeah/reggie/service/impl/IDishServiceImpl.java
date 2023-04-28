package com.okeeah.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.okeeah.reggie.entity.Dish;
import com.okeeah.reggie.entity.Employee;
import com.okeeah.reggie.mapper.DishMapper;
import com.okeeah.reggie.mapper.EmployeeMapper;
import com.okeeah.reggie.service.IDishService;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/27
 */
@Service
public class IDishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {
}
