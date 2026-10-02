package com.rinitec.algerieoffice.web.controllers.media;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.medias.IFilereaderService;

@Controller
@RequestMapping("/document")
public class DocumentController {

	private IFilereaderService filereaderService;
	
	@Autowired
	public DocumentController(IFilereaderService filereaderService) {
		this.filereaderService = filereaderService;
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * @param documentId
	 * @param companyId
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/bub", method = RequestMethod.GET)
	public String getDocument(@RequestParam("documentId") final String documentId, @RequestParam("companyId") final Long companyId, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(companyId.equals(localUser.getCompanyId())) {
			try {
				filereaderService.downloadDocument(UUID.fromString(documentId), companyId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
