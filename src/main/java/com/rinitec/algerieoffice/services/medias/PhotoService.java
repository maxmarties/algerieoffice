package com.rinitec.algerieoffice.services.medias;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.microsoft.azure.storage.StorageException;
import com.rinitec.algerieoffice.enums.PhotoType;
import com.rinitec.algerieoffice.persistence.dao.medias.PhotoRepository;
import com.rinitec.algerieoffice.persistence.modal.medias.Photo;
import com.rinitec.algerieoffice.services.cloud.IAzureBlobService;
import com.rinitec.algerieoffice.utils.FilesUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;

@Service
public class PhotoService implements IPhotoService {
	private static final String PHOTOS_DIR = "photos";
	private static final String COMPANY_DIR = "company_";
	
	@Value("${multipart.maxFileSize}")
    private Long imageMaxSize;
	
	private HttpServletResponse response;
	private PhotoRepository photoRepository;
	private IAzureBlobService azureBlobService;
	
	@Autowired
	public PhotoService(HttpServletResponse response, PhotoRepository photoRepository, IAzureBlobService azureBlobService) {
		this.response = response;
		this.photoRepository = photoRepository;
		this.azureBlobService = azureBlobService;
	}
	
	private final String getUploadRootDir() {
	    return FilesUtil.UPLOAD_DIR.concat(File.separator).concat(PHOTOS_DIR);
	}
	
	private final String getPhotoDir(final Photo photo) {
		return getUploadRootDir().concat(File.separator).concat(COMPANY_DIR).concat(photo.getCompanyId().toString())
				.concat(File.separator).concat(photo.getPhotoType().toString());
	}
	
	private final File getPhotoFile(final Photo photo) {
		return new File(getPhotoDir(photo).concat(File.separator).concat(photo.getId().toString()).concat(".")
				.concat(photo.getContentType().split("/")[1]));
	}
	
	@Transactional
	private final Photo createPhoto(final MultipartFile file, final Long companyId, final PhotoType photoType) {
		final Photo photo = new Photo();
		photo.setCompanyId(companyId);
		photo.setFilename(FilesUtil.getOriginalFilename(file));
		photo.setContentType(file.getContentType());
		photo.setPhotoType(photoType);
		return photoRepository.save(photo);
	}
	
	@Override
	@Transactional
	public Photo addPhoto(final MultipartFile file, final Long companyId, final PhotoType photoType) {
		if(file == null || !FilesUtil.isValideImage(file)) {
			throw new InvalidImageException("message.input.image");
		}
		if(file.getSize() == 0) {
			throw new InvalidImageException("message.error.empty");
		}
		if(file.getSize() > imageMaxSize) {
			throw new InvalidImageException("message.error.size");
		}
		final Photo photo = createPhoto(file, companyId, photoType);
		try {
			azureBlobService.upload(getPhotoFile(photo), file, companyId);
		} catch (IOException | URISyntaxException | StorageException e) {
			e.printStackTrace();
			throw new AccessUploadException("message.error.upload");
		}
		return photo;
	}
	
	@Override
	public Photo updatePhoto(final UUID photoId, final MultipartFile file) {
		if(file == null || !FilesUtil.isValideImage(file)) {
			throw new InvalidImageException("message.input.image");
		}
		if(file.getSize() == 0) {
			throw new InvalidImageException("message.error.empty");
		}
		if(file.getSize() > imageMaxSize) {
			throw new InvalidImageException("message.error.size");
		}
		final Photo photo = photoRepository.findById(photoId).get();
		try {
			azureBlobService.upload(getPhotoFile(photo), file, photo.getCompanyId());
		} catch (IOException | URISyntaxException | StorageException e) {
			e.printStackTrace();
			throw new AccessUploadException("message.error.upload");
		}
		return photo;
	}
	
	@Override
	@Transactional(readOnly = true)
	public void downloadPhoto(final UUID photoId, final Integer width, final Integer height) {
		final Optional<Photo> uOptional = photoRepository.findById(photoId);
		if(uOptional.isPresent()) {
			final Photo photo = uOptional.get();
			try {
				final byte[] blob = azureBlobService.download(getPhotoFile(photo));
				if(blob != null) {
					response.setHeader("Content-Disposition", "inline;filename=\"" + photo.getFilename() + "\"");
					response.setContentType(photo.getContentType());
					if(width != null && height != null) {
						FilesUtil.downloadImage(blob, photo.getContentType().split("/")[1], width, height, response);
					} else {
						FilesUtil.downloadFile(blob, response);
					}
				}
			} catch (IOException | URISyntaxException | StorageException e) {
				e.printStackTrace();
				System.out.println("Photo >> UploadsUtil.downloadFile rejected with: " + e.getMessage());
			}
		}
	}
	
	@Override
	@Transactional
	public void deletePhoto(final UUID photoId) {
		final Optional<Photo> uOptional = photoRepository.findById(photoId);
		if(uOptional.isPresent()) {
			final Photo photo = uOptional.get();
			final File file = getPhotoFile(photo);
			try {
				azureBlobService.delete(file);
			} catch (IOException | URISyntaxException | StorageException e) {
				e.printStackTrace();
				throw new AccessUploadException("message.error.upload");
			}
			photoRepository.delete(photo);
		}
	}
	
	@Override
	@Transactional
	public void deleteAllPhoto(final List<String> photosUUID) {
		for (final String photoUUID : photosUUID) {
			try {
				deletePhoto(UUID.fromString(photoUUID));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				throw new AccessUploadException("message.error.upload");
			}
		}
	}
	
	@Override
	@Transactional
	public void deleteAllPhotos(final List<UUID> photosUUID) {
		for (final UUID uuid : photosUUID) {
			if(uuid != null) deletePhoto(uuid);
		}
	}
	
	@Transactional
	private final Photo postPhoto(final File file, final Long companyId, final PhotoType photoType) {
		final Photo photo = new Photo();
		photo.setCompanyId(companyId);
		photo.setFilename(file.getName());
		photo.setContentType("image/jpeg");
		photo.setPhotoType(photoType);
		return photoRepository.save(photo);
	}
	
	@Override
	@Transactional
	public Photo addPhotoFile(final File file, final Long companyId, final PhotoType photoType) {
		final Photo photo = postPhoto(file, companyId, photoType);
		try {
			azureBlobService.make(getPhotoFile(photo), file, companyId);
		} catch (IOException | URISyntaxException | StorageException e) {
			e.printStackTrace();
			throw new AccessUploadException("message.error.upload");
		}
		return photo;
	}
	
	@Override
	@Transactional
	public void deleteCompanyDir(final Long companyId) {
		photoRepository.deleteByCompanyId(companyId);
		try {
			azureBlobService.deleteDir(companyId);
		} catch (IOException | URISyntaxException | StorageException e) {
			e.printStackTrace();
			System.out.println("Photo >> delete company dir rejected with: " + e.getMessage());
		}
	}
	
}
