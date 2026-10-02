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
import com.rinitec.algerieoffice.enums.BannerType;
import com.rinitec.algerieoffice.persistence.dao.medias.BannerRepository;
import com.rinitec.algerieoffice.persistence.modal.medias.Banner;
import com.rinitec.algerieoffice.services.cloud.IAzureBlobService;
import com.rinitec.algerieoffice.utils.FilesUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;

@Service
public class BannerService implements IBannerService {
	private static final String BANNERS_DIR = "aobns";
	
	@Value("${multipart.maxFileSize}")
    private Long imageMaxSize;
	
	private HttpServletResponse response;
	private BannerRepository bannerRepository;
	private IAzureBlobService azureBlobService;
	
	@Autowired
	public BannerService(HttpServletResponse response, BannerRepository bannerRepository, IAzureBlobService azureBlobService) {
		this.response = response;
		this.bannerRepository = bannerRepository;
		this.azureBlobService = azureBlobService;
	}
	
	private final String getUploadRootDir() {
	    return FilesUtil.UPLOAD_DIR.concat(File.separator).concat(BANNERS_DIR);
	}
	
	private final String getBannerDir(final Banner banner) {
		return getUploadRootDir().concat(File.separator).concat(File.separator).concat(banner.getBannerType().toString());
	}
	
	private final File getBannerFile(final Banner banner) {
		return new File(getBannerDir(banner).concat(File.separator).concat(banner.getId().toString()).concat(".")
				.concat(banner.getContentType().split("/")[1]));
	}
	
	@Transactional
	private final Banner createBanner(final MultipartFile file, final BannerType bannerType) {
		final Banner banner = new Banner();
		banner.setFilename(FilesUtil.getOriginalFilename(file));
		banner.setContentType(file.getContentType());
		banner.setBannerType(bannerType);
		return bannerRepository.save(banner);
	}
	
	@Override
	@Transactional
	public Banner addBanner(final MultipartFile file, final BannerType bannerType) {
		if(file == null || !FilesUtil.isValideImage(file)) {
			throw new InvalidImageException("message.input.image");
		}
		if(file.getSize() == 0) {
			throw new InvalidImageException("message.error.empty");
		}
		if(file.getSize() > imageMaxSize) {
			throw new InvalidImageException("message.error.size");
		}
		final Banner banner = createBanner(file, bannerType);
		try {
			azureBlobService.upload(getBannerFile(banner), file, null);
		} catch (IOException | URISyntaxException | StorageException e) {
			e.printStackTrace();
			throw new AccessUploadException("message.error.upload");
		}
		return banner;
	}
	
	@Override
	@Transactional
	public Banner updateBanner(final UUID bannerId, final MultipartFile file) {
		if(file == null || !FilesUtil.isValideImage(file)) {
			throw new InvalidImageException("message.input.image");
		}
		if(file.getSize() == 0) {
			throw new InvalidImageException("message.error.empty");
		}
		if(file.getSize() > imageMaxSize) {
			throw new InvalidImageException("message.error.size");
		}
		final Banner banner = bannerRepository.findById(bannerId).get();
		try {
			azureBlobService.upload(getBannerFile(banner), file, null);
		} catch (IOException | URISyntaxException | StorageException e) {
			e.printStackTrace();
			throw new AccessUploadException("message.error.upload");
		}
		return banner;
	}
	
	@Override
	@Transactional(readOnly = true)
	public void downloadBanner(final UUID bannerId, final Integer width, final Integer height) {
		final Optional<Banner> uOptional = bannerRepository.findById(bannerId);
		if(uOptional.isPresent()) {
			final Banner banner = uOptional.get();
			try {
				final byte[] blob = azureBlobService.download(getBannerFile(banner));
				if(blob != null) {
					response.setHeader("Content-Disposition", "inline;filename=\"" + banner.getFilename() + "\"");
					response.setContentType(banner.getContentType());
					if(width != null && height != null) {
						FilesUtil.downloadImage(blob, banner.getContentType().split("/")[1], width, height, response);
					} else {
						FilesUtil.downloadFile(blob, response);
					}
				}
			} catch (IOException | URISyntaxException | StorageException e) {
				e.printStackTrace();
				System.out.println("Banner >> UploadsUtil.downloadFile rejected with: " + e.getMessage());
			}
		}
	}
	
	@Override
	@Transactional
	public void deleteBanner(final UUID bannerId) {
		final Optional<Banner> uOptional = bannerRepository.findById(bannerId);
		if(uOptional.isPresent()) {
			final Banner banner = uOptional.get();
			try {
				azureBlobService.delete(getBannerFile(banner));
			} catch (IOException | URISyntaxException | StorageException e) {
				e.printStackTrace();
				throw new AccessUploadException("message.error.upload");
			}
			bannerRepository.delete(banner);
		}
	}
	
	@Override
	@Transactional
	public void deleteAllBanner(final List<String> bannersUUID) {
		for (final String bannerUUID : bannersUUID) {
			try {
				deleteBanner(UUID.fromString(bannerUUID));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				throw new AccessUploadException("message.error.upload");
			}
		}
	}
	
	@Override
	public void deleteAllBanners(final List<UUID> bannersUUID) {
		for (final UUID bannerUUID : bannersUUID) {
			if(bannerUUID != null) deleteBanner(bannerUUID);
		}
	}
	
}
