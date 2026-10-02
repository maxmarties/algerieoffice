package com.rinitec.algerieoffice.services.medias;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.microsoft.azure.storage.StorageException;
import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumRepository;
import com.rinitec.algerieoffice.persistence.dao.medias.ImageRepository;
import com.rinitec.algerieoffice.persistence.modal.medias.Image;
import com.rinitec.algerieoffice.services.cloud.IAzureBlobService;
import com.rinitec.algerieoffice.ujson.ImageResponse;
import com.rinitec.algerieoffice.utils.FilesUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

@Service
public class ImageService implements IImageService {
	private static final String IMAGES_DIR = "images";
	private static final String COMPANY_DIR = "company_";
	
	@Value("${multipart.maxFileSize}")
    private Long imageMaxSize;
	
	private HttpServletResponse response;
	private ImageRepository imageRepository;
	private PremiumRepository premiumRepository;
	private IAzureBlobService azureBlobService;
	
	@Autowired
	public ImageService(HttpServletResponse response, ImageRepository imageRepository, PremiumRepository premiumRepository, IAzureBlobService azureBlobService) {
		this.response = response;
		this.imageRepository = imageRepository;
		this.premiumRepository = premiumRepository;
		this.azureBlobService = azureBlobService;
	}
	
	private final String getUploadRootDir() {
	    return FilesUtil.UPLOAD_DIR.concat(File.separator).concat(IMAGES_DIR);
	}
	
	private final String getImageDir(final Image image) {
		return getUploadRootDir().concat(File.separator).concat(COMPANY_DIR).concat(image.getCompanyId().toString());
	}
	
	private final File getImageFile(final Image image) {
		return new File(getImageDir(image).concat(File.separator).concat(image.getId().toString()).concat(".")
				.concat(image.getContentType().split("/")[1]));
	}
	
	@Transactional
	private final Image createImage(final MultipartFile file, final Long companyId) {
		final Image image = new Image();
		image.setCompanyId(companyId);
		image.setFilename(FilesUtil.getOriginalFilename(file));
		image.setContentType(file.getContentType());
		image.setUploadDate(new DateTime(Date.from(Instant.now())));
		return imageRepository.save(image);
	}

	@Override
	@Transactional
	public Image addImage(final MultipartFile file, final Long companyId) {
		if(file == null || !FilesUtil.isValideImage(file)) {
			throw new InvalidImageException("message.input.image");
		}
		if(file.getSize() == 0) {
			throw new InvalidImageException("message.error.empty");
		}
		if(file.getSize() > imageMaxSize) {
			throw new InvalidImageException("message.error.size");
		}
		final Image image = createImage(file, companyId);
		final File path = getImageFile(image);
		try {
			azureBlobService.upload(path, file, companyId);
		} catch (IOException | URISyntaxException | StorageException e) {
			e.printStackTrace();
			throw new AccessUploadException("message.error.upload");
		}
		return image;
	}
	
	@Override
	@Transactional(readOnly = true)
	public void downloadImage(final UUID imageId, final Integer width, final Integer height) {
		final Optional<Image> uOptional = imageRepository.findById(imageId);
		if(uOptional.isPresent()) {
			final Image image = uOptional.get();
			try {
				final byte[] blob = azureBlobService.download(getImageFile(image));
				if(blob != null) {
					response.setHeader("Content-Disposition", "inline;filename=\"" + image.getFilename() + "\"");
					response.setContentType(image.getContentType());
					if(width != null && height != null) {
						FilesUtil.downloadImage(blob, image.getContentType().split("/")[1], width, height, response);
					} else {
						FilesUtil.downloadFile(blob, response);
					}
				}
			} catch (IOException | URISyntaxException | StorageException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	@Transactional
	public void deleteImage(final UUID imageId) {
		final Optional<Image> uOptional = imageRepository.findById(imageId);
		if(uOptional.isPresent()) {
			final Image image = uOptional.get();
			try {
				azureBlobService.delete(getImageFile(image));
			} catch (IOException | URISyntaxException | StorageException e) {
				e.printStackTrace();
				throw new AccessUploadException("message.error.upload");
			}
			imageRepository.delete(image);
		}
	}
	
	private final ImageResponse readImageResponse(final Image image) {
		final ImageResponse imageResponse = new ImageResponse();
		imageResponse.setId(image.getId().toString());
		imageResponse.setThumb(ConstraintesURL.URL_IMAGES + "?uuid=".concat(image.getId().toString()).concat("&width=100&height=100"));
		imageResponse.setUrl(ConstraintesURL.URL_IMAGES + "?uuid=".concat(image.getId().toString()));
		imageResponse.setFilename(image.getFilename());
		return imageResponse;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ImageResponse> findProxyImages(final Long companyId, final String search, final int page, final int rows) {
		final List<ImageResponse> imagesResponses = new ArrayList<ImageResponse>();
		final List<Image> images = imageRepository.findProxyImages(companyId, search, page, rows);
		for (final Image image : images) {
			imagesResponses.add(readImageResponse(image));
		}
		return imagesResponses;
	}
	
	@Override
	@Transactional
	public void deleteProxyImage(final String id, final Long companyId) {
		try {
			final Optional<Image> uOptional = imageRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Image image = uOptional.get();
			try {
				azureBlobService.delete(getImageFile(image));
			} catch (IOException | URISyntaxException | StorageException e) {
				e.printStackTrace();
				throw new AccessUploadException("message.error.upload");
			}
			imageRepository.delete(image);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Transactional(readOnly = true)
	private final int readPassPremium(final Long companyId) {
		final Optional<Integer> uOptional = premiumRepository.findPremiumPassByCompanyId(companyId, new DateTime(Date.from(Instant.now())));
		return uOptional.isPresent() ? uOptional.get() : 0;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean hasLimitSpace(final Long companyId) {
		return azureBlobService.hasMaxSpace(companyId);
	}
	
	@Override
	@Transactional
	public void deleteCompanyDir(final Long companyId) {
		imageRepository.deleteByCompanyId(companyId);
		try {
			azureBlobService.deleteDir(companyId);
		} catch (IOException | URISyntaxException | StorageException e) {
			e.printStackTrace();
		}
	}
	
}
