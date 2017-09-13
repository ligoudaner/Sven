package com.ourslook.production.api;

import com.ourslook.production.service.TokenService;
import com.ourslook.production.utils.R;
import com.ourslook.production.utils.validator.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourslook.production.service.UserService;
import com.ourslook.production.utils.annotation.IgnoreAuth;

import java.util.Map;

/**
 * API登录授权
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
@Api("登录接口")
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    /**
     * 登录
     */
    @IgnoreAuth
    @PostMapping("login")
    @ApiOperation(value = "登录",notes = "登录说明")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType="string", name = "mobile", value = "手机号", required = true),
        @ApiImplicitParam(paramType = "query", dataType="string", name = "password", value = "密码", required = true)
    })
    public R login(String mobile, String password){
        Assert.isBlank(mobile, "手机号不能为空");
        Assert.isBlank(password, "密码不能为空");

        //用户登录
        long userId = userService.login(mobile, password);

        //生成token
        Map<String, Object> map = tokenService.createToken(userId);

        return R.ok(map);
    }

}
