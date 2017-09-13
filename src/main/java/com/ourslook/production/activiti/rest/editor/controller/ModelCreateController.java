package com.ourslook.production.activiti.rest.editor.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.explorer.util.XmlUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ourslook.production.utils.PageUtils;
import com.ourslook.production.utils.Query;
import com.ourslook.production.utils.R;
import com.ourslook.production.utils.validator.Assert;

/**
 * 
    * @ClassName: ModelCreateController
    * @Description: 懒得写，直接参照了：http://www.jianshu.com/p/cf766a713a86
    * 自己增加的controller，用于对model进行操作，返回值随便弄的，需要修改
    * @author Sven
    * @date 2017年9月9日
    *
 */
@RestController
@RequestMapping("/models")
public class ModelCreateController {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ModelCreateController.class);
    @Autowired
    ProcessEngine processEngine;
    @Autowired
    ObjectMapper objectMapper;

    /**
     * 
        * @Title: newModel
        * @Description: 新建一个空模型
        * @param @param modelEntity
        * @param @return
        * @param @throws UnsupportedEncodingException    参数
        * @return R    返回类型
        * @throws
     */
    @SuppressWarnings("deprecation")
	@RequestMapping("/newModel")
    @RequiresPermissions("models:save")
    public R newModel(@RequestBody ModelEntity modelEntity) throws UnsupportedEncodingException {
    	Assert.isBlank(modelEntity.getKey(), "key不能为空");
    	Assert.isBlank(modelEntity.getName(), "名称不为能空");
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //初始化一个空模型
        Model model = repositoryService.newModel();
        //设置一些默认信息
        int revision = 1;
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, modelEntity.getName());
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, modelEntity.getCategory());
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setName(modelEntity.getName());
        model.setKey(modelEntity.getKey());
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id,editorNode.toString().getBytes("utf-8"));
        return R.ok(id);
    }

    /**
     * 
        * @Title: modelList
        * @Description: 获取所有模型
        * @param @param params
        * @param @return    参数
        * @return R    返回类型
        * @throws
     */
    @GetMapping("/modelList")
    @RequiresPermissions("models:list")
    public R modelList(@RequestParam Map<String, Object> params){
        RepositoryService repositoryService = processEngine.getRepositoryService();
    	//查询列表数据
		Query query = new Query(params);
        List<Model> models = repositoryService.createModelQuery().listPage(Integer.parseInt(query.get("offset").toString()), Integer.parseInt(query.get("limit").toString()));
        int total = (int) repositoryService.createModelQuery().count();
        PageUtils pageUtil = new PageUtils(models, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }

    /**
     * 
        * @Title: deleteModel
        * @Description: 删除模型
        * @param @param ids
        * @param @return    参数
        * @return R    返回类型
        * @throws
     */
    @PostMapping("deleteModel/{ids}")
    @RequiresPermissions("models:delete")
    public R deleteModel(@PathVariable("ids")String[] ids){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        for (String id : ids) {
        	repositoryService.deleteModel(id);	
		}
        return R.ok();
    }

    /**
     * 
        * @Title: deploy
        * @Description: 发布模型为流程定义
        * @param @param ids
        * @param @return
        * @param @throws Exception    参数
        * @return R    返回类型
        * @throws
     */
    @PostMapping("{ids}/deployment")
    public R deploy(@PathVariable("ids")String[] ids) throws Exception {
        //获取模型
        RepositoryService repositoryService = processEngine.getRepositoryService();
        for (String id : ids) {

	        Model modelData = repositoryService.getModel(id);
	        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
	        if (bytes == null) {
	        	R.error("模型数据为空，请先设计流程并成功保存，再进行发布。");
	        }
	        JsonNode modelNode = new ObjectMapper().readTree(bytes);
	        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
	        if(model.getProcesses().size()==0){
	        	R.error("数据模型不符要求，请至少设计一条主线流程。");
	        }
	        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
	        //发布流程
	        String processName = modelData.getName() + ".bpmn20.xml";
	        Deployment deployment = repositoryService.createDeployment()
	                .name(modelData.getName())
	                .addString(processName, new String(bpmnBytes, "UTF-8"))
	                .deploy();
	        modelData.setDeploymentId(deployment.getId());
	        repositoryService.saveModel(modelData);
        }
        return R.ok();
    }
    
    /**
     * 
        * @Title: export
        * @Description: 导出model的xml文件
        * @param @param modelId
        * @param @param response    参数
        * @return void    返回类型
        * @throws
     */
	@RequestMapping(value = "export/{modelId}")
	public void export(@PathVariable("modelId") String modelId,
			HttpServletResponse response) {
		try {
			//获取模型
	        RepositoryService repositoryService = processEngine.getRepositoryService();
			Model modelData = repositoryService.getModel(modelId);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper().readTree(repositoryService
					.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			IOUtils.copy(in, response.getOutputStream());
			String filename = bpmnModel.getMainProcess().getId()
					+ ".bpmn20.xml";
			response.setHeader("Content-Disposition", "attachment; filename="
					+ filename);
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("导出model的xml文件失败：modelId={}", modelId, e);
		}
	}
    /**
     * 
        * @Title: deployUploadedFile
        * @Description: activiti modeler 导入现有流程文件
        * @param @param uploadfile
        * @param @return    参数
        * @return R    返回类型
        * @throws
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public R deployUploadedFile(
            @RequestParam("uploadfile") MultipartFile uploadfile) {
    	  RepositoryService repositoryService = processEngine.getRepositoryService();
        InputStreamReader in = null;
        try {
            try {
                String fileName = uploadfile.getOriginalFilename();
                if (fileName.endsWith(".bpmn20.xml") || fileName.endsWith(".bpmn")) {
                    XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
                    in = new InputStreamReader(new ByteArrayInputStream(uploadfile.getBytes()), "UTF-8");
                    XMLStreamReader xtr = xif.createXMLStreamReader(in);
                    BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

                    if (bpmnModel.getMainProcess() == null || bpmnModel.getMainProcess().getId() == null) {
//                        notificationManager.showErrorNotification(Messages.MODEL_IMPORT_FAILED,
//                                i18nManager.getMessage(Messages.MODEL_IMPORT_INVALID_BPMN_EXPLANATION));
                        System.out.println("err1");
                        R.error("err1");
                    } else {

                        if (bpmnModel.getLocationMap().isEmpty()) {
//                            notificationManager.showErrorNotification(Messages.MODEL_IMPORT_INVALID_BPMNDI,
//                                    i18nManager.getMessage(Messages.MODEL_IMPORT_INVALID_BPMNDI_EXPLANATION));
                            System.out.println("err2");
                            R.error("err2");
                        } else {

                            String processName = null;
                            if (StringUtils.isNotEmpty(bpmnModel.getMainProcess().getName())) {
                                processName = bpmnModel.getMainProcess().getName();
                            } else {
                                processName = bpmnModel.getMainProcess().getId();
                            }
                            Model modelData;
                            modelData = repositoryService.newModel();
                            ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
                            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processName);
                            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
                            modelData.setKey(bpmnModel.getMainProcess().getId());
                            modelData.setMetaInfo(modelObjectNode.toString());
                            modelData.setName(processName);

                            repositoryService.saveModel(modelData);

                            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
                            ObjectNode editorNode = jsonConverter.convertToJson(bpmnModel);

                            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
                        }
                    }
                } else {
//                    notificationManager.showErrorNotification(Messages.MODEL_IMPORT_INVALID_FILE,
//                            i18nManager.getMessage(Messages.MODEL_IMPORT_INVALID_FILE_EXPLANATION));
                    System.out.println("err3");
                    R.error("err3");
                }
            } catch (Exception e) {
                String errorMsg = e.getMessage().replace(System.getProperty("line.separator"), "<br/>");
//                notificationManager.showErrorNotification(Messages.MODEL_IMPORT_FAILED, errorMsg);
                System.out.println("err4");
                R.error("err4");
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
//                    notificationManager.showErrorNotification("Server-side error", e.getMessage());
                    System.out.println("err5");
                    R.error("err5");
                }
            }
        }
        return R.ok();
    }
}
