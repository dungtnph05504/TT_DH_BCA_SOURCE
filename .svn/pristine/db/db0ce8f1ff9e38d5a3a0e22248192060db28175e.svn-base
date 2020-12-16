package com.nec.asia.nic.documentAttachment.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.investigation.controller.AttachmentEntry;

@Controller
@RequestMapping(value = "/documentAttachment")
public class DocumentAttachmentController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(DocumentAttachmentController.class);

	private static final String PARA_TRANSACTION_ATTACHM_IGNORE_J2K_SCOPE = "SYSTEM";
	private static final String PARA_TRANSACTION_ATTACHM_IGNORE_J2K_VALUE = "TRANSACT2_ATTACHM_IGNORE_J2K";

	private static final String PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_SCOPE = "SYSTEM";
	private static final String PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_VALUE = "TX_ATTCH_J2K_CONVERT_AT_SERVER";

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private CodesService codesService;

	@Autowired
	private ParametersService parametersService;

	@RequestMapping(value = { "/displayDocumentAttachment/{transactionDocId}" })
	public ModelAndView displayDocumentAttachment(@PathVariable long transactionDocId, HttpServletRequest httpRequest,
			Model model) throws Throwable {
		logger.info("displayDocumentAttachment{transactionDocId{" + transactionDocId + "}}");

		ModelAndView modelAndView = new ModelAndView("documentAttachment.displayDocumentAttachment");

		NicTransactionAttachment nicTransactionAttachment = this.getAttachment(transactionDocId);

		if (nicTransactionAttachment.getDocData() != null) {
			nicTransactionAttachment.setDocData(nicTransactionAttachment.getDocData());
		}

		if (nicTransactionAttachment != null) {
			AttachmentEntry attachmentEntry = new AttachmentEntry(
					this.codesService.getCodeValueDescByIdName(Codes.DOC_TYPE, nicTransactionAttachment.getDocType(),
							nicTransactionAttachment.getDocType()),
					null, nicTransactionAttachment.getDocData(), false);
			modelAndView.addObject("image", attachmentEntry.getImageString());
			modelAndView.addObject("description", attachmentEntry.getAttachmentTypeDescription());
		}

		return modelAndView;
	}

	private NicTransactionAttachment getAttachment(long transactionDocId) {

		try {
			return this.nicTransactionAttachmentService.findById(transactionDocId);
		} catch (Exception e) {
			return null;
		}
	}
}
