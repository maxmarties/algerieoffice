package com.rinitec.algerieoffice.services.admins.realtime;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.admins.realtime.AssistRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.realtime.ProblemRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.realtime.TestimonialRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Assist;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Problem;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Testimonial;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.admins.realtime.AdmTestimonialForm;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmAssistLine;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmProblemLine;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmTestimonialLine;

@Service
public class AdmRepportService implements IAdmRepportService {

	private UserRepository userRepository;
	private CompanyRepository companyRepository;
	private TestimonialRepository testimonialRepository;
	private AssistRepository assistRepository;
	private ProblemRepository problemRepository;
	
	@Autowired
	public AdmRepportService(UserRepository userRepository, CompanyRepository companyRepository, TestimonialRepository testimonialRepository, 
			AssistRepository assistRepository, ProblemRepository problemRepository) {
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.testimonialRepository = testimonialRepository;
		this.assistRepository = assistRepository;
		this.problemRepository = problemRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findTestimonialList(final Boolean filter, final String search, final int sort, final int rows, final int page,
			final boolean hasDesc) {
		final Long countResult = testimonialRepository.countAllTestimonialCriteria(filter, search);
		final List<AdmTestimonialLine> lines = countResult == 0L ? new ArrayList<AdmTestimonialLine>() 
				: testimonialRepository.findAllTestimonialCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Transactional(readOnly = true)
	private final User readUserTestimonial(final Long userId) {
		final Optional<User> uOptional = userRepository.findById(userId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Transactional(readOnly = true)
	private final String readTradenameTestimonial(final Long companyId) {
		final Optional<String> uOptional = companyRepository.findTradenameById(companyId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdmTestimonialForm readTestimonialForm(final String id) {
		try {
			final Optional<Testimonial> uOptional = testimonialRepository.findById(UUID.fromString(id));
			if(uOptional.isPresent()) {
				final Testimonial testimonial = uOptional.get();
				final User user = readUserTestimonial(testimonial.getUserId());
				final String tradeame = user != null && user.getCompanyId() != null ? readTradenameTestimonial(user.getCompanyId()) : null;
				return new AdmTestimonialForm(testimonial, user, tradeame);
			}
		} catch (IllegalArgumentException e) {}
		return null;
	}
	
	@Transactional
	private final Testimonial postTestimonial(final Testimonial testimonial, final AdmTestimonialForm testimonialForm) {
		testimonial.setNote(testimonialForm.getNote());
		testimonial.setUsername(testimonialForm.getUsername());
		testimonial.setFunction(testimonialForm.getFunction());
		testimonial.setTradename(testimonialForm.getTradename());
		testimonial.setMessage(testimonialForm.getMessage());
		testimonial.setPostedDate(new DateTime(Date.from(Instant.now())));
		testimonial.setApprouved(testimonialForm.isApprouved());
		return testimonialRepository.save(testimonial);
	}
	
	@Override
	@Transactional
	public Testimonial addTestimonial(final AdmTestimonialForm testimonialForm, final Long adminId) {
		return postTestimonial(new Testimonial(adminId), testimonialForm);
	}
	
	@Override
	@Transactional
	public Testimonial updateTestimonial(final AdmTestimonialForm testimonialForm) {
		final Optional<Testimonial> uOptional = testimonialRepository.findById(UUID.fromString(testimonialForm.getId()));
		if(uOptional.isPresent()) {
			final Testimonial testimonial = uOptional.get();
			return postTestimonial(testimonial, testimonialForm);
		}
		return null;
	}
	
	@Override
	@Transactional
	public Testimonial deleteTestimonial(final String id) {
		try {
			final Optional<Testimonial> uOptional = testimonialRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Testimonial testimonial = uOptional.get();
			testimonialRepository.delete(testimonial);
			return testimonial;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteTestimonials(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) testimonialRepository.countTestimonials(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			testimonialRepository.deleteTestimonials(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findAssistList(final String search, final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = assistRepository.countAllAssistCriteria(search);
		final List<AdmAssistLine> lines = countResult == 0L ? new ArrayList<AdmAssistLine>() 
				: assistRepository.findAllAssistCriteria(search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Assist deleteAssist(final String id) {
		try {
			final Optional<Assist> uOptional = assistRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Assist assist = uOptional.get();
			assistRepository.delete(assist);
			return assist;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteAssists(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) assistRepository.countAssists(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			assistRepository.deleteAssists(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public ElementsList findProblemList(final Integer filter, final String search, final int sort, final int rows, final int page, 
			final boolean hasDesc) {
		final Long countResult = problemRepository.countAllProblemCriteria(filter, search);
		final List<AdmProblemLine> lines = countResult == 0L ? new ArrayList<AdmProblemLine>() 
				: problemRepository.findAllProblemCriteria(filter, search, sort, rows, page, hasDesc);
		return new ElementsList(countResult, lines);
	}
	
	@Override
	@Transactional
	public Problem deleteProblem(final String id) {
		try {
			final Optional<Problem> uOptional = problemRepository.findById(UUID.fromString(id));
			if(!uOptional.isPresent()) {
				throw new NotFoundException("message.error.notfound");
			}
			final Problem problem = uOptional.get();
			problemRepository.delete(problem);
			return problem;
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
	@Override
	@Transactional
	public void deleteProblems(final List<String> lines) {
		try {
			final List<UUID> linesUUID = ParseUtil.parseLinesUUID(lines);
			if(linesUUID.isEmpty() || linesUUID.size() != (int) problemRepository.countProblems(linesUUID)) {
				throw new NotFoundException("message.error.notfound");
			}
			problemRepository.deleteProblems(linesUUID);
		} catch (IllegalArgumentException e) {
			throw new InvalidResourceException();
		}
	}
	
}
