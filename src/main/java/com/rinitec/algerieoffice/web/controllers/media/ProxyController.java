package com.rinitec.algerieoffice.web.controllers.media;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.enums.FileType;
import com.rinitec.algerieoffice.persistence.modal.medias.Filereader;
import com.rinitec.algerieoffice.persistence.modal.medias.Image;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.medias.IFilereaderService;
import com.rinitec.algerieoffice.services.medias.IImageService;
import com.rinitec.algerieoffice.services.medias.ILinkedMenuService;
import com.rinitec.algerieoffice.services.medias.IScreenshotService;
import com.rinitec.algerieoffice.ujson.FilereaderResponse;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.ujson.ImageResponse;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.MaxPlanException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

@Controller
@RequestMapping("/proxy")
public class ProxyController {

	private IFilereaderService filereaderService;
	private IImageService imageService;
	private IScreenshotService screenshotService;
	private ILinkedMenuService linkedMenuService;
	
	@Autowired
	public ProxyController(IFilereaderService filereaderService, IImageService imageService,
			IScreenshotService screenshotService, ILinkedMenuService linkedMenuService) {
		this.filereaderService = filereaderService;
		this.imageService = imageService;
		this.screenshotService = screenshotService;
		this.linkedMenuService = linkedMenuService;
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/image", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse uploadImage(@RequestParam("file") final MultipartFile file, @AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUser().getAdmin() && imageService.hasLimitSpace(localUser.getCompanyId())) {
			throw new MaxPlanException("message.plan.usedspace");
		}
		final long companyId = localUser.getUser().getAdmin() ? 1L : localUser.getCompanyId();
		final Image image = imageService.addImage(file, companyId);
		return new GenericResponse(ConstraintesURL.getProxyImageMapsiteURL(image.getId().toString()));
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * @param localUser
	 * @param page
	 * @param ch
	 * @return
	 */
	@RequestMapping(value = "/load-images", method = RequestMethod.GET)
	@ResponseBody
	public List<ImageResponse> loadImages(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("pg") int page, @RequestParam("ch") String ch) {
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final long companyId = localUser.getUser().getAdmin() ? 1L : localUser.getCompanyId();
		final List<ImageResponse> imagesResponses = imageService.findProxyImages(companyId, search, page, 30);
		return imagesResponses;
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/delete-image", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteImages(@RequestParam(name = "id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final long companyId = localUser.getUser().getAdmin() ? 1L : localUser.getCompanyId();
		imageService.deleteProxyImage(id, companyId);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/filereader", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse uploadFilereader(@RequestParam("file") final MultipartFile file, @AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUser().getAdmin() && filereaderService.hasLimitSpace(localUser.getCompanyId())) {
			throw new MaxPlanException("message.plan.usedspace");
		}
		final long companyId = localUser.getUser().getAdmin() ? 1L : localUser.getCompanyId();
		final Filereader filereader = filereaderService.addFilereader(file, companyId, FileType.proxy);
		return new GenericResponse(ConstraintesURL.getProxyFileMapsiteURL(filereader.getId().toString()));
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * @param localUser
	 * @param page
	 * @param ch
	 * @return
	 */
	@RequestMapping(value = "/load-filereaders", method = RequestMethod.GET)
	@ResponseBody
	public List<FilereaderResponse> loadFilereaders(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("pg") int page, @RequestParam("ch") String ch) {
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final long companyId = localUser.getUser().getAdmin() ? 1L : localUser.getCompanyId();
		final List<FilereaderResponse> filereadersResponses = filereaderService.findProxyFilereaders(companyId, search, page, 30);
		return filereadersResponses;
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/delete-filereader", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteFilereader(@RequestParam(name = "id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final long companyId = localUser.getUser().getAdmin() ? 1L : localUser.getCompanyId();
		filereaderService.deleteProxyFilereader(id, companyId);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * @param model
	 * @param localUser
	 * @param type
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/linked", method = RequestMethod.GET)
	public String menuLinked(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("type") final Integer type, @RequestParam("id") final String id) {
		final Long companyId = localUser.getCompanyId();
		if(companyId == null) {
			throw new AccessUploadException("message.warning.linkeditor");
		}
		if(!linkedMenuService.hasCompanyPublished(companyId)) {
			throw new AccessUploadException("message.error.linkeditor");
		}
		if(type == ILinkedMenuService.LINKED_COMPANYPAGE) {
			model.addAttribute("companyURL", linkedMenuService.getUrlCompany(companyId));
		} else {
			model.addAttribute("choseLinkeds", linkedMenuService.findChoseLinked(companyId, type));
		}
		model.addAttribute("linkId", id);
		model.addAttribute("linkType", type);
		return "inboxMenuLinked";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/screenshot", method = RequestMethod.GET)
	public String getScreenshot(@RequestParam("id") final String id) {
		try {
			screenshotService.downloadScreenshot(UUID.fromString(id));
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
}
