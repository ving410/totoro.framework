package com.totoro.basic.framework.rest.signature;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SessionVerify {
    SessionVerifyMode verifyMode() default SessionVerifyMode.OPEN_API;
}
