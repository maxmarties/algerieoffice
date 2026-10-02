package com.rinitec.algerieoffice.services.medias;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.microsoft.azure.storage.StorageException;
import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.dao.medias.AvatarRepository;
import com.rinitec.algerieoffice.persistence.modal.AvatarID;
import com.rinitec.algerieoffice.persistence.modal.medias.Avatar;
import com.rinitec.algerieoffice.services.cloud.IAzureBlobService;
import com.rinitec.algerieoffice.utils.FilesUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;

@Service
public class AvatarService implements IAvatarService {
	private static final String AVATARS_DIR = "avt";
	
	@Value("${multipart.maxFileSize}")
    private Long imageMaxSize;
	
	private HttpServletResponse response;
	private AvatarRepository avatarRepository;
	private IAzureBlobService azureBlobService;
	
	@Autowired
	public AvatarService(HttpServletResponse response, AvatarRepository avatarRepository, IAzureBlobService azureBlobService) {
		this.response = response;
		this.avatarRepository = avatarRepository;
		this.azureBlobService = azureBlobService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String getFilename(final Long postedId, final AvatarType avatarType) {
		final Optional<String> uOptional = avatarRepository.findFilenameByAvatarID(new AvatarID(postedId, avatarType));
		return uOptional.isPresent() ? uOptional.get() : "";
	}
	
	private final String getUploadRootDir() {
	    return FilesUtil.UPLOAD_DIR.concat(File.separator).concat(AVATARS_DIR);
	}
	
	private final String getAvatarDir(final AvatarID avatarID) {
		return getUploadRootDir().concat(File.separator).concat(avatarID.getAvatarType().toString());
	}
	
	private final File getAvatarFile(final Avatar avatar) {
		return new File(getAvatarDir(avatar.getAvatarID()).concat(File.separator).concat(avatar.getAvatarID().getAvatarType().toString())
				.concat("_").concat(avatar.getAvatarID().getPostedId().toString()).concat(".")
				.concat(avatar.getContentType().split("/")[1]));
	}
	
	@Override
	@Transactional
	public Avatar postOrUpdate(final MultipartFile file, final Long postedId, final AvatarType avatarType) {
		if(file == null || !FilesUtil.isValideImage(file)) {
			throw new InvalidImageException("message.input.image");
		}
		if(file.getSize() == 0) {
			throw new InvalidImageException("message.error.empty");
		}
		if(file.getSize() > imageMaxSize) {
			throw new InvalidImageException("message.error.size");
		}
		final AvatarID avatarID = new AvatarID(postedId, avatarType);
		Avatar avatar = avatarRepository.findByAvatarID(avatarID);
		if(avatar == null) {
			avatar = new Avatar(avatarID);
		}
		avatar.setFilename(FilesUtil.getOriginalFilename(file));
		avatar.setContentType(file.getContentType());
		try {
			azureBlobService.upload(getAvatarFile(avatar), file, null);
		} catch (IOException | URISyntaxException | StorageException e) {
			e.printStackTrace();
			throw new AccessUploadException("message.error.upload");
		}
		return avatarRepository.save(avatar);
	}
	
	@Override
	@Transactional(readOnly = true)
	public void downloadAvatar(final Long postedId, final AvatarType avatarType, final Integer width, final Integer height) {
		final Avatar avatar = avatarRepository.findByAvatarID(new AvatarID(postedId, avatarType));
		if(avatar != null) {
			try {
				final byte[] blob = azureBlobService.download(getAvatarFile(avatar));
				if(blob != null) {
					response.setHeader("Content-Disposition", "inline;filename=\"" + avatar.getFilename() + "\"");
					response.setContentType(avatar.getContentType());
					if(width != null && height != null) {
						FilesUtil.downloadImage(blob, avatar.getContentType().split("/")[1], width, height, response);
					} else {
						FilesUtil.downloadFile(blob, response);
					}
				}
			} catch (IOException | URISyntaxException | StorageException e) {
				e.printStackTrace();
				System.out.println("Avatar >> UploadsUtil.downloadFile rejected with: " + e.getMessage());
			}
		}
	}
	
	@Override
	@Transactional
	public void deleteAvatar(final Long postedId, final AvatarType avatarType) {
		final Avatar avatar = avatarRepository.findByAvatarID(new AvatarID(postedId, avatarType));
		if(avatar != null) {
			try {
				azureBlobService.delete(getAvatarFile(avatar));
			} catch (IOException | URISyntaxException | StorageException e) {
				throw new AccessUploadException("message.error.upload");
			}
			avatarRepository.delete(avatar);
		}
	}
	
	@Override
	@Transactional
	public void deleteAllAvatar(final List<Long> postedsId, final AvatarType avatarType) {
		for (final Long postedId : postedsId) {
			if(postedId != null) deleteAvatar(postedId, avatarType);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public File readFile(final Long postedId, final AvatarType avatarType) {
		final Avatar avatar = avatarRepository.findByAvatarID(new AvatarID(postedId, avatarType));
		if(avatar != null) {
			try {
				return azureBlobService.readFile(getAvatarFile(avatar));
			} catch (IOException | URISyntaxException | StorageException e) {e.printStackTrace();}
		}
		return null;
	}
	
	@Override
	@Transactional
	public void deleteAllAvatarCompany(final Long companyId, final List<Long> agentIds) {
		final AvatarType[] types = {AvatarType.company, AvatarType.cover, AvatarType.extra, AvatarType.about};
		for (final AvatarType type : types) {
			deleteAvatar(companyId, type);
		}
		for (final Long agentId : agentIds) {
			if(agentId != null) deleteAvatar(agentId, AvatarType.agent);
		}
	}
	
}
