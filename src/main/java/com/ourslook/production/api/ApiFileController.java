package com.ourslook.production.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ourslook.production.utils.PathUtil;
import com.ourslook.production.utils.R;
import com.ourslook.production.utils.annotation.IgnoreAuth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api/file")
@Api("文件上传接口")
public class ApiFileController {

	@IgnoreAuth
	@PostMapping("fileUpload")
	@ApiOperation(value = "上传文件")
	@ApiImplicitParam(paramType = "form", dataType = "file", name = "files", value = "文件", required = true)
	public R fileUpload(@RequestParam("files") MultipartFile files[], HttpServletRequest request) {
		List<String> list = new ArrayList<String>();
		// 上传位置
		String path = PathUtil.getWebUploadRootPath(request);// 设定文件保存的目录
		String model="upload"+File.separator+"userPhoto"+File.separator;
		path=path+model;
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		for (int i = 0; i < files.length; i++) {
			// 获得原始文件名
			String fileName = files[i].getOriginalFilename();
			System.out.println("原始文件名:" + fileName);
			// 新文件名
			String newFileName = UUID.randomUUID() + fileName;
			if (!files[i].isEmpty()) {
				File targetFile = new File(path + newFileName);
				try {
					files[i].transferTo(targetFile);
				}catch (IllegalStateException e) {
		            return R.error("图片上传失败");
		        } catch (IOException e) {
		            return R.error("图片上传失败(IO)"+e.getMessage());
		        } catch (Exception e) {
					return R.error("图片上传失败(Ex)" + e.getMessage());
				}
			}
			System.out.println("上传图片到:" + model + newFileName);
			list.add(model + newFileName);
		}
		return R.ok().put("fileList", list);
	}
}
