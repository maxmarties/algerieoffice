package com.rinitec.algerieoffice.services.medias;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.medias.Avatar;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;

public interface IAvatarService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postedId
	 * @param avatarType
	 * @return
	 */
	String getFilename(Long postedId, AvatarType avatarType);

	/**
	 * VERSION BEGIN 03/2021
	 * @param file
	 * @param postedId
	 * @param avatarType
	 * @return
	 * @throws InvalidImageException
	 * @throws AccessUploadException
	 */
	Avatar postOrUpdate(MultipartFile file, Long postedId, AvatarType avatarType) throws InvalidImageException, AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postedId
	 * @param avatarType
	 * @param width
	 * @param height
	 */
	void downloadAvatar(Long postedId, AvatarType avatarType, Integer width, Integer height);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postedId
	 * @param avatarType
	 * @throws AccessUploadException
	 */
	void deleteAvatar(Long postedId, AvatarType avatarType) throws AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postedsId
	 * @param avatarType
	 * @throws AccessUploadException
	 */
	void deleteAllAvatar(List<Long> postedsId, AvatarType avatarType) throws AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param postedId
	 * @param avatarType
	 * @return
	 */
	File readFile(Long postedId, AvatarType avatarType);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param agentIds
	 */
	void deleteAllAvatarCompany(Long companyId, List<Long> agentIds);
	
}
