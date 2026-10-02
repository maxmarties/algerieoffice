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
import com.rinitec.algerieoffice.persistence.dao.medias.ScreenshotRepository;
import com.rinitec.algerieoffice.persistence.modal.medias.Screenshot;
import com.rinitec.algerieoffice.services.cloud.IAzureBlobService;
import com.rinitec.algerieoffice.utils.FilesUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;

@Service
public class ScreenshotService implements IScreenshotService {
	private static final String SCREENSHOT_DIR = "screenshot";
	
	@Value("${multipart.maxFileSize}")
    private Long imageMaxSize;
	
	private HttpServletResponse response;
	private ScreenshotRepository screenshotRepository;
	private IAzureBlobService azureBlobService;
	
	@Autowired
	public ScreenshotService(HttpServletResponse response, ScreenshotRepository screenshotRepository, IAzureBlobService azureBlobService) {
		this.response = response;
		this.screenshotRepository = screenshotRepository;
		this.azureBlobService = azureBlobService;
	}
	
	private final String getUploadRootDir() {
		return FilesUtil.UPLOAD_DIR.concat(File.separator).concat(SCREENSHOT_DIR);
	}
	
	private final File getScreenshotFile(final Screenshot screenshot) {
		return new File(getUploadRootDir().concat(File.separator).concat(screenshot.getId().toString()).concat(".")
				.concat(screenshot.getContentType().split("/")[1]));
	}
	
	@Transactional
	private final Screenshot createScreenshot(final MultipartFile file) {
		final Screenshot screenshot = new Screenshot();
		screenshot.setFilename(FilesUtil.getOriginalFilename(file));
		screenshot.setContentType(file.getContentType());
		return screenshotRepository.save(screenshot);
	}
	
	@Override
	@Transactional
	public Screenshot addScreenshot(final MultipartFile file) {
		if(file == null || !FilesUtil.isValideImage(file)) {
			throw new InvalidImageException("message.input.image");
		}
		if(file.getSize() == 0) {
			throw new InvalidImageException("message.error.empty");
		}
		if(file.getSize() > imageMaxSize) {
			throw new InvalidImageException("message.error.size");
		}
		final Screenshot screenshot = createScreenshot(file);
		try {
			azureBlobService.upload(getScreenshotFile(screenshot), file, null);
		} catch (IOException | URISyntaxException | StorageException e) {
			e.printStackTrace();
			throw new AccessUploadException("message.error.upload");
		}
		return screenshot;
	}
	
	@Override
	@Transactional(readOnly = true)
	public void downloadScreenshot(final UUID screenshotId) {
		final Optional<Screenshot> uOptional = screenshotRepository.findById(screenshotId);
		if(uOptional.isPresent()) {
			final Screenshot screenshot = uOptional.get();
			try {
				final byte[] blob = azureBlobService.download(getScreenshotFile(screenshot));
				if(blob != null) {
					response.setHeader("Content-Disposition", "inline;filename=\"" + screenshot.getFilename() + "\"");
					response.setContentType(screenshot.getContentType());
					FilesUtil.downloadFile(blob, response);
				}
			} catch (IOException | URISyntaxException | StorageException e) {
				e.printStackTrace();
				System.out.println("Screenshot >> UploadsUtil.downloadFile rejected with: " + e.getMessage());
			}
		}
	}
	
	@Override
	@Transactional
	public void deleteScreenshot(final UUID screenshotId) {
		final Optional<Screenshot> uOptional = screenshotRepository.findById(screenshotId);
		if(uOptional.isPresent()) {
			final Screenshot screenshot = uOptional.get();
			try {
				azureBlobService.delete(getScreenshotFile(screenshot));
			} catch (IOException | URISyntaxException | StorageException e) {
				e.printStackTrace();
				throw new AccessUploadException("message.error.upload");
			}
			screenshotRepository.delete(screenshot);
		}
	}
	
	@Override
	@Transactional
	public void deleteScreenshots(List<UUID> lines) {
		for (final UUID line : lines) {
			if(line != null) deleteScreenshot(line);
		}
	}
	
}
