package com.rinitec.algerieoffice.services.medias;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.enums.EnvelopeType;
import com.rinitec.algerieoffice.persistence.modal.medias.Envelope;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;

public interface IEnvelopeService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param file
	 * @param envelopeType
	 * @return
	 * @throws InvalidImageException
	 * @throws AccessUploadException
	 */
	Envelope addEnvelope(MultipartFile file, EnvelopeType envelopeType) throws InvalidImageException, AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param envelopeId
	 */
	void downloadEnvelope(UUID envelopeId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param envelopeId
	 * @throws AccessUploadException
	 */
	void deleteEnvelope(UUID envelopeId) throws AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param envelopesUUID
	 * @throws AccessUploadException
	 */
	void deleteAllEnvelope(List<String> envelopesUUID) throws AccessUploadException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param envelopesUUID
	 */
	void deleteAll(List<UUID> envelopesUUID);
	
}
