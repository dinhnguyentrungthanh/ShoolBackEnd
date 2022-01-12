package com.project.smartschool.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.smartschool.services.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

	@Value("${local.storage.avatar}")
	private String AVATAR;

	@Value("${local.storage.document}")
	private String DOCUMENT;

	@Value("${local.storage.test}")
	private String TEST;
	
	@Value("${local.storage.img}")
	private String IMG;

	@Override
	public String saveAvatar(MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			return saveFile(AVATAR, file);
		}
		return null;

	}

	@Override
	public String saveDocument(MultipartFile file) throws IOException {
		return saveFile(DOCUMENT, file);
	}

	@Override
	public String saveTest(MultipartFile file) throws IOException {
		return saveFile(TEST, file);
	}

	private String saveFile(String path, MultipartFile file) throws IOException {
		Path root = Paths.get(path);

		String filename = file.getOriginalFilename();
		String extention = filename.substring(filename.lastIndexOf("."));
		String finalFile = UUID.randomUUID() + extention;

		if (!Files.exists(root))
			Files.createDirectory(root);

		Files.copy(file.getInputStream(), root.resolve(finalFile));
		return finalFile;
	}

	@Override
	public String saveImg(MultipartFile file) throws IOException {
		return saveFile(IMG, file);
	}

}
