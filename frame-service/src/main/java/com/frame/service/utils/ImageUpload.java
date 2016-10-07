package com.frame.service.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.frame.common.tools.DateUtil;
import com.frame.domain.img.ImgDealMsg;


@Service("imageUpload")
public class ImageUpload {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpYun.class);
	private String  uploadPath;
	//@Resource private TaskExecutor taskExecutor; 
	
	public String getUploadPath() {
		return uploadPath;
	}
	
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	
	private PictureUtils pictureUtils;
	

	public PictureUtils getPictureUtils() {
		return pictureUtils;
	}

	public void setPictureUtils(PictureUtils pictureUtils) {
		this.pictureUtils = pictureUtils;
	}

	public ImgDealMsg uploadFile(byte[] img, String path) throws Exception {
		File dir = new File(uploadPath);
		if (!dir.exists() && StringUtils.isNotEmpty(System.getProperty("iceasyweb.root"))) {
			uploadPath = System.getProperty("iceasyweb.root");
			dir = new File(uploadPath);
		}
		ImgDealMsg imgDealMsg = new ImgDealMsg();
		
		if (!(dir.isDirectory() && dir.canWrite() && dir.canRead())) {
//			throw new Exception("服务器图片存储目录权限非可读可写,图片存储目录路径为" + dir.getAbsolutePath());
			dir.mkdirs();
		}
		String monthDirStr = DateUtil.format(new Date(), "yyyyMM");
		File monthDir = new File(uploadPath + "/" + monthDirStr);
		if (!monthDir.exists()) {
			boolean isSuccess = monthDir.mkdir();
			if (!isSuccess) {
				throw new Exception("服务器创建每月的图片存储目录失败,创建按月图片存储目录路径为" + monthDir.getAbsolutePath());
			}
		}
		
		if (!(monthDir.isDirectory() && monthDir.canWrite() && monthDir.canRead())) {
			throw new Exception("服务器每月的图片存储目录非可读可写,按月图片存储目录路径为" + monthDir.getAbsolutePath());			
		}
		
		String imgFileName = String.valueOf(UUID.randomUUID());
		File imgFile = new File(uploadPath + "/" + monthDirStr + "/" + imgFileName);
		
		//写文件
		FileOutputStream fos = null;
		try {
			fos=new FileOutputStream(imgFile);
			 // 给图片添加水印
	       // String iconPath =  new ClassPathResource("water.png").getFile().getAbsolutePath();
	        fos.write(img);   
	        fos.flush(); 
	        //文件上传到又拍云
	        if (!path.equalsIgnoreCase("common")) {
	        	path = path + "/" + monthDirStr + "/";
	        } else {
	        	path = path + "/";
	        }
	        String youPaiUrl = pictureUtils.upload(imgFile, path);
	        if (StringUtils.isNotBlank(youPaiUrl)) {
	        	LOGGER.error("上传图片到又拍成功"+youPaiUrl);
	        	//图片存储成功
				imgDealMsg.setId("1");
				imgDealMsg.setMsg(youPaiUrl);
				return imgDealMsg;
	        }
	        return null;
	      //  ImageUtil.waterMarkImageByIcon(iconPath, imgFile.getAbsolutePath(), imgFile.getAbsolutePath(), 0, 0, 0, 0.1f);
		} catch (Exception e){  
			throw new Exception("服务器写图片失败，图片路径为" + imgFile.getAbsolutePath(), e);
		}finally{   
            try{   
            	if(fos != null){
            		fos.close();   
            	}
            }   
            catch(IOException iex){
            	
            }   
        }   
	}
	
    @SuppressWarnings("resource")
	public static byte[] fileToByteArray(File file)throws IOException{  
        FileChannel fc = null;  
        try{  
            fc = new RandomAccessFile(file,"r").getChannel();  
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();  
            System.out.println(byteBuffer.isLoaded());  
            byte[] result = new byte[(int)fc.size()];  
            if (byteBuffer.remaining() > 0) {  
                byteBuffer.get(result, 0, byteBuffer.remaining());  
            }  
            return result;  
        }catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        }finally{  
            try{  
            	if(fc != null){
            		fc.close();  
            	}
            }catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
    }
	
	public ImgDealMsg uploadFile(File file) throws Exception {
		byte[] fileBytes = fileToByteArray(file);
		return this.uploadFile(fileBytes, "");
	}

	public List<ImgDealMsg> uploadFile(List<File> files) throws Exception {
		List<ImgDealMsg> imgMsgs = new ArrayList<ImgDealMsg>();
		if (files != null && !files.isEmpty()) {
			for (File file : files) {
				ImgDealMsg imgDealMsg = this.uploadFile(file);
				imgMsgs.add(imgDealMsg);
			}
		}
		return imgMsgs;
	}

}
