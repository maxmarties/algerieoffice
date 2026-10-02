package com.rinitec.algerieoffice.services.medias;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.enums.FileType;
import com.rinitec.algerieoffice.persistence.modal.medias.Filereader;
import com.rinitec.algerieoffice.ujson.FilereaderResponse;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidFileException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;

public interface IFilereaderService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param fileId
	 * @return
	 */
	String getFilename(UUID fileId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param file
	 * @param companyId
	 * @param fileType
	 * @return
	 * @throws InvalidFileException
	 * @throws AccessUploadException
	 */
	Filereader addFilereader(MultipartFile file, Long companyId, FileType fileType) throws InvalidFileException, AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param fileId
	 * @param file
	 * @return
	 * @throws InvalidFileException
	 * @throws AccessUploadException
	 */
	Filereader updateFilereader(UUID fileId, MultipartFile file) throws InvalidFileException, AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param fileId
	 */
	void downloadFilereader(UUID fileId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param fileId
	 * @param companyId
	 */
	void downloadDocument(UUID fileId, Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param fileId
	 * @throws AccessUploadException
	 */
	void deleteFilereader(UUID fileId) throws AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filesUUID
	 * @throws AccessUploadException
	 */
	void deleteAllFilereaders(List<UUID> filesUUID) throws AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param search
	 * @param page
	 * @param rows
	 * @return
	 */
	List<FilereaderResponse> findProxyFilereaders(Long companyId, String search, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param companyId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 * @throws AccessUploadException
	 */
	void deleteProxyFilereader(String id, Long companyId) throws NotFoundException, InvalidResourceException, AccessUploadException;
	
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
