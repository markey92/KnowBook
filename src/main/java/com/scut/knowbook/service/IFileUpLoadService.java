package com.scut.knowbook.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUpLoadService {

	public String FileUpload(MultipartFile file,String fileName) throws Exception;
}
