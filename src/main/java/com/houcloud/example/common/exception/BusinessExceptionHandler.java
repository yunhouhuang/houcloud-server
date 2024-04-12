package com.houcloud.example.common.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.houcloud.example.common.notice.dingtalk.DingTalkMessage;
import com.houcloud.example.common.result.Result;
import com.houcloud.example.ext.wechat.exception.WechatException;
import com.houcloud.example.utils.IpUtil;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

/**
 * <p> 全局异常处理器 </p>
 *
 * @author Houcloud
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class BusinessExceptionHandler {


    @Value("${spring.profiles.active:none}")
    private String env;

    /**
     * 自定义异常处理
     *
     * @param exception e
     * @return R
     */
    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> businessExceptionHandler(BusinessException exception) {
        log.warn("业务异常\n{}", exception.getMessage());
        return Result.fail(exception.getCode(), exception.getMessage());
    }


    @ExceptionHandler({WechatException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> businessExceptionHandler(WechatException exception) {
        log.warn("微信业务异常\n{}", exception.getMessage());
        return Result.fail(exception.getCode(), exception.getMessage());
    }

    /**
     * validation Exception
     *
     * @param exception
     * @return R
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<Void> handleBodyValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        log.warn("参数绑定异常\n{}", fieldErrors.get(0).getDefaultMessage());
        return Result.fail(fieldErrors.get(0).getDefaultMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<Void> handleBodyValidException(MissingServletRequestParameterException exception) {
        String name = exception.getParameterName();
        log.warn("请求参数{}异常", name, exception);
        return Result.fail("请求缺少" + name + "参数");
    }

    /**
     * validation Exception
     *
     * @param exception
     * @return R
     */
    @ExceptionHandler({MysqlDataTruncation.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<Void> handleBodyValidException(MysqlDataTruncation exception) {
        log.warn("MysqlDataTruncation\n{}", exception.getMessage());
        if (exception.getMessage().contains("value is out of range in")) {
            if (exception.getMessage().contains("stock")) {
                return Result.fail("库存不足");
            }
            return Result.fail("数值超出范围");
        }
        return Result.fail("数据限制异常");
    }

    /**
     * validation Exception
     *
     * @param exception
     * @return R
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<Void> handleBodyValidException(MethodArgumentTypeMismatchException exception) {
        String name = exception.getName();
        log.warn("请求参数{}异常", name, exception);
        return Result.fail("参数" + name + "类型校验错误");
    }

    /**
     * validation Exception
     *
     * @param exception
     * @return R
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<?> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.warn("参数绑定异常\n{}", exception.getHttpInputMessage());
        exception.printStackTrace();
        return Result.fail("参数校验错误", exception.getMessage());
    }

    /**
     * validation Exception (以form-data形式传参)
     *
     * @param exception
     * @return R
     */
    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> bindExceptionHandler(BindException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        log.warn("参数异常\n{}", fieldErrors.get(0).getDefaultMessage());
        return Result.fail("参数校验异常", fieldErrors.get(0).getDefaultMessage());
    }

    /**
     * validation Exception (以form-data形式传参)
     *
     * @param exception
     * @return R
     */
    @ExceptionHandler({InvalidFormatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> invalidFormatExceptionHandler(InvalidFormatException exception) {
        Object value = exception.getValue();
        log.warn("参数验证异常\n{}", value);
        return Result.fail("参数验证异常", value.toString() + exception.getMessage());
    }

    /**
     * http请求无效处理
     *
     * @param exception
     * @return R
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<Void> httpRequestExceptionHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException exception) {
        String method = exception.getMethod();
        log.warn("请求 {} 不支持{}方法", request.getRequestURL(), method);
        return Result.fail(method + "方法请求无效");
    }

    /**
     * http请求无效处理
     *
     * @param exception
     * @return R
     */
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> runtimeException(HttpServletRequest request, RuntimeException exception) {
        senDingTalkExceptionMessag(request, exception);
        log.warn("异常类:{}", exception.getClass());
        log.warn("系统繁忙", exception);
        return Result.fail(BusinessStatus.SERVICE_BUSY.getCode(), BusinessStatus.SERVICE_BUSY.getMessage(), exception.getMessage());
    }


    /**
     * http请求无效处理
     *
     * @param exception
     * @return R
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> unknownException(HttpServletRequest request, Exception exception) {
        senDingTalkExceptionMessag(request, exception);
        log.warn("异常类:{}", exception.getClass());
        log.warn("未知异常", exception);
        return Result.fail(BusinessStatus.UNKNOWN.getCode(), BusinessStatus.UNKNOWN.getMessage(), exception.getMessage());
    }


    private void senDingTalkExceptionMessag(HttpServletRequest request, Exception e) {
        String envName = "未知环境";
        if ("dev".equals(env)) {
            envName = "开发环境";
        }
        if ("prod".equals(env)) {
            envName = "线上环境";
        }
        String requestInfo = "";
        String ip = "";
        try {
            requestInfo = request.getRemoteHost() + "/" + request.getMethod() + "-" + request.getRequestURI();
            ip = IpUtil.getRealIp(request);
        } catch (Exception e2) {
            log.error("无法请求信息");
        }
        String message = envName + "\n" + requestInfo + "\n" + ip + "\n【" + e.getMessage() + "\n】";
        DingTalkMessage.sendExceptionMessage(message, e);
    }

}
