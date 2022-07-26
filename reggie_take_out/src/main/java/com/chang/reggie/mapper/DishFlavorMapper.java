package com.chang.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chang.reggie.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

/**
* @author chang
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
* @createDate 2022-07-26 08:52:58
* @Entity .entity.DishFlavor
*/
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

}




