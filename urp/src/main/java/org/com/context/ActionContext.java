package org.com.context;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.com.annotation.Controller;
import org.com.annotation.RequestMapping;
import org.com.servlet.ServletDispacher;
import org.com.util.ActionContextUtil;
/**
 * action上下文环境类
 * @author Smith
 *
 */
public class ActionContext {
	/**
	 * key：路径 value：method
	 */
	
	//是否线程安全？
	private static Map<String, ActionMetaData> controllerMap;
	private static List<Class<?>> loadBeanDefination;
	static{
		controllerMap=ActionContextUtil.loadActionData();
		try {
			loadBeanDefination= ActionContextUtil.loadBeanDefination();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Map<String, ActionMetaData> getControllerMap() {
		return controllerMap;
	}
	public static void setControllerMap(Map<String, ActionMetaData> controllerMap) {
		ActionContext.controllerMap = controllerMap;
	}
	public static List<Class<?>> getLoadBeanDefination() {
		return loadBeanDefination;
	}
	public static void setLoadBeanDefination(List<Class<?>> loadBeanDefination) {
		ActionContext.loadBeanDefination = loadBeanDefination;
	}
	public static ActionMetaData getActionMeta(String uriStr){
		return controllerMap.get(getRealUrl(uriStr));
	}
	public static String getRealUrl(String uriStr){
		if(uriStr.endsWith(".action")){
			return uriStr.substring(0, uriStr.lastIndexOf("."));
		}else{
			return null;
		}
	}
	//构造静态内部类,BeanDefination
	public static class ActionMetaData{
		private Class<?> actionClazz;
		private Method currentMethod;
		private Class<?>[] parameterTypes;
		
		public Class<?>[] getParameterTypes() {
			return parameterTypes;
		}
		public void setParameterTypes(Class<?>[] parameterTypes) {
			this.parameterTypes = parameterTypes;
		}
		public Class<?> getActionClazz() {
			return actionClazz;
		}
		public void setActionClazz(Class<?> actionClazz) {
			this.actionClazz = actionClazz;
		}
		public Method getCurrentMethod() {
			return currentMethod;
		}
		public void setCurrentMethod(Method currentMethod) {
			this.currentMethod = currentMethod;
		}
		
	}
}

