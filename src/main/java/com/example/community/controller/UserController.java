package com.example.community.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.common.R;
import com.example.community.entity.User;
import com.example.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 成员登录
     * @param request
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody User user){

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,user.getPhone());
        User emp = userService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if(emp == null){
            return R.error("登录失败");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if(!emp.getPassword().equals(user.getPassword())){
            return R.error("登录失败");
        }

        if(emp.getStatus() == 0){
            return R.error("账号已禁用");
        }
        //6、登录成功，将住户id存入Session并返回登录成功结果
        request.getSession().setAttribute("user",emp.getId());
        return R.success(emp);
    }

    @GetMapping("/logincode")
    public ResponseEntity<byte[]> LoginCode(HttpServletRequest request){

        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

        String captchaText = lineCaptcha.getCode();

        // 将验证码存储在会话中
        HttpSession session = request.getSession();
        session.setAttribute("captcha", captchaText);

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();

        // 将验证码图片数据转换为字节数组
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        lineCaptcha.write(outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(imageBytes.length);

        // 返回图片数据
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/validatecaptcha")
    public boolean validateCaptcha(@RequestParam String userInputtedCaptcha, HttpSession session) {
        // 从会话中获取生成的验证码
        String generatedCaptcha = (String) session.getAttribute("captcha");
        System.out.println("*****************");
        System.out.println(generatedCaptcha);
        System.out.println("******************");

        // 将用户输入的验证码与生成的验证码进行比较
        return userInputtedCaptcha.equals(generatedCaptcha);
    }

    /**
     * 用户退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录用户的id
        request.getSession().removeAttribute("user");
        return R.success("退出成功");
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody User user){
        log.info("新增用户，用户信息：{}", user.toString());

        user.setUpdateTime(LocalDateTime.now());//更新时间

        //获得当前登录用户的id
        Long empId = (Long) request.getSession().getAttribute("user");

        user.setUpdateUser(empId);//更新人

        userService.save(user);

        return R.success("新增住户成功");
    }

    @PostMapping("/register")
    public R<String> register(HttpServletRequest request,@RequestBody User user){
        log.info("新增用户，用户信息：{}", user.toString());

        user.setUpdateTime(LocalDateTime.now());//更新时间


        userService.save(user);

        return R.success("新增住户成功");
    }

    /**
     * 用户信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page = {},pageSize = {},name = {}" ,page,pageSize,name);

        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), User::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(User::getUpdateTime);

        //执行查询
        userService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据id修改用户信息
     * @param user
     * @return0
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody User user){
        log.info(user.toString());

        long id = Thread.currentThread().getId();
        log.info("线程id为：{}",id);
        user.setUpdateTime(LocalDateTime.now());//更新时间

        //获得当前登录用户的id
        Long empId = (Long) request.getSession().getAttribute("user");

        user.setUpdateUser(empId);//更新人

        userService.updateById(user);
        return R.success("住户信息修改成功");
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<User> getById(@PathVariable Long id){
        log.info("根据id查询员工信息...");
        User user = userService.getById(id);
        if(user != null){
            return R.success(user);
        }
        return R.error("没有查询到对应员工信息");
    }

    @DeleteMapping
    public R<String> delete(Long id){
        log.info("删除分类，id为：{}",id);
        userService.remove(id);
        return R.success("住户信息删除成功");
    }
}


