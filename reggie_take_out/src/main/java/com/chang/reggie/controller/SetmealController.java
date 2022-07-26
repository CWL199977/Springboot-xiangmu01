package com.chang.reggie.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chang.reggie.service.SetmealService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 套餐(Setmeal)表控制层
 *
 * @author makejava
 * @since 2022-07-25 16:29:50
 */
@RestController
@RequestMapping("setmeal")
public class SetmealController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private SetmealService setmealService;

}

