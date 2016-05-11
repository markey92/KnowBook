package com.scut.knowbook.service.impl;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scut.knowbook.service.IFileUpLoadService;

@Service("fileUpLoadService")
public class FileUpLoadServiceImpl implements IFileUpLoadService {

	public String FileUpload(MultipartFile file,String fileName) throws IOException {
		// TODO Auto-generated method stub
		if(!file.isEmpty()){
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File("e:\\javass\\knowbook\\src\\main\\webapp\\static\\images", fileName));
			file.transferTo(new File(fileName)); 
			file.getInputStream().close();
		}
		else{
			return null;
		}
		String url="/images/"+fileName;
		return url;
	}

	public String anotherFileUpload(MultipartFile file, String savePath, String fileName) throws IOException {
		// TODO Auto-generated method stub
		if(!file.isEmpty()){
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(savePath, fileName));
			file.transferTo(new File(fileName)); 
			file.getInputStream().close();
		}
		else{
			return null;
		}
		String url="/images/"+fileName;
		return url;
	}

}
