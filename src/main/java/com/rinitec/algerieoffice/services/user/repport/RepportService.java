package com.rinitec.algerieoffice.services.user.repport;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.enums.EnvelopeType;
import com.rinitec.algerieoffice.persistence.dao.admins.realtime.AssistRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.realtime.ProblemRepository;
import com.rinitec.algerieoffice.persistence.dao.admins.realtime.TestimonialRepository;
import com.rinitec.algerieoffice.persistence.dao.users.profiles.ProfileRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Assist;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Problem;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Testimonial;
import com.rinitec.algerieoffice.persistence.modal.medias.Envelope;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.services.medias.IEnvelopeService;
import com.rinitec.algerieoffice.web.form.user.repport.AssistForm;
import com.rinitec.algerieoffice.web.form.user.repport.ProblemForm;
import com.rinitec.algerieoffice.web.form.user.repport.TestimonialForm;

@Service
public class RepportService implements IRepportService {

	private ProfileRepository profileRepository;
	private TestimonialRepository testimonialRepository;
	private AssistRepository assistRepository;
	private ProblemRepository problemRepository;
	private IEnvelopeService envelopeService;
	
	@Autowired
	public RepportService(ProfileRepository profileRepository, TestimonialRepository testimonialRepository, 
			AssistRepository assistRepository, ProblemRepository problemRepository, IEnvelopeService envelopeService) {
		this.profileRepository = profileRepository;
		this.testimonialRepository = testimonialRepository;
		this.assistRepository = assistRepository;
		this.problemRepository = problemRepository;
		this.envelopeService = envelopeService;
	}
	
	@Transactional(readOnly = true)
	private final String readFunction(final Long userId) {
		final Optional<String> uOptional = profileRepository.findFunctionByUserId(userId);
		return uOptional.isPresent() ? uOptional.get() : null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public TestimonialForm readTestimonialForm(final User user) {
		final TestimonialForm testimonialForm = new TestimonialForm();
		testimonialForm.setId(user.getId());
		testimonialForm.setUsername(user.getDisplayName());
		testimonialForm.setFunction(readFunction(user.getId()));
		return testimonialForm;
	}
	
	@Override
	@Transactional
	public Testimonial addTestimonial(final TestimonialForm testimonialForm) {
		final Testimonial testimonial = new Testimonial();
		testimonial.setUserId(testimonialForm.getId());
		testimonial.setNote(testimonialForm.getNote());
		testimonial.setUsername(testimonialForm.getUsername());
		testimonial.setFunction(testimonialForm.getFunction());
		testimonial.setMessage(testimonialForm.getMessage());
		testimonial.setPostedDate(new DateTime(Date.from(Instant.now())));
		return testimonialRepository.save(testimonial);
	}
	
	@Override
	@Transactional
	public Assist addAssist(final AssistForm assistForm) {
		final Assist assist = new Assist();
		assist.setUserId(assistForm.getId());
		assist.setApp(assistForm.getApp());
		assist.setManagement(assistForm.getManagement());
		assist.setProgram(assistForm.getProgram());
		assist.setObject(assistForm.getObject());
		assist.setMessage(assistForm.getMessage());
		assist.setPostedDate(new DateTime(Date.from(Instant.now())));
		return assistRepository.save(assist);
	}
	
	@Override
	@Transactional
	public Problem addProblem(final ProblemForm problemForm) {
		UUID fileId = null;
		final Problem problem = new Problem();
		if(problemForm.isHasFile()) {
			final Envelope envelope = envelopeService.addEnvelope(problemForm.getFile(), EnvelopeType.problem);
			fileId = envelope.getId();
		}
		problem.setUserId(problemForm.getId());
		problem.setType(problemForm.getType());
		problem.setMessage(problemForm.getMessage());
		problem.setFileUUID(fileId);
		problem.setPostedDate(new DateTime(Date.from(Instant.now())));
		return problemRepository.save(problem);
	}
	
}
