package com.zjht.youoil.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.zjht.youoil.common.file.FileWrap;
/**
 * 模板工具类
 * @author dengbaohua
 *
 */
public class TemplateUtils {
	private static final Logger log = LoggerFactory.getLogger(TemplateUtils.class);

	private static FileFilter resFilter = new FileFilter() {
		@Override
		public boolean accept(File paramFile) {
			if (paramFile.isDirectory())
				return true;
			String str = FilenameUtils.getExtension(paramFile.getName());
			return FileWrap.allowEdit(str);
		}
	};

	public static FileWrap getTplFileWrap(String s, String rootPath) {
		FileWrap filewrap = new FileWrap(new File(s), s);
		filewrap.setName(rootPath);
		return filewrap;
	}
	
    public static FileWrap getTplFileWrap(String s, String rootPath,FileFilter fileFilter) {
        FileWrap filewrap = new FileWrap(new File(s), s,fileFilter);
        filewrap.setName(rootPath);
        return filewrap;
    }

	public static FileWrap getResFileWrap(String s, String rootPath) {
		FileWrap filewrap = new FileWrap(new File(s), s, resFilter);
		filewrap.setName(rootPath);
		return filewrap;
	}

	public static int uploadResourceFile(String s, MultipartFile[] files) {
		if ((files == null) || (files.length == 0))
			return 0;
		File localFile = new File(s);
		int i = 0;
		try {
			for (MultipartFile file : files) {
				String str = file.getOriginalFilename();
				if ((file.isEmpty()) || (!allowUpload(FilenameUtils.getExtension(str))))
					continue;
				file.transferTo(new File(localFile, str));
				i++;
			}
		} catch (IOException e) {
			log.error("upload resource failed", e);
		}
		return i;
	}

	public static boolean allowUpload(String s) {
		return true;
	}
}
