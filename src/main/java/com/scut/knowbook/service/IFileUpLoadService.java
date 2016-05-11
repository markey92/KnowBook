package com.scut.knowbook.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUpLoadService {

	public String FileUpload(MultipartFile file,String fileName) throws Exception;
	
	public String anotherFileUpload(MultipartFile file, String savePath,String fileName) throws IOException;
}
