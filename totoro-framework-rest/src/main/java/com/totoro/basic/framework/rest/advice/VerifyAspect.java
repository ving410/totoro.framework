package com.totoro.basic.framework.rest.advice;

import com.totoro.basic.framework.core.exception.TotoroException;
import com.totoro.basic.framework.core.props.BaseProperties;
import com.totoro.basic.framework.model.dto.BaseRequest;
import com.totoro.basic.framework.rest.signature.SessionVerify;
import com.totoro.basic.framework.rest.signature.SessionVerifyMode;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 权限验证切面
 *
 * @author Maleah
 * @create 2017-10-26 11:33
 **/
@Aspect
@Component
@Order(-2)
@Slf4j
public class VerifyAspect {

    private final String ACCESS_TOKEN = "accessToken";
    private final String REQUEST_TIMESTAMP = "timestamp";
    private final String REQUEST_SIGNATURE = "signature";
    private final String REQUEST_SOURCE = "requestSource";

    /**
     * 是否开启验签
     */
    private static Boolean enableSign = true;

    /**
     *全局配置初始化
     */
    static {
        enableSign = BaseProperties.getProperty("framework.sign.enable", Boolean.class, true);
    }

    @Pointcut("execution(public * *..*Controller.*(..)) && @annotation(com.totoro.basic.framework.rest.signature.SessionVerify)")
    public void verify() {
        log.debug("verify");
    }

    @Around("@annotation(verifyAnnotation)")
    public Object handler(ProceedingJoinPoint pjp, SessionVerify verifyAnnotation) throws Throwable {
        if (!enableSign) {
            return pjp.proceed();
        }

        HttpServletRequest servletRequest = null;
        BaseRequest baseRequest = null;

        Object[] args = pjp.getArgs();
        for (Object obj : args) {
            if (servletRequest != null && baseRequest != null)
                break;

            if (obj instanceof HttpServletRequest) {
                servletRequest = (HttpServletRequest) obj;
            } else if (obj instanceof BaseRequest) {
                baseRequest = (BaseRequest) obj;
            }
        }
        if (servletRequest == null || baseRequest == null)
            throw new TotoroException(10000, "参数错误");

        verifyRequest(servletRequest, verifyAnnotation);

        return pjp.proceed();
    }

    private void verifyRequest(HttpServletRequest servletRequest, SessionVerify verifyAnnotation) {
        if (StringUtils.isBlank(servletRequest.getParameter(REQUEST_SIGNATURE))) {
            throw new TotoroException(10100, "signature is null");
        }
        if (StringUtils.isBlank(servletRequest.getParameter(ACCESS_TOKEN))) {
            throw new TotoroException(10100, "access_token is null");
        }
        String timestamp = servletRequest.getParameter(REQUEST_TIMESTAMP);
        if (StringUtils.isBlank(timestamp)) {
            throw new TotoroException(10100, "授权必须提供Unix式时间戳（timestamp）参数，如：1468296000");
        }
        /**
         * 30分钟过期
         */
        if (((Calendar.getInstance().getTimeInMillis() / 1000) - Long.parseLong(timestamp)) > 30 * 60) {
            throw new TotoroException(10100, "签名验证已过期~~~~");
        }

        SessionVerifyMode modes = verifyAnnotation.verifyMode();
        switch (modes) {
            case OPEN_API:
                verifyOpenApi(servletRequest);
                break;
            case BACKGROUND:
                break;
            case CITY:
                break;
        }

    }

    private void verifyOpenApi(HttpServletRequest servletRequest) {
        String signature = servletRequest.getParameter(REQUEST_SIGNATURE);
        String accessToken = servletRequest.getParameter(ACCESS_TOKEN);
        if (!validSecret(servletRequest.getRequestURI(), getParams(servletRequest), accessToken, signature))
            throw new TotoroException(10100, "认证错误~~~~");
    }

    private boolean validSecret(String path, String params, String accessToken, String signature) {
        String toSign = String.format("%s?%s%s", path, params, accessToken);
        try {
            String sign = DigestUtils.md5Hex(toSign.getBytes("utf8")).toLowerCase();
            return sign.equals(signature.toLowerCase());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getParams(HttpServletRequest request) {
        StringBuffer stringBuffer = new StringBuffer();
        SortedSet<String> params = new TreeSet<>();

        for (Map.Entry<String, String[]> t : request.getParameterMap().entrySet()) {
            if (t.getKey().equals(REQUEST_SIGNATURE)) {
                continue;
            }
            try {
                params.add(String.format("%s=%s", URLEncoder.encode(t.getKey(), "UTF-8"), URLEncoder.encode(request.getParameter(t.getKey()), "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                throw new TotoroException(10100, "URLEncoder 异常~~~~");
            }
        }

        params.forEach(n -> stringBuffer.append("&" + n));
        stringBuffer.delete(0, 1);
        return stringBuffer.toString();
    }
}
