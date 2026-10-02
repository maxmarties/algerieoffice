package com.rinitec.algerieoffice.services.medias;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.persistence.modal.medias.Screenshot;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;

public interface IScreenshotService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param file
	 * @return
	 * @throws InvalidImageException
	 * @throws AccessUploadException
	 */
	Screenshot addScreenshot(MultipartFile file) throws InvalidImageException, AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param screenshotId
	 */
	void downloadScreenshot(UUID screenshotId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param screenshotId
	 * @throws AccessUploadException
	 */
	void deleteScreenshot(UUID screenshotId) throws AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @throws AccessUploadException
	 */
	void deleteScreenshots(List<UUID> lines) throws AccessUploadException;
	
}
