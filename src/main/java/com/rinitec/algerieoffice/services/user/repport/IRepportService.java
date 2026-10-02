package com.rinitec.algerieoffice.services.user.repport;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Assist;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Problem;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Testimonial;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.user.repport.AssistForm;
import com.rinitec.algerieoffice.web.form.user.repport.ProblemForm;
import com.rinitec.algerieoffice.web.form.user.repport.TestimonialForm;

public interface IRepportService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param user
	 * @return
	 */
	TestimonialForm readTestimonialForm(User user);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param testimonialForm
	 * @return
	 */
	Testimonial addTestimonial(TestimonialForm testimonialForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param assistForm
	 * @return
	 */
	Assist addAssist(AssistForm assistForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param problemForm
	 * @return
	 */
	Problem addProblem(ProblemForm problemForm);
	
}
