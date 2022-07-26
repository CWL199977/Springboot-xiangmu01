package com.chang.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chang.reggie.common.R;
import com.chang.reggie.entity.Employee;
import com.chang.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 员工信息(Employee)表控制层
 *
 * @author makejava
 * @since 2022-07-21 18:45:25
 */
@Slf4j
@RestController
@RequestMapping("employee")
public class EmployeeController {
    /**
     * 服务对象
     */
    @Resource
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request,
                             @RequestBody Employee employee) {

        //1.将页面提交的密码进行m5d加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2.根据页面提交的用户名查询数据库employee = {Employee@7124} "Employee(id=null, name=null, username=admin, password=111111, phone=null, sex=null, idNumber=null, status=null, createTime=null, updateTime=null, createUser=null, updateUser=null)"
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);


        //3.判断用户名是否存在,如果没有查询到,返回登录失败
        if (emp == null) {
            return R.error("登录失败,用户名不存在");
        }

        //4.判断密码是否正确,如果不正确,返回登录失败
        if (!password.equals(emp.getPassword())) {
            return R.error("登录失败,密码不正确");
        }

        //5.查看与员工状态，如果为已禁用状态，返回员工已禁用
        if (emp.getStatus() == 0) {
            return R.error("登录失败,员工已禁用");
        }

        //6.登陆成功，将员工信息存入session中并返回登录成功
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);


    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //1.清除session中的员工信息
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request1,@RequestBody Employee employee) {
        log.info("新增员工,员工信息: {}", employee.toString());
        //设置初始密码123456使用md5加密

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        //设置创建时间和修改时间
       /* employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //获取当前用户的id
        Long empId = (Long) request1.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);*/
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     * 员工分页信息查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("分页查询员工信息,页码: {}, 每页条数: {}, 员工姓名: {}", page, pageSize, name);
        //分页构造器
        Page pageInfo= new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据id修改员工信息
     */
 @PutMapping
 public R<String> update(HttpServletRequest request1,@RequestBody Employee employee) {
      log.info("修改员工信息,员工id: {}, 员工信息: {}", employee.toString());
      //设置修改时间
      /*employee.setUpdateTime(LocalDateTime.now());
      //获取当前用户的id
      Long empId = (Long) request1.getSession().getAttribute("employee");
      employee.setUpdateUser(empId);*/
      employeeService.updateById(employee);
      return R.success("修改员工信息成功");
  }
  @GetMapping("/{id}")
    public R<Employee> get(@PathVariable Long id) {
        log.info("根据id查询员工信息,员工id: {}");
        Employee employee = employeeService.getById(id);
        if (employee!=null){
            return R.success(employee);
        }
      return R.error("修改员工信息失败");
    }



}



