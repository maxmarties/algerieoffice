package com.rinitec.algerieoffice.services.medias;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.enums.BannerType;
import com.rinitec.algerieoffice.persistence.modal.medias.Banner;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;

public interface IBannerService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param file
	 * @param bannerType
	 * @return
	 * @throws InvalidImageException
	 * @throws AccessUploadException
	 */
	Banner addBanner(MultipartFile file, BannerType bannerType) throws InvalidImageException, AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param bannerId
	 * @param file
	 * @return
	 * @throws InvalidImageException
	 * @throws AccessUploadException
	 */
	Banner updateBanner(UUID bannerId, MultipartFile file) throws InvalidImageException, AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param bannerId
	 * @param width
	 * @param height
	 */
	void downloadBanner(UUID bannerId, Integer width, Integer height);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param bannerId
	 * @throws AccessUploadException
	 */
	void deleteBanner(UUID bannerId) throws AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param bannersUUID
	 * @throws AccessUploadException
	 */
	void deleteAllBanner(List<String> bannersUUID) throws AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param bannersUUID
	 * @throws AccessUploadException
	 */
	void deleteAllBanners(List<UUID> bannersUUID) throws AccessUploadException;
	
}
