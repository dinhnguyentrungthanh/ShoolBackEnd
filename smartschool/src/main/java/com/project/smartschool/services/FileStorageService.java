package com.project.smartschool.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

	String saveAvatar(MultipartFile file) throws IOException;
	
	String saveDocument(MultipartFile file) throws IOException;
	
	String saveTest(MultipartFile file) throws IOException;
	
	String saveImg(MultipartFile file) throws IOException;
	
}
