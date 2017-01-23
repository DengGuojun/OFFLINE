package com.lpmas.tpp.console.channel.offline.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.business.AdminUserHelper;
import com.lpmas.admin.config.OperationConfig;
import com.lpmas.framework.excel.ExcelReadKit;
import com.lpmas.framework.excel.ExcelReadResultBean;
import com.lpmas.framework.tools.common.bean.ImportResultBean;
import com.lpmas.framework.transfer.FileUploadKit;
import com.lpmas.framework.transfer.FileUploadResultBean;
import com.lpmas.framework.util.DateKit;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.HttpResponseKit;
import com.lpmas.framework.web.ParamKit;
import com.lpmas.system.bean.StoreInfoBean;
import com.lpmas.system.client.SystemServiceClient;
import com.lpmas.tpp.console.channel.offline.config.OfflineConfig;
import com.lpmas.tpp.console.channel.offline.config.OfflineOrderInfoConfig;
import com.lpmas.tpp.console.channel.offline.config.OfflineResource;
import com.lpmas.tpp.console.channel.offline.handler.OfflineOrderInfoHandler;

@WebServlet("/offline/OfflineOrderInfoImport.do")
public class OfflineOrderInfoImport extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(OfflineOrderInfoImport.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OfflineOrderInfoImport() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(OfflineResource.ORDER_INFO, OperationConfig.CREATE)) {
			return;
		}

		// 获取商店列表
		SystemServiceClient systemClient = new SystemServiceClient();
		List<StoreInfoBean> storeList = systemClient.getStoreInfoListByChannelCode(OfflineConfig.CHANNEL_CODE);

		request.setAttribute("StoreList", storeList);
		RequestDispatcher rd = request.getRequestDispatcher(OfflineConfig.PAGE_PATH + "OfflineOrderInfoImport.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AdminUserHelper adminUserHelper = new AdminUserHelper(request, response);
		if (!adminUserHelper.checkPermission(OfflineResource.ORDER_INFO, OperationConfig.CREATE)) {
			return;
		}

		int userId = adminUserHelper.getAdminUserId();
		// 检查必须的参数
		int storeId = ParamKit.getIntParameter(request, "storeId", 0);
		if (storeId <= 0) {
			HttpResponseKit.alertMessage(response, "商店参数缺失", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		String tradeSource = ParamKit.getParameter(request, "tradeSource", "");
		if (!StringKit.isValid(tradeSource)) {
			HttpResponseKit.alertMessage(response, "来源参数缺失", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 上传Excel到服务器
		FileUploadKit fileUploadKit = new FileUploadKit();
		String fileName = DateKit.getCurrentDateTime("yyyyMMddHHmmss");
		String uploadPath = OfflineOrderInfoConfig.FILE_PATH;
		fileUploadKit.setAllowedFileType(OfflineOrderInfoConfig.ALLOWED_FILE_TYPE);// 设置允许上传的文件类型
		fileUploadKit.setMaxSize(OfflineOrderInfoConfig.MAX_SIZE);
		FileUploadResultBean resultBean = fileUploadKit.fileUpload(request, "file", uploadPath, fileName);
		if (!resultBean.getResult()) {
			HttpResponseKit.alertMessage(response, resultBean.getResultContent(), HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		if (resultBean.getFileItemList() == null || resultBean.getFileItemList().size() == 0) {
			HttpResponseKit.alertMessage(response, "文件上传至服务器失败，详情请查看日志。", HttpResponseKit.ACTION_HISTORY_BACK);
			return;
		}
		// 获取上传结果
		String filePath = resultBean.getFileItemList().get(0).getFilePath();
		ExcelReadKit excelReadKit = new ExcelReadKit();
		ExcelReadResultBean excelReadResultBean = excelReadKit.readExcel(filePath, 0);
		if (excelReadResultBean != null && excelReadResultBean.getResult()) {
			// 解析Excel数据,从第二行开始读起
			OfflineOrderInfoHandler orderInfoHandler = new OfflineOrderInfoHandler();
			try {
				ImportResultBean result = orderInfoHandler.importOrderInfoList(userId, storeId, tradeSource, excelReadResultBean);

				// 获取商店列表
				SystemServiceClient systemClient = new SystemServiceClient();
				List<StoreInfoBean> storeList = systemClient.getStoreInfoListByChannelCode(OfflineConfig.CHANNEL_CODE);
				request.setAttribute("ImportResult", result);
				request.setAttribute("StoreId", storeId);
				request.setAttribute("TradeSource", tradeSource);
				request.setAttribute("StoreList", storeList);
				RequestDispatcher rd = request.getRequestDispatcher(OfflineConfig.PAGE_PATH + "OfflineOrderInfoImport.jsp");
				rd.forward(request, response);
			} catch (Exception e) {
				log.error("", e);
				HttpResponseKit.alertMessage(response, "导入失败", HttpResponseKit.ACTION_HISTORY_BACK);
			}
			return;
		} else {
			HttpResponseKit.alertMessage(response, excelReadResultBean.getErrMsg(), "/offline/OfflineOrderInfoImport.do");
			return;
		}
	}

}
