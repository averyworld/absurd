package com.okeeah.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.okeeah.reggie.advice.CustomException;
import com.okeeah.reggie.dto.DishDTO;
import com.okeeah.reggie.entity.Category;
import com.okeeah.reggie.entity.Dish;
import com.okeeah.reggie.entity.DishFlavor;
import com.okeeah.reggie.entity.Employee;
import com.okeeah.reggie.mapper.DishMapper;
import com.okeeah.reggie.mapper.EmployeeMapper;
import com.okeeah.reggie.service.CategoryService;
import com.okeeah.reggie.service.IDishFlavorService;
import com.okeeah.reggie.service.IDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author SunYi okeeahsy@gmail.com
 * @Date 2023/4/27
 */
@Service
public class IDishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {
    @Autowired
    private IDishFlavorService iDishFlavorService;
    @Autowired
    private CategoryService iCategoryService;


    @Override
    public void add(DishDTO dishDTO) {
//      多态
        this.save(dishDTO);

        Long dishId = dishDTO.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();

        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishId);
            iDishFlavorService.save(flavor);
        }


    }


    //    page<dish>只有分类id无分类名字 自己构造一个dishdto 数据传输对象含分类名,并将查询结果放到newPage中
    @Override
    public Page<DishDTO> page4dishDTO(Page<Dish> queryPage, LambdaQueryWrapper<Dish> dishLambdaQueryWrapper) {
        Page<Dish> originPage = this.page(queryPage, dishLambdaQueryWrapper);

        Page<DishDTO> result = new Page<>();
//      能抄就抄 不能抄的自己查
        BeanUtils.copyProperties(originPage,result,"records");

        List<Dish> records = originPage.getRecords();

        List<DishDTO> newRecords = new ArrayList<>();

        for (Dish record : records) {
//          dish缺分类名字 所以创建一个分类名的传输对象
            DishDTO dishDTO = new DishDTO();
//           能抄就抄 不能抄的自己查
            BeanUtils.copyProperties(record,dishDTO);

            Category category = iCategoryService.getById(record.getCategoryId());

            dishDTO.setCategoryName(category.getName());

            newRecords.add(dishDTO);

        }
        result.setRecords(newRecords);
        return result;

    }

    @Override
    public DishDTO getById4DTO(Long id) {
        Dish dish = this.getById(id);
//        查出来的结果不包含口味信息
        DishDTO dishDTO = new DishDTO();
        BeanUtils.copyProperties(dish,dishDTO);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);

        List<DishFlavor> dishFlavors = iDishFlavorService.list(queryWrapper);

        dishDTO.setFlavors(dishFlavors);

        return dishDTO;



    }

    @Override
    public void updateById4DTO(DishDTO dishDTO) {

        this.updateById(dishDTO);

        List<DishFlavor> flavors = dishDTO.getFlavors();

        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishDTO.getId());
        iDishFlavorService.remove(dishFlavorLambdaQueryWrapper);
//      去掉isdelete字段避免逻辑删除
//      直接删除原来所有的按新的添加
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishDTO.getId());
            iDishFlavorService.save(flavor);
        }



    }

    @Override
    public void deleteByIds(List<Long> ids) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.in(Dish::getId, ids);
        //       需要判断是否停售才能删除
        dishLambdaQueryWrapper.eq(Dish::getStatus, 1);
        int count = this.count(dishLambdaQueryWrapper);
        if (count > 0) {
            throw new CustomException("菜品正在售卖中,不能删除");
        }
        this.removeByIds(ids);
    }

    @Override
    public List<DishDTO> listWithFlavors(LambdaQueryWrapper<Dish> dishLambdaQueryWrapper) {
        return null;
    }

}
