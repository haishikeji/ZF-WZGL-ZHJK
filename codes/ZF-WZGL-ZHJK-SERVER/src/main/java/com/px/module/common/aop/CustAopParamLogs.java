package com.px.module.common.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.px.module.common.base.Result;
import com.px.module.common.base.ReturnMsgEnum;
import com.px.module.common.utils.StringUtil;
import com.px.module.common.utils.TokenUtil;
import com.px.module.common.utils.UUIDUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 品讯科技
 * @Package com.module.base.common.aop
 * @ClassName CustAopParamLogs
 * @Description aop日志处理
 * @date 2024-08
 */
@Aspect
@Component
public class CustAopParamLogs {
    private Logger logger = LogManager.getLogger(CustAopParamLogs.class);

    /**
     * @param pjp
     * @return
     * @throws Throwable
     * @throws
     * @Title: recodInParam
     * @Description: controller 日志记录
     * @author 品讯科技
     */
    @Around(value = "execution(* com.px.module.*.controller..*(..))")
    public Object recodInParam(ProceedingJoinPoint pjp) throws Throwable {
        String requestUUID = UUIDUtil.get32UUID().toUpperCase();
        JSONObject requestUser = new JSONObject();
        logger.info("------------------------请求开始[" + requestUUID + "]---------------------");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        String targetClass = pjp.getTarget().getClass().toString();
        String tartgetmethodd = pjp.getSignature().getName();
        HttpServletRequest request = sra == null ? null : sra.getRequest();
        //传入参数
        Object[] args = pjp.getArgs();
        String inputparam = "";
        //验证参数结果
        BindingResult bindingResult = null;
        //判断json入参
        JSONArray inputParamArray = new JSONArray();
        for (int i = 0; i < args.length; i++) {
            //String tempArg = args[i].toString();
            //inputparam += tempArg + ";";
            if (args[i] instanceof BindingResult) {
                bindingResult = (BindingResult) args[i];
                if (bindingResult.hasErrors()) {
                    String errorMsg = bindingResult.getFieldError().getDefaultMessage();
                    return Result.buildResult(ReturnMsgEnum.FAILURE, errorMsg);
                }
            } else if (args[i] instanceof HttpServletResponse) {

            } else {
                inputParamArray.add(args[i]);
            }
        }
        //inputparam = inputParamArray.toJSONString();
        try {
            inputparam = JSON.toJSONString(inputParamArray, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        } catch (Exception e) {
            inputparam = "";
        }

        //回参
        Object outParam = pjp.proceed();
        //请求地址IP
        String ip = "";
        if (null != request) {
            ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP");
            String token = request.getHeader("Authorization");
            if(StringUtil.isNotBlank(token)&& !"null".equals(token)){
                requestUser.put("requestUserId", TokenUtil.getUserId(token));
                //requestUser.put("requestUserName", TokenUtil.getUserName(token));
            }
        }
        JSONObject infoLogJson = new JSONObject();
        infoLogJson.put("Request_UUID", requestUUID);
        infoLogJson.put("Request_User", requestUser);
        infoLogJson.put("Request_IP", ip);
        infoLogJson.put("Request_Log_Type", "ele-log"); //日志类型为 controller info
        infoLogJson.put("Request_Time", sdf.format(new Date()));
        infoLogJson.put("Request_Method", targetClass + "." + tartgetmethodd);
        infoLogJson.put("Request_Data", inputParamArray);
        infoLogJson.put("Response_Data", outParam);
        //infoLogJson.put("exception", new JSONObject());
        String jsonString = "";
        try {
            jsonString = JSON.toJSONString(infoLogJson, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        } catch (Exception e) {

        }

        //String jsonString = infoLogJson.toJSONString();
        logger.info(jsonString);
        logger.info("------------------------请求结束[" + requestUUID + "]---------------------");
        return outParam;
    }

    /**
     * @param pjp
     * @return
     * @throws Throwable
     * @throws
     * @Title: oldPadCheckToken
     * @Description: 获取老平板请求头中的token，并进行验证
     * @author 品讯科技
     */
    @Around(value = "execution(* com.px.module.*.controller.HorizontalScreenPadApiCalledController..*(..))")
    public void oldPadCheckToken(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        String tartgetmethodd = pjp.getSignature().getName();
        HttpServletRequest request = sra == null ? null : sra.getRequest();
        HttpServletResponse response = sra == null ? null : sra.getResponse();
        String token = request.getHeader("Authorization");

        /*if (!"checkVersion".equals(tartgetmethodd) && !"login".equals(tartgetmethodd) && !"padAuthorization".equals(tartgetmethodd)&& !"findAdvertisingInfo".equals(tartgetmethodd)) {
            //验证token
            boolean verify = TokenUtil.verifyTokenWithRedis(token);
//            boolean b = false;
            if (!verify) {
                response.setContentType("application/json;charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("CODE", "10003");
                jsonObject.put("MSG", "登录过期");
                if ("posCorrect".equals(tartgetmethodd)) {
                    response.getWriter().print(jsonObject.toJSONString());
                    return;
                } else {
                    String type = request.getHeader("type");
                    String version = request.getHeader("version");
                    //获取链路加密
                    HandleMsgSecurity handleMsgSecurity = this.getSecurity(type, version);
                    //解密
                    JSONObject paramJson = JsonUtil.getJsonObject(request, response, handleMsgSecurity);
                    response.getWriter().print(handleMsgSecurity.EncodeReturnMsg(jsonObject.toJSONString(), paramJson.getString("authPassword")));
                    return;
                }
            }
        }*/
        //传入参数
//        pjp.getArgs();

        //回参
        pjp.proceed();
//        response.getWriter().print(outParam.toString());
    }

}
