package com.rinitec.algerieoffice.services.medias;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.persistence.modal.medias.Image;
import com.rinitec.algerieoffice.ujson.ImageResponse;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;

public interface IImageService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param file
	 * @param companyId
	 * @return
	 * @throws InvalidImageException
	 * @throws AccessUploadException
	 */
	Image addImage(MultipartFile file, Long companyId) throws InvalidImageException, AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param imageId
	 * @param width
	 * @param height
	 */
	void downloadImage(UUID imageId, Integer width, Integer height);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param imageId
	 * @throws AccessUploadException
	 */
	void deleteImage(UUID imageId) throws AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param search
	 * @param page
	 * @param rows
	 * @return
	 */
	List<ImageResponse> findProxyImages(Long companyId, String search, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 * @throws AccessUploadException
	 */
	void deleteProxyImage(String id, Long companyId) throws NotFoundException, InvalidResourceException, AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	boolean hasLimitSpace(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteCompanyDir(Long companyId);
	
}
