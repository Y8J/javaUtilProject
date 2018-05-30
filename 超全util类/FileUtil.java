package com.zjht.youoil.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import com.zjht.youoil.common.file.FileNameUtils;
import com.zjht.youoil.common.web.Constants;

public class FileUtil {
	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

	public static String dealSaveFile(MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
		if (file == null || file.isEmpty()) {
			return "no file";
		}
		String fileName = null;
		String uploadpath = PropertyUtil.getProperty("youoil.upload.path");// /res/upload
		String real = request.getSession().getServletContext().getRealPath(uploadpath);
		String dateDir = Constants.SPT + FileNameUtils.genPathName();
		// 创建目录
		File root = new File(real, dateDir);
		if (!root.exists()) {
			root.mkdirs();
		}
		// 取文件名
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		fileName = FileNameUtils.genFileNameWithOutPath(ext);
		// 保存为临时文件
		File tempFile = new File(root, fileName);
		// 相对路径
		String relPath = dateDir + Constants.SPT + fileName;
		try {
			file.transferTo(tempFile);
		} catch (IllegalStateException e) {
			log.error("upload file error!", e);
			throw e;
		} catch (IOException e) {
			log.error("upload file error!", e);
			throw e;
		}
		return relPath;
	}

	/**
	 * 保存油站图片
	 * 
	 * @param code
	 * @param file
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String saveStationsFile(String code, MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
		if (file == null || file.isEmpty()) {
			return "no file";
		}
		String fileName = null;
		String uploadpath = PropertyUtil.getProperty("youoil.upload.path");// /res/upload
		String real = request.getSession().getServletContext().getRealPath(uploadpath);
		String dateDir = Constants.SPT + "stations";
		// 创建目录
		File root = new File(real, dateDir);
		if (!root.exists()) {
			root.mkdirs();
		}
		// 取文件名
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		fileName = code + "." + ext;
		// 保存为临时文件
		File tempFile = new File(root, fileName);
		// 相对路径
		String relPath = dateDir + Constants.SPT + fileName;
		try {
			file.transferTo(tempFile);
		} catch (IllegalStateException e) {
			log.error("upload file error!", e);
			throw e;
		} catch (IOException e) {
			log.error("upload file error!", e);
			throw e;
		}
		return relPath;
	}
}
