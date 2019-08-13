package cn.org.yxzb.camemt.controller;

import cn.org.yxzb.camemt.pojo.TsUser;
import cn.org.yxzb.camemt.service.TsUserService;
import cn.org.yxzb.camemt.utils.PageResult;
import cn.org.yxzb.camemt.utils.Result;
import cn.org.yxzb.camemt.utils.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;

@RestController
@RequestMapping("user")
public class TsUserController {

    @Autowired
    private TsUserService userService;

    //分页查 管理员列表
    @PostMapping("{pageNum}/{pageSize}")
    public Result page(@PathVariable int pageNum, @PathVariable int pageSize, @RequestBody HashMap<String, String> hashMap) {
        Example example = new Example(TsUser.class);
        PageResult pageResult = userService.selectList(pageNum, pageSize, example);
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    //新增 管理员
//    public Result
    //删除 管理员

    //修改 管理员

}
