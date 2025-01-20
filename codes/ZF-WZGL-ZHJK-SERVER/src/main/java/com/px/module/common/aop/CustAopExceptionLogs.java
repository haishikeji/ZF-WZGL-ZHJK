package com.px.module.common.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.px.module.common.utils.StringUtil;
import com.px.module.common.utils.TokenUtil;
import com.px.module.common.utils.UUIDUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Package com.module.base.common.aop
 * @ClassName CustAopParamLogs
 * @author 品讯科技
 * @Description
 *          aop异常处理
 * @date 2024-08
 */
@Aspect
@Component
public class CustAopExceptionLogs {
    private Logger logger = LogManager.getLogger(CustAopExceptionLogs.class);

    /**
     *
     * @Title: custThrowing
     * @Description: 异常记录
     * @author 品讯科技
     * @param joinPoint
     * @param error
     * @throws
     */
    @AfterThrowing(value = "execution(* com.px.module.*..*(..))", throwing = "error")
    public void custThrowing(JoinPoint joinPoint, Throwable error) {
        String requestUUID = UUIDUtil.get32UUID().toUpperCase();
        JSONObject requestUser = new JSONObject ();
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        //获取传入参数
        HttpServletRequest request = sra == null ? null : sra.getRequest();
        //传入参数json
        Object[] params = joinPoint.getArgs();
        String inputparam = "";
        //判断json入参
        JSONArray inputParamArray = new JSONArray ();
        for(int i = 0 ; i < params.length ; i ++){
            //String tempArg = params[i].toString();
            //inputparam += tempArg + ";";
            if (!(params[i] instanceof BindingResult) && !(params[i] instanceof HttpServletResponse)){
                inputParamArray.add(params[i]);
            }
        }
        List<JSONObject> errorJsonList=new ArrayList<> ();
        //异常对象
        for(StackTraceElement stackTraceElement:error.getStackTrace()){
            JSONObject errorJson=new JSONObject();
            if(joinPoint.getTarget().getClass().getName ().equals (stackTraceElement.getClassName ())){
                errorJson.put ("exceptionClass",stackTraceElement.getClassName ());
                errorJson.put ("exceptionLine",stackTraceElement.getLineNumber ());
                errorJsonList.add (errorJson);
            }

        }
        //请求地址IP
        String ip = "";
        if(null != request){
            ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP");
            String token = request.getHeader("Authorization");
            if(StringUtil.isNotBlank(token)&& !"null".equals(token)){
                requestUser.put("requestUserId", TokenUtil.getUserId(token));
                //requestUser.put("requestUserName", TokenUtil.getUserName(token));
            }
        }
        //class名称
        String targetClass = joinPoint.getTarget().getClass().toString();
        //方法名称
        String tartgetmethodd = joinPoint.getSignature().getName();
        //异常名称
        String execption = error.getClass().toString();
        //异常信息
        String messages = error.getMessage();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //异常出现时间
        String errTime = sdf.format(new Date());
        //异常信息json
        JSONObject execptionJson = new JSONObject ();
        //异常出现行号
        execptionJson.put("exceptionType", execption);
        execptionJson.put("exceptionMsg", messages);
        execptionJson.put("exceptionTime", sdf.format(new Date()));
        execptionJson.put("exceptionJson",errorJsonList);
        JSONObject errJson = new JSONObject ();
        errJson.put("Request_UUID", requestUUID);
        errJson.put("Request_User", requestUser);
        errJson.put("Request_IP", ip);
        errJson.put("Request_Log_Type", "ele-ExceptionLogs");
        //errJson.put("className",targetClass);
        errJson.put("Request_Method", targetClass + "." + tartgetmethodd);
        errJson.put("Request_Time", errTime);
        errJson.put("Request_Data", inputParamArray);
        errJson.put("Response_Data", "exception");
        errJson.put("Response_Exception", execptionJson);
        String errJsonStr = JSON.toJSONString(errJson, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        logger.error(errJsonStr);
    }
}
