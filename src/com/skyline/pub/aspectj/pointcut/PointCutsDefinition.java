package com.skyline.pub.aspectj.pointcut;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-18
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
@Aspect
@Component
public class PointCutsDefinition {

    public PointCutsDefinition() {
    }

    @Pointcut("execution(public * com.skyline.*.service.impl.*.*(..))")
    public void pointCutServiceImpl(){
    }
}
