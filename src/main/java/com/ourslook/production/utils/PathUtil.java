package com.ourslook.production.utils;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

/**
 * 
    * @ClassName: PathUtil
    * @Description: 路径获取工具
    * @author Sven
    * @date 2017年9月5日
    *
 */
public class PathUtil {

	/**
	 * 
	    * @Title: getWebRootPath
	    * @Description: 获取项目所在地址(F:\\Java\\apache-tomcat-9.0.0.M26\\webapps\\production\\)
	    * @param @param request
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public static String getWebRootPath(HttpServletRequest request) {
		String root = request.getSession(true).getServletContext().getRealPath(File.separator);
		return root;
	}
	/**
	 * 
	    * @Title: getWebUploadRootPath
	    * @Description: 获取tomcat根目录下面的存放upload文件的地址;docBase可以是相对目录，相对目录是相对appBase
	    * <Context path="/safety/upload/" docBase="../portalUploadRoot/upload"></Context>
	    * @param @param request
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public static String getWebUploadRootPath(HttpServletRequest request) {
		String root = getWebRootPath(request);
		String parentRoot = new File(root).getParentFile().getParent();
		String uploadRoot = parentRoot +File.separator+ "productionUploadRoot"+File.separator;
		try {
			new File(uploadRoot).mkdirs();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print("======创建upload目录失败!!=====");
		}
		return uploadRoot;
	}
}
