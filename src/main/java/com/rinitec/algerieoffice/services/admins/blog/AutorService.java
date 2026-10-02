package com.rinitec.algerieoffice.services.admins.blog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.AutorRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.blog.BlogRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.Autor;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.services.medias.IAvatarService;
import com.rinitec.algerieoffice.web.error.exception.AccessLeaderException;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.SocialExistException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.admins.blog.AutorForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.blog.AutorLine;

@Service
public class AutorService implements IAutorService {

	private AutorRepository autorRepository;
	private BlogRepository blogRepository;
	private IAvatarService avatarService;
	
	@Autowired
	public AutorService(AutorRepository autorRepository, BlogRepository blogRepository, 
			IAvatarService avatarService) {
		this.autorRepository = autorRepository;
		this.blogRepository = blogRepository;
		this.avatarService = avatarService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean existsByIdentify(final String identify) {
		return autorRepository.existsByIdentify(identify);
	}
	
	private final AutorForm parseAutorForm(final Autor autor) {
		final AutorForm autorForm = new AutorForm();
		autorForm.setId(autor.getId());
		autorForm.setAutorname(autor.getAutorname());
		autorForm.setIdentify(autor.getIdentify());
		autorForm.setCheckedIdentify(autor.getIdentify());
		autorForm.setFunction(autor.getFunction());
		autorForm.setBiography(autor.getBiography());
		autorForm.setEmail(autor.getEmail());
		autorForm.setFacebook(autor.getFacebook());
		autorForm.setTwitter(autor.getTwitter());
		autorForm.setLinkedin(autor.getLinkedin());
		autorForm.setHasAvatar(autor.getHasAvatar());
		autorForm.setUrlAvatar(autor.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + autor.getId() + "&type=" + AvatarType.autor 
				: "/static/picts/avatars/account-min.jpg");
		return autorForm;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AutorForm readAutorForm(final Long id) {
		final Optional<Autor> uOptional = autorRepository.findById(id);
		if(uOptional.isPresent()) {
			return parseAutorForm(uOptional.get());
		}
		return null;
	}
	
	private final Autor postAutor(final Autor autor, final AutorForm autorForm) {
		autor.setAutorname(autorForm.getAutorname());
		autor.setIdentify(autorForm.getIdentify());
		autor.setFunction(autorForm.getFunction());
		autor.setBiography(autorForm.getBiography());
		autor.setEmail(!StringUtils.isEmpty(autorForm.getEmail()) ? autorForm.getEmail() : null);
		autor.setFacebook(!StringUtils.isEmpty(autorForm.getFacebook()) ? autorForm.getFacebook() : null);
		autor.setTwitter(!StringUtils.isEmpty(autorForm.getTwitter()) ? autorForm.getTwitter() : null);
		autor.setLinkedin(!StringUtils.isEmpty(autorForm.getLinkedin()) ? autorForm.getLinkedin() : null);
		autor.setHasAvatar(autorForm.isHasAvatar());
		return autorRepository.save(autor);
	}
	
	@Override
	@Transactional
	public Autor addAutor(final AutorForm autorForm) {
		if(autorRepository.existsByIdentify(autorForm.getIdentify())) {
			throw new UrlUnavailableException("message.error.identify");
		}
		if(!StringUtils.isEmpty(autorForm.getEmail()) && autorRepository.existsByEmail(autorForm.getEmail())) {
			throw new AlreadyExistException("message.error.alreadyexist");
		}
		if(!StringUtils.isEmpty(autorForm.getFacebook()) && autorRepository.existsByFacebook(autorForm.getFacebook())) {
			throw new SocialExistException("facebook");
		}
		if(!StringUtils.isEmpty(autorForm.getTwitter()) && autorRepository.existsByTwitter(autorForm.getTwitter())) {
			throw new SocialExistException("twitter");
		}
		if(!StringUtils.isEmpty(autorForm.getLinkedin()) && autorRepository.existsByLinkedin(autorForm.getLinkedin())) {
			throw new SocialExistException("linkedin");
		}
		final Autor autor = postAutor(new Autor(), autorForm);
		if(autorForm.isHasAvatar()) {
			avatarService.postOrUpdate(autorForm.getFile(), autor.getId(), AvatarType.autor);
		}
		return autor;
	}
	
	@Override
	@Transactional
	public Autor updateAutor(final AutorForm autorForm) {
		final Optional<Autor> uOptional = autorRepository.findById(autorForm.getId());
		if(uOptional.isPresent()) {
			final Autor autor = uOptional.get();
			if(!autorForm.getIdentify().equalsIgnoreCase(autor.getIdentify()) && autorRepository.existsByIdentify(autorForm.getIdentify())) {
				throw new UrlUnavailableException("message.error.identify");
			}
			if(!StringUtils.isEmpty(autorForm.getEmail()) && !autorForm.getEmail().equalsIgnoreCase(autor.getEmail()) 
					&& autorRepository.existsByEmail(autorForm.getEmail())) {
				throw new AlreadyExistException("message.error.alreadyexist");
			}
			if(!StringUtils.isEmpty(autorForm.getFacebook()) && !autorForm.getFacebook().equalsIgnoreCase(autor.getFacebook()) 
					&& autorRepository.existsByFacebook(autorForm.getFacebook())) {
				throw new SocialExistException("facebook");
			}
			if(!StringUtils.isEmpty(autorForm.getTwitter()) && !autorForm.getTwitter().equalsIgnoreCase(autor.getTwitter()) 
					&& autorRepository.existsByTwitter(autorForm.getTwitter())) {
				throw new SocialExistException("twitter");
			}
			if(!StringUtils.isEmpty(autorForm.getLinkedin()) && !autorForm.getLinkedin().equalsIgnoreCase(autor.getLinkedin()) 
					&& autorRepository.existsByLinkedin(autorForm.getLinkedin())) {
				throw new SocialExistException("linkedin");
			}
			if(autorForm.isHasFileChanged()) {
				if(autorForm.isHasAvatar()) {
					avatarService.postOrUpdate(autorForm.getFile(), autor.getId(), AvatarType.autor);
				} else {
					avatarService.deleteAvatar(autor.getId(), AvatarType.autor);
				}
			}
			return postAutor(autor, autorForm);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAutorsList(final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = autorRepository.countAllAutorCriteria(search);
		final List<AutorLine> lines = countResult == 0L ? new ArrayList<AutorLine>() 
				: autorRepository.findAllAutorCriteria(search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Autor deleteAutor(final Long id) {
		final Optional<Autor> uOptional = autorRepository.findById(id);
		if(!uOptional.isPresent()) {
			throw new NotFoundException("message.error.notfound");
		}
		final Autor autor = uOptional.get();
		if(blogRepository.countByAutorId(id) != 0L) {
			throw new AccessLeaderException("message.error.autor");
		}
		if(autor.getHasAvatar()) {
			avatarService.deleteAvatar(id, AvatarType.autor);
		}
		autorRepository.delete(autor);
		return autor;
	}
	
	@Override
	@Transactional
	public void deleteAutors(final List<Long> lines) {
		if(lines.isEmpty() || lines.size() != (int) autorRepository.countAutors(lines)) {
			throw new NotFoundException("message.error.notfound");
		}
		if(!blogRepository.findAllBlogsByAutorIds(lines).isEmpty()) {
			throw new AccessLeaderException("message.error.autor");
		}
		autorRepository.deleteAutors(lines);
		avatarService.deleteAllAvatar(lines, AvatarType.autor);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserMini> findAllAutors() {
		return autorRepository.findAllAutors();
	}
	
}
