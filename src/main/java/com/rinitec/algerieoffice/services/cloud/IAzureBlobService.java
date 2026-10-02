package com.rinitec.algerieoffice.services.cloud;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.web.multipart.MultipartFile;

import com.microsoft.azure.storage.StorageException;
import com.rinitec.algerieoffice.persistence.modal.medias.AzureBlob;

public interface IAzureBlobService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	boolean hasMaxSpace(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param path
	 * @param file
	 * @param companyId
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	AzureBlob make(File path, File file, Long companyId) throws IOException, URISyntaxException, StorageException;

	/**
	 * VERSION BEGIN 03/2021
	 * @param path
	 * @param multipartFile
	 * @param companyId
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	AzureBlob upload(File path, MultipartFile multipartFile, Long companyId) throws IOException, URISyntaxException, StorageException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	byte[] download(File path) throws IOException, URISyntaxException, StorageException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	File readFile(File path) throws IOException, URISyntaxException, StorageException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param path
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	void delete(File path) throws IOException, URISyntaxException, StorageException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	void deleteDir(Long companyId) throws IOException, URISyntaxException, StorageException;
	
}
