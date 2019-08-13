package cn.org.yxzb.camemt.controller;

import cn.org.yxzb.camemt.service.TsRoleService;
import cn.org.yxzb.camemt.service.TsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TsUserService userService;

    @Autowired
    private TsRoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin')")
    public String test(){
        return "hello world!";
    }

    @GetMapping("hehe")
    @PreAuthorize("hasAuthority('hehe')")
    public String test1(){
        return "hello hehe!";
    }

    @GetMapping("test2")
    public String test2(){
        return "hello hehe!";
    }


}
