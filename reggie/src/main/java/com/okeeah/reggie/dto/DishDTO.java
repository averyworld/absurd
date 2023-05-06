package com.okeeah.reggie.dto;

import com.okeeah.reggie.entity.Dish;
import com.okeeah.reggie.entity.DishFlavor;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

/**
 * @author: Avery
 * @TIME: 2022/5/9 5:06 PM
 * @Description:
 * @since: JDK 1.8
 * @version: 1.0
 */
@Data
public class DishDTO extends Dish {
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;

}
