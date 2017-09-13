package com.ourslook.production.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourslook.production.entity.UserEntity;
import com.ourslook.production.utils.R;
import com.ourslook.production.utils.annotation.IgnoreAuth;
import com.ourslook.production.utils.annotation.LoginUser;

/**
 * API测试接口
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
@Api("测试接口")
public class ApiTestController {

    /**
     * 获取用户信息
     */
    @GetMapping("userInfo")
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true)
    public R userInfo(@LoginUser UserEntity user){
        return R.ok().put("user", user);
    }

    /**
     * 忽略Token验证测试
     */
    @IgnoreAuth
    @GetMapping("notToken")
    @ApiOperation(value = "忽略Token验证测试")
    public R notToken(HttpServletRequest request){
    	String root = request.getSession(true).getServletContext().getRealPath("/");
        return R.ok().put("msg", "无需token也能访问。。。"+root);
    }
}
