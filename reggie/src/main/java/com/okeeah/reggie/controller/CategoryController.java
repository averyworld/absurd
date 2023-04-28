package com.okeeah.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.okeeah.reggie.advice.CustomException;
import com.okeeah.reggie.entity.Category;
import com.okeeah.reggie.service.CategoryService;
import com.okeeah.reggie.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //查询分类列表
    @GetMapping("/category/page")
    public R page(int page ,int pageSize) {
        //分页
        Page<Category> pageQuery = new Page<>(page,pageSize);
        //查询条件
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);

        Page<Category> result = categoryService.page(pageQuery, queryWrapper);
        return R.success(result);
    }

    //保存菜品分类
    @PostMapping("/category")
    public R save(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("保存分类信息成功");
    }

    //修改
    @PutMapping("/category")
    public R<Object> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("更新分类数据的信息");
    }

    //删除
    @DeleteMapping("/category")
    public R deleteById(Long id) {
        try {
            categoryService.deleteById(id);
            return R.success("删除成功");
        }catch (CustomException e){
            return R.error(e.getMessage());
        }
    }

    //根据类型查询分类列表
    @GetMapping("/category/list")
    public R findByType(Integer type){
        //List<Category> categoryList =  categoryService.findByType(type);
        return R.success();
    }
}
