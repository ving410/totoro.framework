package com.totoro.basic.framework.rest.config;

import com.totoro.basic.framework.core.exception.SysException;
import com.totoro.basic.framework.model.dto.BaseResponse;
import com.totoro.basic.framework.model.rest.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 统一异常处理
 *
 * @author Maleah
 * @create 2017-10-25 19:10
 **/

@RestControllerAdvice
@Slf4j
public class GlobalExceptionResolver {

    @ExceptionHandler(value = Exception.class)
    public DataResult<BaseResponse> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        if (e instanceof SysException) {
            SysException syse = (SysException) e;
            log.error(String.format("url:%s, case:%s", getReqUrl(req), e.getMessage()), e);
            return buildErrorResponse(syse.getErrorCode(), syse.getMessage());
        } else {
            log.error(String.format("url:%s, case:%s", getReqUrl(req), e.getMessage()), e);
            return buildErrorResponse(90000, "程序异常，请稍后重试!");
        }

    }

    private String getReqUrl(HttpServletRequest req){
        try {
            String path = req.getRequestURI();
            String queryStr = req.getQueryString();
            if (queryStr != null) {
                path += "?" + req.getQueryString();
            }
            return path;
        }catch (Exception ex){
            return ex.getMessage();
        }
    }

    private DataResult buildErrorResponse(int status,String message){
        DataResult<BaseResponse> result = new DataResult<>();
        result.setStatus(status);
        result.setMessage(message);
        result.setTimestamp(new Date());
        return result;
    }
}
