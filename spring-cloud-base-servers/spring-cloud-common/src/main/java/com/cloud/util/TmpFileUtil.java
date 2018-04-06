package com.cloud.util;

import com.cloud.support.TraceErrorException;

import java.io.File;
/**
 * @author summer
 */
public class TmpFileUtil {
	private static final transient TLogger DB_LOGGER = LoggerUtils.getLogger(HttpUtils.class);
	public static void deleteFile(File file){
		if(file.exists()){
			if(file.isFile()){
				file.delete();
			}else{
				throw new TraceErrorException("can't delete directory:" + file);
			}
		}else{
			DB_LOGGER.warn("fileNotExists:" + file);
		}
	}

}
