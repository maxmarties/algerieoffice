package com.rinitec.algerieoffice.services.medias;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.enums.PhotoType;
import com.rinitec.algerieoffice.persistence.modal.medias.Photo;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;

public interface IPhotoService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param file
	 * @param companyId
	 * @param photoType
	 * @return
	 * @throws InvalidImageException
	 * @throws AccessUploadException
	 */
	Photo addPhoto(MultipartFile file, Long companyId, PhotoType photoType) throws InvalidImageException, AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param photoId
	 * @param file
	 * @return
	 * @throws InvalidImageException
	 * @throws AccessUploadException
	 */
	Photo updatePhoto(UUID photoId, MultipartFile file) throws InvalidImageException, AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param photoId
	 * @param width
	 * @param height
	 */
	void downloadPhoto(UUID photoId, Integer width, Integer height);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param photoId
	 * @throws AccessUploadException
	 */
	void deletePhoto(UUID photoId) throws AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param photosUUID
	 * @throws AccessUploadException
	 */
	void deleteAllPhoto(List<String> photosUUID) throws AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param photosUUID
	 * @throws AccessUploadException
	 */
	void deleteAllPhotos(List<UUID> photosUUID) throws AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param file
	 * @param companyId
	 * @param photoType
	 * @return
	 * @throws AccessUploadException
	 */
	Photo addPhotoFile(File file, Long companyId, PhotoType photoType) throws AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void deleteCompanyDir(Long companyId);
	
}
