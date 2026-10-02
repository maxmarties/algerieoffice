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
import com.rinitec.algerieoffice.enums.FileType;
import com.rinitec.algerieoffice.persistence.dao.admins.premium.PremiumRepository;
import com.rinitec.algerieoffice.persistence.dao.medias.FilereaderRepository;
import com.rinitec.algerieoffice.persistence.modal.medias.Filereader;
import com.rinitec.algerieoffice.services.cloud.IAzureBlobService;
import com.rinitec.algerieoffice.ujson.FilereaderResponse;
import com.rinitec.algerieoffice.utils.FilesUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidFileException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

@Service
public class FilereaderService implements IFilereaderService {
	private static final String FILES_DIR = "filereaders";
	private static final String COMPANY_DIR = "company_";
	
	@Value("${multipart.maxFileSize}")
    private Long fileMaxSize;
	
	private HttpServletResponse response;
	private FilereaderRepository filereaderRepository;
	private PremiumRepository premiumRepository;
	private IAzureBlobService azureBlobService;
	
	@Autowired
	public FilereaderService(HttpServletResponse response, FilereaderRepository filereaderRepository, PremiumRepository premiumRepository, IAzureBlobService azureBlobService) {
		this.response = response;
		this.filereaderRepository = filereaderRepository;
		this.premiumRepository = premiumRepository;
		this.azureBlobService = azureBlobService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String getFilename(final UUID fileId) {
		final Optional<String> uOptional = filereaderRepository.findFilenameByFilereaderId(fileId);
		return uOptional.isPresent() ? uOptional.get() : "";
	}
	
	private final String getUploadRootDir() {
	    return FilesUtil.UPLOAD_DIR.concat(File.separator).concat(FILES_DIR);
	}
	
	private final String getFileDir(final Filereader filereader) {
		return getUploadRootDir().concat(File.separator).concat(COMPANY_DIR).concat(filereader.getCompanyId().toString())
				.concat(File.separator).concat(filereader.getFileType().toString());
	}
	
	private final File getFilereaderFile(final Filereader filereader) {
		return new File(getFileDir(filereader).concat(File.separator).concat(filereader.getId().toString()).concat(".")
				.concat(filereader.getContentType().split("/")[1]));
	}
	
	@Transactional
	private final Filereader createFilereader(final MultipartFile file, final Long companyId, final FileType fileType) {
		final Filereader filereader = new Filereader();
		filereader.setCompanyId(companyId);
		filereader.setFilename(FilesUtil.getOriginalFilename(file));
		filereader.setContentType(file.getContentType());
		filereader.setFileType(fileType);
		filereader.setUploadDate(new DateTime(Date.from(Instant.now())));
		return filereaderRepository.save(filereader);
	}
	
	@Override
	@Transactional
	public Filereader addFilereader(final MultipartFile file, final Long companyId, final FileType fileType) {
		if(file == null || !FilesUtil.isValidePDF(file)) {
			throw new InvalidFileException("message.input.file");
		}
		if(file.getSize() == 0) {
			throw new InvalidFileException("message.error.empty");
		}
		if(file.getSize() > fileMaxSize) {
			throw new InvalidFileException("message.error.size");
		}
		final Filereader filereader = createFilereader(file, companyId, fileType);
		try {
			azureBlobService.upload(getFilereaderFile(filereader), file, companyId);
		} catch (IOException | URISyntaxException | StorageException e) {
			e.printStackTrace();
			throw new AccessUploadException("message.error.upload");
		}
		return filereader;
	}
	
	@Override
	@Transactional
	public Filereader updateFilereader(final UUID fileId, final MultipartFile file) {
		if(file == null || !FilesUtil.isValidePDF(file)) {
			throw new InvalidFileException("message.input.file");
		}
		if(file.getSize() == 0) {
			throw new InvalidFileException("message.error.empty");
		}
		if(file.getSize() > fileMaxSize) {
			throw new InvalidFileException("message.error.size");
		}
		final Filereader filereader = filereaderRepository.findById(fileId).get();
		try {
			azureBlobService.upload(getFilereaderFile(filereader), file, filereader.getCompanyId());
		} catch (IOException | URISyntaxException | StorageException e) {
			e.printStackTrace();
			throw new AccessUploadException("message.error.upload");
		}
		return filereader;
	}
	
	@Override
	@Transactional(readOnly = true)
	public void downloadFilereader(final UUID fileId) {
		final Optional<Filereader> uOptional = filereaderRepository.findById(fileId);
		if(uOptional.isPresent()) {
			final Filereader filereader = uOptional.get();
			if(!filereader.getFileType().equals(FileType.document)) {
				try {
					final byte[] blob = azureBlobService.download(getFilereaderFile(filereader));
					if(blob != null) {
						response.setHeader("Content-Disposition", "inline;filename=\"" + filereader.getFilename() + "\"");
						response.setContentType(filereader.getContentType());
						FilesUtil.downloadFile(blob, response);
					}
				} catch (IOException | URISyntaxException | StorageException e) {
					e.printStackTrace();
					System.out.println("Filereader >> UploadsUtil.downloadFile rejected with: " + e.getMessage());
				}
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public void downloadDocument(final UUID fileId, final Long companyId) {
		final Optional<Filereader> uOptional = filereaderRepository.findById(fileId);
		if(uOptional.isPresent()) {
			final Filereader filereader = uOptional.get();
			if(filereader.getCompanyId().equals(companyId) && filereader.getFileType().equals(FileType.document)) {
				try {
					final byte[] blob = azureBlobService.download(getFilereaderFile(filereader));
					if(blob != null) {
						response.setHeader("Content-Disposition", "inline;filename=\"" + filereader.getFilename() + "\"");
						response.setContentType(filereader.getContentType());
						FilesUtil.downloadFile(blob, response);
					}
				} catch (IOException | URISyntaxException | StorageException e) {
					e.printStackTrace();
					System.out.println("Filereader >> UploadsUtil.downloadFile rejected with: " + e.getMessage());
				}
			}
		}
	}
	
	@Override
	@Transactional
	public void deleteFilereader(final UUID fileId) {
		final Optional<Filereader> uOptional = filereaderRepository.findById(fileId);
		if(uOptional.isPresent()) {
			final Filereader filereader = uOptional.get();
			try {
				azureBlobService.delete(getFilereaderFile(filereader));
			} catch (IOException | URISyntaxException | StorageException e) {
				e.printStackTrace();
				throw new AccessUploadException("message.error.upload");
			}
			filereaderRepository.delete(filereader);
		}
	}
	
	@Override
	@Transactional
	public void deleteAllFilereaders(final List<UUID> filesUUID) {
		for (final UUID fileId : filesUUID) {
			if(fileId != null) deleteFilereader(fileId);
		}
	}
	
	private FilereaderResponse readFilereaderResponse(final Filereader filereader) {
		final FilereaderResponse filereaderResponse = new FilereaderResponse();
		filereaderResponse.setId(filereader.getId().toString());
		filereaderResponse.setUrl(ConstraintesURL.URL_FILES + "?fileId=".concat(filereader.getId().toString()));
		filereaderResponse.setFilename(filereader.getFilename().concat(".").concat(filereader.getContentType().split("/")[1]));
		return filereaderResponse;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<FilereaderResponse> findProxyFilereaders(final Long companyId, final String search, final int page, final int rows) {
		final List<FilereaderResponse> filereadersResponses = new ArrayList<FilereaderResponse>();
		final List<Filereader> filereaders = filereaderRepository.findProxyFilereaders(companyId, search, page, rows);
		for (final Filereader filereader : filereaders) {
			filereadersResponses.add(readFilereaderResponse(filereader));
		}
		return filereadersResponses;
	}
	
	@Override
	@Transactional
	public void deleteProxyFilereader(final String id, final Long companyId) {
		try {
			final Optional<Filereader> uOptional = filereaderRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent() || !companyId.equals(uOptional.get().getCompanyId())) {
				throw new NotFoundException("message.error.notfound");
			}
			final Filereader filereader = uOptional.get();
			try {
				azureBlobService.delete(getFilereaderFile(filereader));
			} catch (IOException | URISyntaxException | StorageException e) {
				e.printStackTrace();
				throw new AccessUploadException("message.error.upload");
			}
			filereaderRepository.delete(filereader);
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
		filereaderRepository.deleteByCompanyId(companyId);
		try {
			azureBlobService.deleteDir(companyId);
		} catch (IOException | URISyntaxException | StorageException e) {
			e.printStackTrace();
		}
	}
	
}
