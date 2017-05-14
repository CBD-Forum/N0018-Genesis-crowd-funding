package com.fbd.admin.common.mail;

import java.util.Collection;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
//该注解标示该类为切面类   
public class LogAspect {

	// 标注该方法体为后置通知，当目标方法执行成功后执行该方法体
	@AfterReturning("within(com.fbd.admin..*) && @annotation(rl)")
	public void addLogSuccess(JoinPoint jp, Log rl) {
	    
	    Object[] parames = jp.getArgs();// 获取目标方法体参数
        String params = parseParames(parames); // 解析目标方法体的参数
        String className = jp.getTarget().getClass().toString();// 获取目标类名
        className = className.substring(className.indexOf("com"));
        String signature = jp.getSignature().toString(); // 获取目标方法签名
        String methodName = signature.substring(signature.lastIndexOf(".") + 1, signature.indexOf("("));
        String modelName = rl.model(); // 根据类名获取所属的模块
        
        System.out.println("=============="+modelName);
		
	}

	// 标注该方法体为异常通知，当目标方法出现异常时，执行该方法体
	@AfterThrowing(pointcut = "within(com.main..*) && @annotation(rl)", throwing = "ex")
	public void addLog(JoinPoint jp, Log rl, Exception ex) {

		Object[] parames = jp.getArgs();// 获取目标方法体参数
		String params = parseParames(parames); // 解析目标方法体的参数
		String className = jp.getTarget().getClass().toString();// 获取目标类名
		className = className.substring(className.indexOf("com"));
		String signature = jp.getSignature().toString(); // 获取目标方法签名
		String methodName = signature.substring(signature.lastIndexOf(".") + 1, signature.indexOf("("));
		String modelName = rl.model(); // 根据类名获取所属的模块

	}

	/**
	 * 解析方法参数
	 * 
	 * @param parames
	 *            方法参数
	 * @return 解析后的方法参数
	 */
	private String parseParames(Object[] parames) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < parames.length; i++) {
			if (parames[i] instanceof Object[] || parames[i] instanceof Collection) {
				JSONArray json = JSONArray.fromObject(parames[i]);
				if (i == parames.length - 1) {
					sb.append(json.toString());
				} else {
					sb.append(json.toString() + ",");
				}
			}else if (parames[i] instanceof String) {
				sb.append(parames[i]);
			} else {
				JSONObject json = JSONObject.fromObject(parames[i]);
				if (i == parames.length - 1) {
					sb.append(json.toString());
				} else {
					sb.append(json.toString() + ",");
				}
			}
		}
		String params = sb.toString();
		params = params.replaceAll("(\"\\w+\":\"\",)", "");
		params = params.replaceAll("(,\"\\w+\":\"\")", "");
		return params;
	}

}
