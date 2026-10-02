package com.rinitec.algerieoffice.web.controllers.media;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.services.medias.IAvatarService;
import com.rinitec.algerieoffice.services.medias.IBannerService;
import com.rinitec.algerieoffice.services.medias.IFilereaderService;
import com.rinitec.algerieoffice.services.medias.IImageService;
import com.rinitec.algerieoffice.services.medias.IPhotoService;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

@Controller
@RequestMapping("/media")
public class MediaController {

	private IAvatarService avatarService;
	private IPhotoService photoService;
	private IImageService imageService;
	private IBannerService bannerService;
	private IFilereaderService filereaderService;
	
	@Autowired
	public MediaController(IAvatarService avatarService, IPhotoService photoService, IImageService imageService, IBannerService bannerService, 
			IFilereaderService filereaderService) {
		this.avatarService = avatarService;
		this.photoService = photoService;
		this.imageService = imageService;
		this.bannerService = bannerService;
		this.filereaderService = filereaderService;
	}
	
	/**
	 * AUTORITY IGNORED_RESOURCE
	 * @param postedId
	 * @param avatarType
	 * @param width
	 * @param height
	 * @return
	 */
	@RequestMapping(value = "/avt", method = RequestMethod.GET)
	public String getAvatar(@RequestParam("postedId") final Long postedId, @RequestParam("type") final AvatarType avatarType,
			@RequestParam(name = "width", required = false) final Integer width, @RequestParam(name = "height", required = false) final Integer height) {
		if(avatarType.equals(AvatarType.identity)) {
			return "redirect:".concat(ConstraintesURL.URL_IDENTITY + "?postedId=").concat(postedId.toString());
		}
		try {
			avatarService.downloadAvatar(postedId, avatarType, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * AUTORITY IGNORED_RESOURCE
	 * @param photoId
	 * @param width
	 * @param height
	 * @return
	 */
	@RequestMapping(value = "/photo", method = RequestMethod.GET)
	public String getPhoto(@RequestParam("photoId") final String photoId, @RequestParam(name = "width", required = false) final Integer width, 
			@RequestParam(name = "height", required = false) final Integer height) {
		try {
			photoService.downloadPhoto(UUID.fromString(photoId), width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * AUTORITY IGNORED_RESOURCE
	 * @param uuid
	 * @param width
	 * @param height
	 * @return
	 */
	@RequestMapping(value = "/image", method = RequestMethod.GET)
	public String getImage(@RequestParam("uuid") final String uuid, @RequestParam(name = "width", required = false) final Integer width, 
			@RequestParam(name = "height", required = false) final Integer height) {
		try {
			imageService.downloadImage(UUID.fromString(uuid), width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * AUTORITY IGNORED_RESOURCE
	 * @param bannerId
	 * @return
	 */
	@RequestMapping(value = "/aobnn", method = RequestMethod.GET)
	public String getBanner(@RequestParam("aobnId") final String bannerId, @RequestParam(name = "width", required = false) final Integer width, 
			@RequestParam(name = "height", required = false) final Integer height) {
		try {
			bannerService.downloadBanner(UUID.fromString(bannerId), width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * AUTORITY IGNORED_RESOURCE
	 * @param fileId
	 * @return
	 */
	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public String getFilereader(@RequestParam("fileId") final String fileId) {
		try {
			filereaderService.downloadFilereader(UUID.fromString(fileId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
