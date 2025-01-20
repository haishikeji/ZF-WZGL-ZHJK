package com.px.module.wisdomCdc.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.px.module.common.exception.RequestException;
import com.px.module.wisdomCdc.dto.LoginDto;
import com.px.module.wisdomCdc.entity.UserModel;
import com.px.module.wisdomCdc.service.IAccountService;
import com.px.module.common.base.Result;
import com.px.module.common.base.ReturnMsgEnum;
import io.swagger.annotations.Api;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;


@RestController
@RequestMapping("/account")
@Api(tags = "AccountController", description = "对Account的业务处理controller")
public class AccountController {

    @Resource(name="AccountServiceImpl")
    IAccountService accountService;

    @ApiIgnore
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody @Validated LoginDto loginDto, BindingResult result) {
        JSONObject sign = null;
        try {
            sign = accountService.signIn(loginDto);
        } catch (RequestException e) {
            if (ReturnMsgEnum.Sing_In_Input_Empty.getMsg().equals(e.getMsg())) {
                return Result.buildResult(ReturnMsgEnum.Sing_In_Input_Empty);
            } else if(ReturnMsgEnum.Login_Error.getMsg().equals(e.getMsg())) {
                return Result.buildResult(ReturnMsgEnum.Login_Error);
            } else if(ReturnMsgEnum.User_Locking.getMsg().equals(e.getMsg())) {
                return Result.buildResult(ReturnMsgEnum.User_Locking);
            }
        } catch (Exception e) {
            return Result.buildResult(ReturnMsgEnum.Login_Err);
        }
        return Result.SUCCESS(sign);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@Validated @RequestBody UserModel userModel, BindingResult result) {
        return accountService.userRegister(userModel);
    }

}
