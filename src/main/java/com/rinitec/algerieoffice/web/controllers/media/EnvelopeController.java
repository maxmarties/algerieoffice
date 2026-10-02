package com.rinitec.algerieoffice.web.controllers.media;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.services.medias.IAvatarService;
import com.rinitec.algerieoffice.services.medias.IEnvelopeService;

@Controller
@RequestMapping("/envelope")
public class EnvelopeController {

	private IAvatarService avatarService;
	private IEnvelopeService envelopeService;
	
	@Autowired
	public EnvelopeController(IAvatarService avatarService, IEnvelopeService envelopeService) {
		this.avatarService = avatarService;
		this.envelopeService = envelopeService;
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * @param fileId
	 * @return
	 */
	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public String getPhoto(@RequestParam("fileId") final String fileId) {
		try {
			envelopeService.downloadEnvelope(UUID.fromString(fileId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * @param postedId
	 * @return
	 */
	@RequestMapping(value = "/avatar", method = RequestMethod.GET)
	public String getAvatar(@RequestParam("postedId") final Long postedId) {
		try {
			avatarService.downloadAvatar(postedId, AvatarType.identity, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
