package com.skyline.pub.aspectj.advice;

import com.skyline.base.service.LogService;
import com.skyline.pub.annotation.MethodRemark;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: skyline
 * Date: 12-9-19
 * Time: 上午9:24
 * To change this template use File | Settings | File Templates.
 */
@Aspect
@Component
public class LogAdvice {
    private static Logger logger = Logger.getLogger(LogAdvice.class.getName());
    public LogAdvice() {
    }
    private LogService logService;

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    @AfterReturning(value = "com.skyline.pub.aspectj.pointcut.PointCutsDefinition.pointCutServiceImpl() && @annotation(remark)")
    public void insertLog(JoinPoint jp,MethodRemark remark){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        String className = jp.getTarget().getClass().toString();
        String methodName = jp.getSignature().getName();   //获得方法名
        Object[] args = jp.getArgs();  //获得参数列表
        Class clazz = jp.getTarget().getClass();
        try {
            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new ClassClassPath(clazz)); //在servlet容器中启动
            CtClass cc = pool.get(clazz.getName());
            CtMethod cm = cc.getDeclaredMethod(methodName);
            // 使用javaassist的反射方法获取方法的参数名
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                    .getAttribute(LocalVariableAttribute.tag);
            if (attr == null) {
            }
            String[] paramNames = new String[cm.getParameterTypes().length];
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < paramNames.length; i++){
                paramMap.put(attr.variableName(i + pos),args[i]);
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (Exception ce){
            ce.printStackTrace();
        }
        logService.insertLog(className,methodName,paramMap,remark);
        logger.info("[logService]保存操作日志");
    }
}
