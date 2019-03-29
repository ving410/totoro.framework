package com.totoro.basic.framework.common.util;


import com.totoro.basic.framework.core.consts.SysErrorConsts;
import com.totoro.basic.framework.core.exception.SysException;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 封装BeanUtils的异常及增加copy单个对象的功能
 * @Author Maleah
 * @Date 2018-01-12
 */
public class TotoroBeanUtils {

    /**
     * 处理BeanUtils.copyProperties()的异常及非空值不复制
     * @param dest
     * @param orig
     */
    public static void copy(Object orig, Object dest){
        try {
            BeanUtils.copyProperties(dest,orig,true);
        } catch (IllegalAccessException e) {
            throw new SysException(90000,"系统异常",e);
        } catch (InvocationTargetException e) {
            throw new SysException(90000,"系统异常",e);
        }
    }

    /**
     * 处理BeanUtils.copyProperties()的异常
     * @param dest
     * @param orig
     */
    public static void copyWithNull(Object orig, Object dest){
        try {
            BeanUtils.copyProperties(dest,orig);
        } catch (IllegalAccessException e) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE,"系统异常",e);
        } catch (InvocationTargetException e) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE,"系统异常",e);
        }
    }
    /**
     * 基于cglib进行对象复制
     *
     * @param source 被复制的对象
     * @param clazz  复制对象类型
     * @return
     */
    public static <T> T copy(Object source, Class<T> clazz) {
        if (isEmpty(source)) {
            return null;
        }
        T target = instantiate(clazz);
        BeanCopier copier = BeanCopier.create(source.getClass(), clazz, false);
        copier.copy(source, target, null);
        return target;
    }

    /**
     * 基于cglib进行对象组复制
     *
     * @param datas 被复制的对象数组
     * @param clazz 复制对象
     * @return
     */
    public static <T> List<T> copyList(List<?> datas, Class<T> clazz) {
        if(datas==null)return null;
        if (isEmpty(datas)) {
            return new ArrayList<>(0);
        }
        List<T> result = new ArrayList<>(datas.size());
        for (Object data : datas) {
            result.add(copy(data, clazz));
        }
        return result;
    }

    public static <T> T instantiate(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException ex) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, clazz + ":Is it an abstract class?", ex);
        } catch (IllegalAccessException ex) {
            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, clazz + ":Is the constructor accessible?", ex);
        }
    }

    private static <T> boolean isEmpty(T t) {
        if (t == null) {
            return true;
        }
        if (StringUtils.isEmpty(t.toString())) {
            return true;
        }
        return false;
    }
}
