package com.chang.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chang.reggie.entity.Category;


/**
 * @author chang
 * @description 针对表【category(菜品及套餐分类)】的数据库操作Service
 * @createDate 2022-07-25 11:46:32
 */
public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
