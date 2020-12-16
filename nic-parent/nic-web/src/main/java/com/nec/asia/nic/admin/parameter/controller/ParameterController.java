/**
 * 
 */
package com.nec.asia.nic.admin.parameter.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.domain.type.ParameterType;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.web.session.UserSession;

/**
 * @author prasad_karnati
 */

/*
 * Modification History:
 * 13 Dec 2013 (Chris Lai) : Add in Audit Log
 */

@Controller
@RequestMapping(value = "/parameterController")
public class ParameterController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ParameterController.class);

    private static String ASSENDING_ORDER = "1";

    private static String DECENDING_ORDER = "2";

    @Autowired
    private ParametersService parametersService;

    @Autowired
    private CodesService codesService;

    @Autowired
    private AuditAccessLogService auditAccessLogService;

    @RequestMapping(value = "/getParamList")
    @ExceptionHandler
    public ModelAndView getParamList(WebRequest request, Model model) throws Exception {
        logger.debug(" Start the getParamList  : ParameterController:getParamList  ");
        int pageSize = 10;
        int pageNo = 1;

        ParametersId id = new ParametersId();
        id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
        id.setParaScope(Parameters.SCOPE_SYSTEM);
        String tableId = "row";
        String sortedElement = "id.paraName";
        if (StringUtils.isNotBlank(request.getParameter("subType"))) {
			sortedElement = request.getParameter("subType");
			model.addAttribute("subType", sortedElement);
		}
        String order = ParameterController.DECENDING_ORDER;

        try {
            String sort = request.getParameter(new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_SORT));
            if (StringUtils.isNotBlank(sort))
                sortedElement = sort;
        } catch (Exception ex) {
            logMessage(ex);
        }

        try {
            String requestOrder = request.getParameter(new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
            if (StringUtils.isNotBlank(requestOrder))
                order = requestOrder;
        } catch (Exception ex) {
            logMessage(ex);
        }

        Order hibernateOrder = Order.desc(sortedElement);
        if (order.equalsIgnoreCase(ParameterController.DECENDING_ORDER)) {
            hibernateOrder = Order.asc(sortedElement);
        }

        Parameters parameter = parametersService.findById(id);

        if (parameter != null) {
            String pageSizeDb = parameter.getParaShortValue();

            if (StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)) {
                pageSize = Integer.parseInt(pageSizeDb);
            }
        }
        String pageNumber = request.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

        if (StringUtils.isNotBlank(pageNumber) && StringUtils.isNumeric(pageNumber)) {
            pageNo = Integer.parseInt(pageNumber);
        }
        pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
        pageSize = Constants.PAGE_SIZE_DEFAULT;
        //[25 Jan 2016][Tue] - Start
        //PaginatedResult<Parameterss> prParamList =parametersService.findAllForPagination(pageNo, pageSize, hibernateOrder);
        PaginatedResult<Parameters> prParamList = null;
        if (StringUtils.isNotBlank(request.getParameter("paraName"))) {
        	prParamList = parametersService.getPageParamByParaName(request.getParameter("paraName"), pageNo, pageSize, hibernateOrder);
        	model.addAttribute("paraName", request.getParameter("paraName"));
        }else{
			prParamList = parametersService.findAllActForPagination(pageNo, pageSize, hibernateOrder);
		}
        // End

        Parameters parameterForm = new Parameters();
        List<Parameters> list = new ArrayList<Parameters>();
        list = prParamList.getRows();
        int i = (pageNo - 1) * pageSize;
        for(Parameters pr : list){
        	i++;
        	pr.setStt(i);
        }
        Map<String, Object> paramList = new HashMap<String, Object>();
        paramList.put("paramList", list);
        paramList.put("totalRecords", prParamList.getTotal());
        paramList.put("pageSize", pageSize);
        //model.addAttribute("jobList", list);
        //phúc edit
        int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("totalPage", pagingUtil.totalPage(prParamList.getTotal(), pageSize));
        model.addAttribute("startIndex", prParamList.getTotal() != 0 ? firstResults + 1 : 0);
        model.addAttribute("totalRecord", prParamList.getTotal());
        model.addAttribute("endIndex", firstResults + prParamList.getRowSize());
        model.addAttribute("jobList", list);
        //end
        ModelAndView modelView = new ModelAndView("viewParamList", paramList);
        if (request.getAttribute("status", 1) != null) {
            parameterForm.setStatus((String) request.getAttribute("status", 1));
            request.setAttribute("status", "empty", 1);
            parameterForm.setMessage((String) request.getAttribute("message", 2));
            request.setAttribute("message", "", 2);
        }
        model.addAttribute("fnSelected", Constants.HEADING_NIC_PARAM_MGMT);
        modelView.addObject("parameterForm", parameterForm);
        logger.debug(" Exit the getParamList  : ParameterController:getParamList  ");
        return modelView;

    }

    @RequestMapping(value = "/editParam/{params}")
    @ExceptionHandler
    public ModelAndView editParam(WebRequest request, @PathVariable String params, Model model) throws Exception {
        logger.debug(" Start the editParam  : ParameterController:editParam ");

        ParameterForm paramEntity = new ParameterForm();
        try {
            String paraScope = "";
            String paramName = "";
            if (params.contains("&&")) {
                String param[] = params.split("&&");
                paraScope = param[0];
                paramName = param[1];
            } else if (params != null) {
                paraScope = params;

            }

            Parameters parameter = parametersService.getParamDetails(paraScope, paramName);

            if (parameter != null) {
                paramEntity.setAdminAccessibleFlag(parameter.getAdminAccessibleFlag());
                paramEntity.setParaDesc(parameter.getParaDesc());
                paramEntity.setParaName((null != parameter.getId() ? parameter.getId().getParaName() : null));
                paramEntity.setParaScope((null != parameter.getId() ? parameter.getId().getParaScope() : null));
                paramEntity.setParaType((null != parameter.getParaType() ? parameter.getParaType().getKey() : null));
                paramEntity.setParaValue(parameter.getParaShortValue());
                paramEntity.setSystemId(parameter.getSystemId());
                paramEntity.setUserAccessibleFlag(parameter.getUserAccessibleFlag());
                //[26 Jan 2016][Tue] - add lob data
                paramEntity.setParaLobValue(parameter.getParaLobValue());
            }
            List<CodeValues> siteIdList = codesService.findAllByCodeId(Constants.CODE_ID_SYSTEM_ID);
            model.addAttribute("codeMap", siteIdList);
            model.addAttribute("fnSelected", Constants.HEADING_NIC_PARAM_MGMT);
            model.addAttribute("paraTypeList", ParameterType.getAllInstances());
            logger.debug(" Exit the editParam  : ParameterController:editParam ");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ModelAndView("updateParam", "updateParameterForm", paramEntity);

    }

    @RequestMapping(value = "/save")
    @ResponseBody
    @ExceptionHandler
    public String save(@ModelAttribute("parameterForm") ParameterForm parameterForm, WebRequest request, HttpServletRequest httpRequest) throws Exception {
        // Chris Lai (13 Dec 2013) : Add in Audit Log
        long startTimeMs = System.currentTimeMillis();
        Throwable throwable = null;
        Exception failSave = new Exception();

        logger.debug(" Start the Save Parameters  : ParameterController: save ");
        HttpSession session = httpRequest.getSession();
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        Parameters parameter = new Parameters();
        ParametersId id = new ParametersId();

        id.setParaName(parameterForm.getParaName());
        id.setParaScope(parameterForm.getParaScope());

        String paramType = parameterForm.getParaType();
        //logger.info("The parameter type is ============>>>>>>>>>>"+paramType);
        parameter.setAdminAccessibleFlag(parameterForm.getAdminAccessibleFlag());
        parameter.setUserAccessibleFlag(parameterForm.getUserAccessibleFlag());
        parameter.setId(id);
        parameter.setParaShortValue(parameterForm.getParaValue());
        parameter.setParaDesc(parameterForm.getParaDesc());
        parameter.setSystemId(parameterForm.getSystemId());

        parameter.setParaType(ParameterType.getInstance(paramType));
        parameter.setCreateBy(userSession.getUserId());
        parameter.setCreateDate(DateUtil.getSystemTimestamp());
        parameter.setCreateWkstnId(userSession.getWorkstationId());
        //[26 Jan 2016][Tue] - add lob data
        parameter.setParaLobValue(parameterForm.getParaLobValue());

        String status = parametersService.saveParam(parameter);
        // Chris Lai (13 Dec 2013) : Add in Audit Log
        if (!status.equals("success")) {
            throwable = failSave;
        }

        logger.debug(" Exit from the Save Parameters  : ParameterController: save  ");
        // Chris Lai (13 Dec 2013) : Add in Audit Log
        try {
            String functionName = "Thêm  mới cấu hình thông số hệ thống";
            Object[] args = {
                parameterForm
            };
            long timeMs = System.currentTimeMillis() - startTimeMs;
            auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, functionName, args, throwable, timeMs);
        } catch (Exception exp) {
        }

        return status;

    }

    @RequestMapping(value = "/addParam")
    @ExceptionHandler
    public ModelAndView addParam(@ModelAttribute("parameterForm") ParameterForm parameterForm, WebRequest request, Model model) throws Exception {
        model.addAttribute("fnSelected", Constants.HEADING_NIC_PARAM_MGMT);
        //parameterForm
        List<CodeValues> siteIdList = codesService.findAllByCodeId(Constants.CODE_ID_SYSTEM_ID);
        Map<String, String> sysCodeMap = new LinkedHashMap<String, String>();
        if (siteIdList != null) {
            for (CodeValues codeVal : siteIdList) {
                sysCodeMap.put(codeVal.getId().getCodeValue(), codeVal.getCodeValueDesc());
            }
        }
        parameterForm.setSysCodeMap(sysCodeMap);
        model.addAttribute("paraTypeList", ParameterType.getAllInstances());

        return new ModelAndView("newParam", "parameterForm", parameterForm);
    }

    @RequestMapping(value = "/updateParameter")
    @ResponseBody
    @ExceptionHandler
    public String updateParam(@ModelAttribute("updateParameterForm") ParameterForm parameterForm, WebRequest request, HttpServletRequest httpRequest) throws Exception {
        // Chris Lai (13 Dec 2013) : Add in Audit Log
        long startTimeMs = System.currentTimeMillis();
        Throwable throwable = null;
        Exception failUpdate = new Exception();
        String status = "fail";

        logger.debug(" Start the updateParam  : ParameterController: updateParam ");
        HttpSession session = httpRequest.getSession();
        UserSession userSession = (UserSession) session.getAttribute("userSession");

        ParametersId id = new ParametersId();

        id.setParaName(parameterForm.getParaName());
        id.setParaScope(parameterForm.getParaScope());

        Parameters parameter = parametersService.findById(id);

        if (parameter != null) {
            String paramType = parameterForm.getParaType();

            parameter.setAdminAccessibleFlag(parameterForm.getAdminAccessibleFlag());
            parameter.setUserAccessibleFlag(parameterForm.getUserAccessibleFlag());
            parameter.setParaShortValue(parameterForm.getParaValue());
            parameter.setParaDesc(parameterForm.getParaDesc());
            parameter.setSystemId(parameterForm.getSystemId());

            parameter.setParaType(ParameterType.getInstance(paramType));

            parameter.setUpdateBy(userSession.getUserId());
            parameter.setUpdateWkstnId(userSession.getWorkstationId());
            parameter.setUpdateDate(DateUtil.getSystemTimestamp());
            //[26 Jan 2016][Tue] - add lob data
            parameter.setParaLobValue(parameterForm.getParaLobValue());

            status = parametersService.updateParam(parameter);

        } else {
            logger.debug(" No Parameter found with Parameter Name:  " + parameterForm.getParaName() + " and with Parameter Scope: " + parameterForm.getParaScope());
            status = "fail";
        }

        // Chris Lai (13 Dec 2013) : Add in Audit Log
        if (!status.equals("success")) {
            throwable = failUpdate;
        }

        try {
            String functionName = "Cập nhật cấu hình thông số hệ thống";
            Object[] args = {
                parameterForm
            };
            long timeMs = System.currentTimeMillis() - startTimeMs;
            auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, functionName, args, throwable, timeMs);
        } catch (Exception exp) {
        }

        logger.debug(" Exit from the updateParam  : ParameterController: updateParam  ");
        return status;

    }

    @RequestMapping(value = "/deleteParam")
    @ExceptionHandler
    public String deleteParam(@ModelAttribute("updateParameterForm") ParameterForm parameterForm, WebRequest request, HttpServletRequest httpRequest, Model model) throws Exception {
        // Chris Lai (13 Dec 2013) : Add in Audit Log
        long startTimeMs = System.currentTimeMillis();
        Throwable throwable = null;
        Exception failDelete = new Exception();
        String status = "fail";
        logger.debug(" Start the deleteParam  : ParameterController: deleteParam ");
        HttpSession session = httpRequest.getSession();
        UserSession userSession = (UserSession) session.getAttribute("userSession");

        ParametersId id = new ParametersId();

        id.setParaName(parameterForm.getParaName());
        id.setParaScope(parameterForm.getParaScope());

        Parameters parameter = parametersService.findById(id);

        if (parameter != null) {
            parameter.setDeleteBy(userSession.getUserName());
            parameter.setDeleteDate(DateUtil.getSystemTimestamp());
            parameter.setDeleteWkstnId(userSession.getWorkstationId());
            parameter.setDeleteFlag(Boolean.TRUE);
            status = parametersService.deleteParam(parameter);
        } else {
            logger.debug("No Parameter found with Parameter Name:  " + parameterForm.getParaName() + " and with Parameter Scope: " + parameterForm.getParaScope());
            status = "fail";
        }

        request.setAttribute("status", status, 1);
        if (status.equals("success")) {
            request.setAttribute("message", "Đã xóa thành công tham số hệ thống.", 1);
        } else {
            request.setAttribute("message", "Đã xảy ra lỗi khi xóa tham số hệ thống.", 2);
            throwable = failDelete;
        }

        model.addAttribute("fnSelected", Constants.HEADING_NIC_PARAM_MGMT);
        logger.debug("Exit from the deleteParam  : ParameterController: deleteParam  ");
        // Chris Lai (13 Dec 2013) : Add in Audit Log
        try {
            String functionName = "Xóa cấu hình thông số hệ thống";
            Object[] args = {
                parameterForm
            };
            long timeMs = System.currentTimeMillis() - startTimeMs;
            auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, functionName, args, throwable, timeMs);
        } catch (Exception exp) {
        }

        return "forward:" + "getParamList";

    }

    private void logMessage(Exception ex) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        ex.printStackTrace(ps);
        logger.error(ex.getMessage() + " : \n" + new String(baos.toByteArray()));
    }

}
