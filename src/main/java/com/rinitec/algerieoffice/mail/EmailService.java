package com.rinitec.algerieoffice.mail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.web.form.admins.ads.AdmMailingForm;
import com.rinitec.algerieoffice.web.form.company.newsletter.NewsletterForm;
import com.rinitec.algerieoffice.web.listener.events.OnAlertEvent;
import com.rinitec.algerieoffice.web.listener.events.OnEmailEvent;
import com.rinitec.algerieoffice.web.listener.events.OnRegisterEvent;
import com.rinitec.algerieoffice.web.modal.admins.ads.AnnonceNewsletterMini;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterSocial;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogNewsletterMini;

@Service
public class EmailService implements IEmailService {
	private static final String[] IMAGES_PART = {"logo", "facebook", "twitter", "google", "linkedin"};

    private JavaMailSender jMailSender;
	private IEmailTemplate emailTemplate;
	
	@Autowired
	public EmailService(JavaMailSender jMailSender, IEmailTemplate emailTemplate) {
		this.jMailSender = jMailSender;
		this.emailTemplate = emailTemplate;
	}
	
	private final void sendEmailPLAIN(final EmailModel emailModel) {
		final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(emailModel.getFrom());
		simpleMailMessage.setTo(emailModel.getTo());
		simpleMailMessage.setSubject(emailModel.getSubject());
		simpleMailMessage.setText(emailModel.getMessage());
		simpleMailMessage.setSentDate(new Date());
		jMailSender.send(simpleMailMessage);
	}
	
	private final void sendEmailHTML(final EmailModel emailModel) throws MessagingException, IOException {
		final MimeMessage templateMailMessage = jMailSender.createMimeMessage();
		final MimeMultipart content = new MimeMultipart("related");
		final MimeBodyPart textPart = new MimeBodyPart();
		final MimeBodyPart imagePart = new MimeBodyPart();
		textPart.setText(emailModel.getMessage(), StandardCharsets.UTF_8.name(), "html");
		imagePart.attachFile(new ClassPathResource("imagepart/logo-min.png").getFile());
		imagePart.setContentID("<logo>");
		imagePart.setDisposition(MimeBodyPart.INLINE);
		content.addBodyPart(textPart);
		content.addBodyPart(imagePart);
		templateMailMessage.setFrom(new InternetAddress(emailModel.getFrom()));
		templateMailMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(emailModel.getTo()));
		templateMailMessage.setSubject(emailModel.getSubject());
		templateMailMessage.setContent(content);
		templateMailMessage.setSentDate(new Date());
		jMailSender.send(templateMailMessage);
	}
	
	@Override
	public void sendUnsubscribeMail(final OnRegisterEvent event) {
		try {
			sendEmailHTML(emailTemplate.generateUnsubscribeMail(event, true));
		} catch (MessagingException | IOException e) {
			sendEmailPLAIN(emailTemplate.generateUnsubscribeMail(event, false));
		}
	}
	
	@Override
	public void sendSubscribeMail(final OnEmailEvent event) {
		try {
			sendEmailHTML(emailTemplate.generateSubscribeMail(event));
		} catch (MessagingException | IOException e) {e.printStackTrace();}
	}
	
	@Override
	public void sendAlertMail(final OnAlertEvent event) {
		try {
			sendEmailHTML(emailTemplate.generateAlertMail(event));
		} catch (MessagingException | IOException e) {e.printStackTrace();}
	}
	
	private final void sendEmailTemplate(final EmailModel emailModel, final List<String> users) throws MessagingException, IOException {
		final MimeMessage templateMailMessage = jMailSender.createMimeMessage();
		final MimeMultipart content = new MimeMultipart("related");
		final MimeBodyPart textPart = new MimeBodyPart();
		textPart.setText(emailModel.getMessage(), StandardCharsets.UTF_8.name(), "html");
		content.addBodyPart(textPart);
		for (final String image : IMAGES_PART) {
			final MimeBodyPart imagePart = new MimeBodyPart();
			imagePart.attachFile(new ClassPathResource("imagepart/" + image + "-min.png").getFile());
			imagePart.setContentID("<" + image + ">");
			imagePart.setDisposition(MimeBodyPart.INLINE);
			content.addBodyPart(imagePart);
		}
		templateMailMessage.setFrom(new InternetAddress(emailModel.getFrom()));
		templateMailMessage.setRecipients(MimeMessage.RecipientType.TO, String.join(",", users));
		templateMailMessage.setSubject(emailModel.getSubject());
		templateMailMessage.setContent(content);
		templateMailMessage.setSentDate(new Date());
		jMailSender.send(templateMailMessage);
	}
	
	@Override
	public void sendNewsletterBlog(final HttpServletRequest request, final List<String> users, final List<BlogNewsletterMini> lines) {
		try {
			sendEmailTemplate(emailTemplate.generateNewsletterBlog(request, lines), users);
		} catch (MessagingException | IOException e) {e.printStackTrace();}
	}
	
	@Override
	public void sendNewsletterAnnonces(final HttpServletRequest request, final List<String> users, final List<AnnonceNewsletterMini> lines, 
			final int todays, final int offers) {
		try {
			sendEmailTemplate(emailTemplate.generateNewsletterAnnonce(request, lines, todays, offers), users);
		} catch (MessagingException | IOException e) {e.printStackTrace();}
	}
	
	@Override
	public void sendNewsletterMailing(final HttpServletRequest request, final AdmMailingForm mailingForm) {
		try {
			sendEmailTemplate(emailTemplate.generateNewsletterMailing(request, mailingForm), mailingForm.getUsers());
		} catch (MessagingException | IOException e) {e.printStackTrace();}
	}
	
	private final void sendEmailCompany(final EmailModel emailModel, final NewsletterForm newsletterForm) throws MessagingException, IOException {
		final MimeMessage templateMailMessage = jMailSender.createMimeMessage();
		final MimeMultipart content = new MimeMultipart("related");
		final MimeBodyPart textPart = new MimeBodyPart();
		textPart.setText(emailModel.getMessage(), StandardCharsets.UTF_8.name(), "html");
		content.addBodyPart(textPart);
		if(newsletterForm.getLogo() != null) {
			final MimeBodyPart logoPart = new MimeBodyPart();
			logoPart.attachFile(newsletterForm.getLogo());
			logoPart.setContentID("<logo>");
			logoPart.setDisposition(MimeBodyPart.INLINE);
			content.addBodyPart(logoPart);
		}
		if(newsletterForm.getCover() != null) {
			final MimeBodyPart coverPart = new MimeBodyPart();
			coverPart.attachFile(newsletterForm.getCover());
			coverPart.setContentID("<cover>");
			coverPart.setDisposition(MimeBodyPart.INLINE);
			content.addBodyPart(coverPart);
		}
		if(newsletterForm.getSocial() != null && newsletterForm.getSocial().hasPresent()) {
			final NewsletterSocial social = newsletterForm.getSocial();
			final String[] images = {"facebook", "twitter", "google", "linkedin"};
			for (final String image : images) {
				if(!StringUtils.isEmpty(social.buildSocial(image))) {
					final MimeBodyPart socialPart = new MimeBodyPart();
					socialPart.attachFile(new ClassPathResource("imagepart/" + image + "-min.png").getFile());
					socialPart.setContentID("<" + image + ">");
					socialPart.setDisposition(MimeBodyPart.INLINE);
					content.addBodyPart(socialPart);
				}
			}
		}
		templateMailMessage.setFrom(new InternetAddress(emailModel.getFrom()));
		templateMailMessage.setRecipients(MimeMessage.RecipientType.TO, String.join(",", newsletterForm.getEmails()));
		templateMailMessage.setSubject(emailModel.getSubject());
		templateMailMessage.setContent(content);
		templateMailMessage.setSentDate(new Date());
		if(!StringUtils.isEmpty(newsletterForm.getSender())) {
			templateMailMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(newsletterForm.getSender()));
		}
		jMailSender.send(templateMailMessage);
	}
	
	@Override
	public boolean sendNewsletterCompany(final HttpServletRequest request, final NewsletterForm newsletterForm) {
		try {
			sendEmailCompany(emailTemplate.generateNewsletterCompany(request, newsletterForm), newsletterForm);
			return true;
		} catch (MessagingException | IOException e) {return false;}
	}
	
}
