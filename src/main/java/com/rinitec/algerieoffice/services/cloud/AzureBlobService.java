package com.rinitec.algerieoffice.services.cloud;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.microsoft.applicationinsights.core.dependencies.apachecommons.lang3.StringUtils;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.rinitec.algerieoffice.persistence.dao.medias.AzureBlobRepository;
import com.rinitec.algerieoffice.persistence.modal.medias.AzureBlob;
import com.rinitec.algerieoffice.utils.FilesUtil;

@Service
public class AzureBlobService implements IAzureBlobService {
	private static final Long MAX_SPACE = 50000L;
	private static final String DEFAULT_CONTAINER = "workspace";
	private static final String NAME_CONTAINER = "workspace-company-";
	
	private HttpServletRequest request;
	private CloudBlobClient blobClient;
	private AzureBlobRepository azureBlobRepository;
	
	@Autowired
	public AzureBlobService(HttpServletRequest request, CloudBlobClient blobClient, AzureBlobRepository azureBlobRepository) {
		this.request = request;
		this.blobClient = blobClient;
		this.azureBlobRepository = azureBlobRepository;
	}
	
	@Transactional(readOnly = true)
	public boolean hasMaxSpace(final Long companyId) {
		return azureBlobRepository.countByCompanyId(companyId) >= MAX_SPACE;
	}
	
	private final boolean makeContainer(final Long companyId) throws URISyntaxException, StorageException {
		final String containerName = NAME_CONTAINER.concat(companyId.toString());
		final CloudBlobContainer container = blobClient.getContainerReference(containerName);
		return container.exists() ? true : container.createIfNotExists();
	}
	
	private final CloudBlobContainer getContainer(final Long companyId) throws URISyntaxException, StorageException {
		final String containerName = companyId != null ? NAME_CONTAINER.concat(companyId.toString()) : DEFAULT_CONTAINER;
		return blobClient.getContainerReference(containerName);
	}
	
	@Transactional
	private final void dropBlobIfExiste(final String path) throws URISyntaxException, StorageException {
		final AzureBlob azureBlob = azureBlobRepository.findByFilename(path);
		if(azureBlob != null) {
			final CloudBlockBlob blobToBeDeleted = getContainer(azureBlob.getCompanyId()).getBlockBlobReference(azureBlob.getSrcname());
			blobToBeDeleted.deleteIfExists();
		}
	}
	
	private final String original(final String path) {
		if(path.lastIndexOf("/") != -1) {
			final String[] names = path.split("/");
			final String original = names[names.length - 1];
			return original.lastIndexOf(".") != -1 ? original.substring(0, original.lastIndexOf(".")) : original.substring(0, original.length());
		}
		return "";
	}
	
	private final String extension(final String path) {
		return path.lastIndexOf(".") != -1 ? path.substring(path.lastIndexOf(".") + 1, path.length()) : "";
	}
	
	private final String generateNewBlobname(final String path) {
		final String extension = extension(path);
		return UUID.randomUUID().toString().concat("-").concat(original(path)).concat(!StringUtils.isEmpty(extension) ? ".".concat(extension) : "");
	}
	
	private final String postBlob(final String path, final File file, final Long companyId) throws IOException, URISyntaxException, StorageException {
		final String blobname = generateNewBlobname(path);
		final CloudBlockBlob blob = getContainer(companyId).getBlockBlobReference(blobname);
		blob.uploadFromFile(file.getAbsolutePath());
		return blobname;
	}
	
	private final String postBlob(final String path, final MultipartFile multipartFile, final Long companyId) throws IOException, URISyntaxException, StorageException {
		final String blobname = generateNewBlobname(path);
		final CloudBlockBlob blob = getContainer(companyId).getBlockBlobReference(blobname);
		blob.upload(multipartFile.getInputStream(), multipartFile.getSize());
		return blobname;
	}
	
	@Transactional
	private final AzureBlob postOrUpdate(final String path, final String blobname, final Long companyId) {
		AzureBlob azureBlob = azureBlobRepository.findByFilename(path);
		if(azureBlob == null) {
			azureBlob = new AzureBlob(companyId);
			azureBlob.setFilename(path);
		}
		azureBlob.setSrcname(blobname);
		return azureBlobRepository.save(azureBlob);
	}
	
	@Override
	@Transactional
	public AzureBlob make(final File path, final File file, final Long companyId) throws IOException, URISyntaxException, StorageException {
		final String pathname = path.getAbsolutePath();
		if(companyId != null) {
			if(!makeContainer(companyId)) {
				throw new IOException();
			}
		}
		dropBlobIfExiste(pathname);
		final String blobname = postBlob(pathname, file, companyId);
		file.delete();
		return postOrUpdate(pathname, blobname, companyId);
	}
	
	@Override
	@Transactional
	public AzureBlob upload(final File path, final MultipartFile multipartFile, final Long companyId) throws IOException, URISyntaxException, StorageException {
		final String pathname = path.getAbsolutePath();
		if(companyId != null) {
			if(!makeContainer(companyId)) {
				throw new IOException();
			}
		}
		dropBlobIfExiste(pathname);
		final String blobname = postBlob(pathname, multipartFile, companyId);
		return postOrUpdate(pathname, blobname, companyId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public byte[] download(final File path) throws IOException, URISyntaxException, StorageException {
		final AzureBlob azureBlob = azureBlobRepository.findByFilename(path.getAbsolutePath());
		if(azureBlob != null) {
			final CloudBlockBlob blob = getContainer(azureBlob.getCompanyId()).getBlockBlobReference(azureBlob.getSrcname());
			if(blob.exists()) {
				byte[] result = new byte[(int) blob.getProperties().getLength()];
				blob.downloadToByteArray(result, 0);
				return result;
			}
		}
		return null;
	}
	
	private final File tempPath(final String blobname) {
		return new File(request.getServletContext().getRealPath(FilesUtil.TEMP_DIR).concat(File.separator).concat(UUID.randomUUID().toString()).concat(blobname));
	}
	
	@Override
	@Transactional(readOnly = true)
	public File readFile(final File path) throws IOException, URISyntaxException, StorageException {
		final AzureBlob azureBlob = azureBlobRepository.findByFilename(path.getAbsolutePath());
		if(azureBlob != null) {
			final CloudBlockBlob blob = getContainer(azureBlob.getCompanyId()).getBlockBlobReference(azureBlob.getSrcname());
			if(blob.exists()) {
				final File temp = tempPath(azureBlob.getSrcname());
				blob.downloadToFile(temp.getPath());
				return temp;
			}
		}
		return null;
	}
	
	@Override
	@Transactional
	public void delete(final File path) throws IOException, URISyntaxException, StorageException {
		final AzureBlob azureBlob = azureBlobRepository.findByFilename(path.getAbsolutePath());
		if(azureBlob != null) {
			final CloudBlockBlob blob = getContainer(azureBlob.getCompanyId()).getBlockBlobReference(azureBlob.getSrcname());
			blob.deleteIfExists();
			azureBlobRepository.delete(azureBlob);
		}
	}
	
	@Override
	@Transactional
	public void deleteDir(final Long companyId) throws IOException, URISyntaxException, StorageException {
		getContainer(companyId).deleteIfExists();
		azureBlobRepository.deleteByCompanyId(companyId);
	}
	
}
