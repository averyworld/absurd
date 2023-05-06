package com.okeeah.reggie.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.okeeah.reggie.dto.SetmealDto;
import com.okeeah.reggie.entity.Setmeal;

import java.util.List;

/**
 * @Description TODO
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/27
 */
public interface ISetmealService extends IService<Setmeal> {

    void saveSetmealAndDish(SetmealDto setmealDto);

    Page<SetmealDto> page4SetmealDTO(Page<Setmeal> pageQuery, LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper);

    SetmealDto findById4DTO(Long id);

    void update4DTO(SetmealDto setmealDto);

    void deleteByIds(List<Long> ids);
}
