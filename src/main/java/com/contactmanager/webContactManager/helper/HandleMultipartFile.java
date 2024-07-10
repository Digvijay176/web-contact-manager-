package com.contactmanager.webContactManager.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class HandleMultipartFile {

	private final String BASE_URL="C:\\Users\\digvi\\OneDrive\\Desktop\\ecommerce website project\\webContactManager\\src\\main\\resources\\static\\user-image";
	
	public boolean addFile(MultipartFile file) throws IOException {
		boolean upload =false;
		
		InputStream is = file.getInputStream();
		byte [] b = new byte[is.available()];
		is.read(b);
		
		FileOutputStream fos = new FileOutputStream(BASE_URL+File.separator+file.getOriginalFilename());
		fos.write(b);
		upload=true;
		
		return upload;
		
	}
}
