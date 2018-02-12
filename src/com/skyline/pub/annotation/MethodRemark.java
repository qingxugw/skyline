package com.skyline.pub.annotation;

import com.skyline.pub.utils.enums.ModuleEnum;
import com.skyline.pub.utils.enums.OpTypeEnum;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-18
 * Time: 下午2:19
 * To change this template use File | Settings | File Templates.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MethodRemark {
    OpTypeEnum opType() default OpTypeEnum.无操作类型;
    ModuleEnum module() default ModuleEnum.无模块;
}
