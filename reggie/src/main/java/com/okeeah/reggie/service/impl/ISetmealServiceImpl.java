package com.okeeah.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.okeeah.reggie.entity.Setmeal;
import com.okeeah.reggie.mapper.SetmealMapper;
import com.okeeah.reggie.service.ISetmealService;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/27
 */
@Service
public class ISetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>implements ISetmealService {
}
