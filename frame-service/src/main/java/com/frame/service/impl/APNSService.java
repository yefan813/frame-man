package com.frame.service.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.frame.domain.common.RemoteResult;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import com.notnoop.apns.PayloadBuilder;
import com.notnoop.exceptions.NetworkIOException;

@Service("apnsService")
public class APNSService {
	private static final Logger LOGGER = LoggerFactory.getLogger(APNSService.class);
	
	
	@Value("${iosCertPassword}")
	private String iosCertPassword;
	
	@Value("${iosCertPath}")
	private String iosCertPath;
	
	public RemoteResult senPushNotification(List<String> deviceTokens, String content){
		RemoteResult msg = null;
		
		 long start = System.currentTimeMillis();
	 
	        // 创建和苹果APNS服务器的连接connection对象
	        ApnsServiceBuilder serviceBuilder = APNS.newService();
	        // 通过类加载器加载ios证书\
	        serviceBuilder.withCert(APNSService.class.getClassLoader().getResourceAsStream(iosCertPath), iosCertPassword);
	        // 设定为推送正式产品环境
	        serviceBuilder.withSandboxDestination();
	        // 从builder到service对象
	        ApnsService service = serviceBuilder.build();
	        String sound = "default.mp3";// 默认响铃文件名称
	        int badge = 5;// 小红点数字
	        PayloadBuilder payloadBuilder = APNS.newPayload();
	        payloadBuilder.badge(badge);
	        payloadBuilder.sound(sound);
	        try {
	            // 尝试推送10条消息内容不同的消息
                String payload = payloadBuilder.alertBody(content).build();
                LOGGER.info(service.push(deviceTokens, payload).toString());
	        } catch (NetworkIOException e) {
	        	LOGGER.error("推送消息到苹果APNS服务器遇到网络异常");
	            e.printStackTrace();
	        } catch (Exception ex) {
	        	LOGGER.error("推送消息到苹果APNS服务器错误");
	            ex.printStackTrace();
	        } finally {
	        	LOGGER.info("推送成功，推送列表为:");
	            for (String t : deviceTokens) {
	                System.out.println(t);
	            }
	            LOGGER.info("耗时为：" + (System.currentTimeMillis() - start) + "ms");
	        }
	 
	        Map<String, Date> inactiveDevices = service.getInactiveDevices();
	        for (String deviceToken : inactiveDevices.keySet()) {
	            Date inactiveOf = inactiveDevices.get(deviceToken);
	            LOGGER.info(inactiveOf.toString());
	        }
	 
	        // 推送完毕后关闭service连接
	        service.stop();
		
		
		return msg;
	}
}
