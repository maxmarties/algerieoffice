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
import com.rinitec.algerieoffice.enums.EnvelopeType;
import com.rinitec.algerieoffice.persistence.dao.medias.EnvelopeRepository;
import com.rinitec.algerieoffice.persistence.modal.medias.Envelope;
import com.rinitec.algerieoffice.services.cloud.IAzureBlobService;
import com.rinitec.algerieoffice.utils.FilesUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;

@Service
public class EnvelopeService implements IEnvelopeService {
	private static final String ENVELOPES_DIR = "envelopes";
	
	@Value("${multipart.maxFileSize}")
    private Long imageMaxSize;
	
	private HttpServletResponse response;
	private EnvelopeRepository envelopeRepository;
	private IAzureBlobService azureBlobService;
	
	@Autowired
	public EnvelopeService(HttpServletResponse response, EnvelopeRepository envelopeRepository, IAzureBlobService azureBlobService) {
		this.response = response;
		this.envelopeRepository = envelopeRepository;
		this.azureBlobService = azureBlobService;
	}
	
	private final String getUploadRootDir() {
	    return FilesUtil.UPLOAD_DIR.concat(File.separator).concat(ENVELOPES_DIR);
	}
	
	private final String getEnvelopeDir(final Envelope envelope) {
		return getUploadRootDir().concat(File.separator).concat(envelope.getEnvelopeType().toString());
	}
	
	private final File getEnvelopeFile(final Envelope envelope) {
		return new File(getEnvelopeDir(envelope).concat(File.separator).concat(envelope.getId().toString()).concat(".")
				.concat(envelope.getContentType().split("/")[1]));
	}
	
	@Transactional
	private final Envelope createEnvelope(final MultipartFile file, final EnvelopeType envelopeType) {
		final Envelope envelope = new Envelope();
		envelope.setFilename(FilesUtil.getOriginalFilename(file));
		envelope.setContentType(file.getContentType());
		envelope.setEnvelopeType(envelopeType);
		return envelopeRepository.save(envelope);
	}
	
	@Override
	@Transactional
	public Envelope addEnvelope(final MultipartFile file, final EnvelopeType envelopeType) {
		if(file == null || !FilesUtil.isValideImage(file)) {
			throw new InvalidImageException("message.input.image");
		}
		if(file.getSize() == 0) {
			throw new InvalidImageException("message.error.empty");
		}
		if(file.getSize() > imageMaxSize) {
			throw new InvalidImageException("message.error.size");
		}
		final Envelope envelope = createEnvelope(file, envelopeType);
		try {
			azureBlobService.upload(getEnvelopeFile(envelope), file, null);
		} catch (IOException | URISyntaxException | StorageException e) {
			e.printStackTrace();
			throw new AccessUploadException("message.error.upload");
		}
		return envelope;
	}
	
	@Override
	@Transactional(readOnly = true)
	public void downloadEnvelope(final UUID envelopeId) {
		final Optional<Envelope> uOptional = envelopeRepository.findById(envelopeId);
		if(uOptional.isPresent()) {
			final Envelope envelope = uOptional.get();
			try {
				final byte[] blob = azureBlobService.download(getEnvelopeFile(envelope));
				if(blob != null) {
					response.setHeader("Content-Disposition", "inline;filename=\"" + envelope.getFilename() + "\"");
					response.setContentType(envelope.getContentType());
					FilesUtil.downloadFile(blob, response);
				}
			} catch (IOException | URISyntaxException | StorageException e) {
				e.printStackTrace();
				System.out.println("Envelope >> UploadsUtil.downloadFile rejected with: " + e.getMessage());
			}
		}
	}
	
	@Override
	@Transactional
	public void deleteEnvelope(final UUID envelopeId) {
		final Optional<Envelope> uOptional = envelopeRepository.findById(envelopeId);
		if(uOptional.isPresent()) {
			final Envelope envelope = uOptional.get();
			try {
				azureBlobService.delete(getEnvelopeFile(envelope));
			} catch (IOException | URISyntaxException | StorageException e) {
				e.printStackTrace();
				throw new AccessUploadException("message.error.upload");
			}
			envelopeRepository.delete(envelope);
		}
	}
	
	@Override
	@Transactional
	public void deleteAllEnvelope(final List<String> envelopesUUID) {
		for (final String envelopeUUID : envelopesUUID) {
			try {
				deleteEnvelope(UUID.fromString(envelopeUUID));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				throw new AccessUploadException("message.error.upload");
			}
		}
	}
	
	@Override
	@Transactional
	public void deleteAll(final List<UUID> envelopesUUID) {
		for (final UUID envelopeUUID : envelopesUUID) {
			if(envelopeUUID != null) deleteEnvelope(envelopeUUID);
		}
	}
	
}
