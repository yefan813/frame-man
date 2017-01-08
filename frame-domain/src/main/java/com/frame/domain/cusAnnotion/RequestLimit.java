package com.frame.domain.cusAnnotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* @author 作者 E-mail: 
* @version 创建时间：2016年11月10日 
* 类说明 
*/

@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)  
@Inherited  
@Documented  
public @interface RequestLimit {  
  
    /** 
     * ip允许访问的次数，默认值1000 
     */  
    int ipCount() default 20;  
  
    /** 
     * ip时间段，单位为毫秒，默认值一分钟 
     */  
    long ipTime() default 60000;  
      
    /** 
     * uri允许访问的次数，默认值600 
     */  
    int uriCount() default 20;  
  
    /** 
     * uri时间段，单位为毫秒，默认值一分钟 
     */  
    long uriTime() default 60000;  
  
}  
