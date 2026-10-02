package com.rinitec.algerieoffice.mail;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.admins.ads.AdmMailingForm;
import com.rinitec.algerieoffice.web.form.company.newsletter.NewsletterForm;
import com.rinitec.algerieoffice.web.listener.events.OnAlertEvent;
import com.rinitec.algerieoffice.web.listener.events.OnEmailEvent;
import com.rinitec.algerieoffice.web.listener.events.OnRegisterEvent;
import com.rinitec.algerieoffice.web.modal.admins.ads.AnnonceNewsletterMini;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterItem;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterSocial;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogNewsletterMini;

@Service
public class EmailTemplate implements IEmailTemplate {
	private static final String URL_SUBSCIBE1 = "/company/manage/preferences";
	private static final String URL_SUBSCIBE2 = "/user/settings/notifications";
	private static final String URL_CONTACTS = "/company/communication/contacts";
	private static final String URL_QUOTES = "/company/prospect/quotes";
	private static final String URL_ADS = "/company/prospect/ads";
	private static final String URL_INFOS = "/company/prospect/infos";
	private static final String URL_JOBS = "/company/prospect/jobs";
	private static final String URL_MESSAGES = "/user/feedback/messages";
	
	private static final String EMAIL_SENDGRID = "noreply@algerieoffice.net";

	private MessageSource messages;
	
	@Autowired
	public EmailTemplate(MessageSource messages) {
		this.messages = messages;
	}
	
	private final String parseUnsubscribeTemplate(final String appURL, final Locale locale, final String content) {
		final String dir = locale.getLanguage().equals("ar") ? "rtl" : "ltr";
		final String left = locale.getLanguage().equals("ar") ? "right" : "left";
		final String politicURL = appURL + ConstraintesURL.URL_POLITIC;
		final String cguURL = appURL + ConstraintesURL.URL_CGU;
		final String template = ""
				+ "<html dir=\"" + dir + "\">" 
				+ "<head><link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/></head>" 
				+ "<body style=\"font-family:'Open Sans',sans-serif;background:#f0f0f0;text-align:center;padding:10px;\">"
					+ "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse:collapse;border-color:#f0f0f0;background:#f0f0f0;\">"
							+ "<tr><td align=\"" + left + "\" style=\"padding:20px 0;\"><a href=\"" + appURL 
								+ "\"><img height=\"34\" src=\"cid:logo\"/></a></td></tr><tr><td align=\"" + left + "\" style=\"text-align:" + left + ";\">"
								+ "<div style=\"font-size:15px;padding:40px;color:#353c45;background:#ffffff;border:1px solid #c3c7ca;\">" + content 
								+ "<p style=\"margin-top:20px;\">" + messages.getMessage("txt.mail.footer1.1", null, locale) + "</p></div></td></tr>" 
							+ "<tr><td align=\"" + left + "\" style=\"text-align:" + left + ";\">"
								+ "<p style=\"margin-top:20px;font-size:12px;line-height:12px;color:#545e74;\">" + messages.getMessage("txt.mail.footer1.2", null, locale) + "<br><br>" 
								+ "&copy;" + String.valueOf(ParseUtil.getCurrYear()) + " " + messages.getMessage("app.copyright", null, locale) 
								+ " <a href=\"" + politicURL + "\">" + messages.getMessage("explorer.mainmenu7.3", null, locale) 
								+ "</a> -- <a href=\"" + cguURL + "\">" + messages.getMessage("explorer.mainmenu7.2", null, locale) + "</a></p></td></tr>"
					+ "</table></body></html>";
		return template;
	}
	
	private final String parseMessageConfirmation(final OnRegisterEvent event, final String confirmURL, final boolean html) {
		final Locale locale = event.getLocale();
		if(html) {
			final String content = "<p>" + messages.getMessage("tool.dashboard.welcome1", null, locale) + " " + event.getUser().getFirstName() + ",</p>"
					+ "<p style=\"margin-top:20px;\">" + messages.getMessage("txt.mail.body1.1.1", null, locale) + " " + messages.getMessage("txt.mail.body1.1.2", null, locale) 
					+ "</p><p style=\"margin-top:20px;\"><a href=\"" + confirmURL + "\" style=\"color:#1757b8\">" + confirmURL + "</a></p>";
			return parseUnsubscribeTemplate(event.getAppurl(), locale, content);
		}
		return messages.getMessage("tool.dashboard.welcome1", null, locale) + " " + event.getUser().getFirstName() + System.lineSeparator()
			+ messages.getMessage("txt.mail.body1.1.1", null, locale) + " " + messages.getMessage("txt.mail.body1.1.2", null, locale) + System.lineSeparator() 
			+ System.lineSeparator() + confirmURL + System.lineSeparator() + System.lineSeparator() + messages.getMessage("txt.mail.footer1.1", null, locale) + System.lineSeparator();
	}
	
	private final EmailModel generateConfirmRegister(final OnRegisterEvent event, final boolean html) {
		final User user = event.getUser();
		final String confirmURL = event.getAppurl() + ConstraintesURL.URL_CONFIRM_MAIL + event.getToken();
		final String subject = messages.getMessage("txt.mail.subject1.1", null, event.getLocale());
		final String message = parseMessageConfirmation(event, confirmURL, html);
		return new EmailModel(EMAIL_SENDGRID, user.getEmail(), subject, message);
	}
	
	private final String parseMessagePassword(final OnRegisterEvent event, final boolean html) {
		final Locale locale = event.getLocale();
		if(html) {
			final String content = "<p>" + messages.getMessage("tool.dashboard.welcome1", null, locale) + " " + event.getUser().getFirstName() + ",</p>" 
					+ "<p style=\"margin-top:20px;\">" + messages.getMessage("txt.mail.body1.2.1", null, locale) + "</p><p style=\"margin-top:20px;\">" 
					+ messages.getMessage("txt.mail.body1.2.2", null, locale) + "</p><p style=\"margin-top:20px;\"><span style=\"font-weight:600\">" 
					+ messages.getMessage("lbl.login.password", null, locale) + " : </span> " + event.getUser().getRandomPassword() + "</p>";
			return parseUnsubscribeTemplate(event.getAppurl(), locale, content);
		}
		return messages.getMessage("tool.dashboard.welcome1", null, locale) + " " + event.getUser().getFirstName() + System.lineSeparator()
			+ messages.getMessage("txt.mail.body1.2.1", null, locale) + System.lineSeparator() + System.lineSeparator() 
			+ messages.getMessage("txt.mail.body1.2.2", null, locale) + System.lineSeparator() + System.lineSeparator()
			+ messages.getMessage("lbl.login.password", null, locale) + " : " + event.getUser().getRandomPassword() + System.lineSeparator() + System.lineSeparator()
			+ messages.getMessage("txt.mail.footer1.1", null, locale) + System.lineSeparator();
	}
	
	private final EmailModel generateRegisterPassword(final OnRegisterEvent event, final boolean html) {
		final User user = event.getUser();
		final String subject = messages.getMessage("txt.mail.subject1.2", null, event.getLocale());
		final String message = parseMessagePassword(event, html);
		return new EmailModel(EMAIL_SENDGRID, user.getEmail(), subject, message);
	}
	
	private final String parseMessagePassword(final OnRegisterEvent event, final String confirmURL, final boolean html) {
		final Locale locale = event.getLocale();
		if(html) {
			final String content = "<p>" + messages.getMessage("tool.dashboard.welcome1", null, locale) + " " + event.getUser().getFirstName() + ",</p>" 
					+ "<p style=\"margin-top:20px;\">" + messages.getMessage("txt.mail.body1.3.1", null, locale) + " " + messages.getMessage("txt.mail.body1.1.2", null, locale) 
					+ "</p><p style=\"margin-top:20px;\"><a href=\"" + confirmURL + "\" style=\"color:#1757b8\">" + confirmURL + "</a></p>" 
					+ "<p style=\"margin-top:20px;\">" + messages.getMessage("txt.mail.body1.3.2", null, locale) + "</p>";
			return parseUnsubscribeTemplate(event.getAppurl(), locale, content);
		}
		return messages.getMessage("tool.dashboard.welcome1", null, locale) + " " + event.getUser().getFirstName() + System.lineSeparator()
			+ messages.getMessage("txt.mail.body1.3.1", null, locale) + " " + messages.getMessage("txt.mail.body1.1.2", null, locale) + System.lineSeparator() 
			+ System.lineSeparator() + confirmURL + System.lineSeparator() + System.lineSeparator() + messages.getMessage("txt.mail.body1.3.2", null, locale) 
			+ System.lineSeparator() + System.lineSeparator() + messages.getMessage("txt.mail.footer1.1", null, locale) + System.lineSeparator();
	}
	
	private final EmailModel generateChangePassword(final OnRegisterEvent event, final boolean html) {
		final User user = event.getUser();
		final String confirmURL = event.getAppurl() + ConstraintesURL.URL_CHANGE_PASSWORD + user.getId() + "&token=" + event.getToken();
		final String subject = messages.getMessage("txt.mail.subject1.3", null, event.getLocale());
		final String message = parseMessagePassword(event, confirmURL, html);
		return new EmailModel(EMAIL_SENDGRID, user.getEmail(), subject, message);
	}
	
	private final String parseMessageValidate(final OnRegisterEvent event, final String confirmURL, final boolean html, final boolean login) {
		final Locale locale = event.getLocale();
		if(html) {
			final String content = "<p>" + messages.getMessage("tool.dashboard.welcome1", null, locale) + " " + event.getUser().getFirstName() + ",</p>" 
					+ "<p style=\"margin-top:20px;\">" + messages.getMessage(login ? "txt.mail.body1.4.1" : "txt.mail.body1.4.2", null, locale) + " " 
					+ messages.getMessage("txt.mail.body1.4.3", null, locale) + "</p><p style=\"margin-top:20px;\"><a href=\"" 
					+ confirmURL + "\" style=\"color:#1757b8\">" + confirmURL + "</a></p>";
			return parseUnsubscribeTemplate(event.getAppurl(), locale, content);
		}
		return messages.getMessage("tool.dashboard.welcome1", null, locale) + " " + event.getUser().getFirstName() + System.lineSeparator() 
			+ messages.getMessage(login ? "txt.mail.body1.4.1" : "txt.mail.body1.4.2", null, locale) + System.lineSeparator() + System.lineSeparator() 
			+ confirmURL + System.lineSeparator() + System.lineSeparator() + messages.getMessage("txt.mail.footer1.1", null, locale) + System.lineSeparator();
	}
	
	private final EmailModel generateValidateMail(final OnRegisterEvent event, final boolean html, final boolean login) {
		final User user = event.getUser();
		final String confirmURL = event.getAppurl() + ConstraintesURL.URL_CONFIRM_MAIL + event.getToken();
		final String subject = messages.getMessage("txt.mail.subject1.4", null, event.getLocale());
		final String message = parseMessageValidate(event, confirmURL, html, login);
		return new EmailModel(EMAIL_SENDGRID, user.getEmail(), subject, message);
	}
	
	private final String parseMessageAdduser(final OnRegisterEvent event, final String confirmURL, final boolean html) {
		final Locale locale = event.getLocale();
		if(html) {
			final String content = "<p>" + messages.getMessage("tool.dashboard.welcome1", null, locale) + " " + event.getUser().getFirstName() + ",</p>" 
					+ "<p style=\"margin-top:20px;\">" + messages.getMessage("txt.mail.body1.4.4", null, locale) + " " + messages.getMessage("txt.mail.body1.1.2", null, locale) 
					+ "</p><p style=\"margin-top:20px;\"><a href=\"" + confirmURL + "\" style=\"color:#1757b8\">" + confirmURL + "</a></p>" 
					+ (!StringUtils.isEmpty(event.getUser().getRandomPassword()) ? "<p style=\"margin-top:20px;\">" + messages.getMessage("txt.mail.body1.2.2", null, locale) 
						+ "</p><p style=\"margin-top:20px;\"><span style=\"font-weight:600\">" + messages.getMessage("lbl.login.password", null, locale) + " : </span> " 
							+ event.getUser().getRandomPassword() + "</p>" : "");
			return parseUnsubscribeTemplate(event.getAppurl(), locale, content);
		}
		return messages.getMessage("tool.dashboard.welcome1", null, locale) + " " + event.getUser().getFirstName() + System.lineSeparator() 
			+ messages.getMessage("txt.mail.body1.4.4", null, locale) + " " + messages.getMessage("txt.mail.body1.1.2", null, locale) + System.lineSeparator() 
			+ System.lineSeparator() + confirmURL + System.lineSeparator() + System.lineSeparator() + (!StringUtils.isEmpty(event.getUser().getRandomPassword()) 
					? messages.getMessage("txt.mail.body1.2.2", null, locale) + System.lineSeparator() + System.lineSeparator()
					+ messages.getMessage("lbl.login.password", null, locale) + " : " + event.getUser().getRandomPassword() : "") 
			+ messages.getMessage("txt.mail.footer1.1", null, locale) + System.lineSeparator();
	}
	
	private final EmailModel generateAdduserMail(final OnRegisterEvent event, final boolean html) {
		final User user = event.getUser();
		final String confirmURL = event.getAppurl() + ConstraintesURL.URL_CONFIRM_MAIL + event.getToken();
		final String subject = messages.getMessage("txt.mail.subject1.4", null, event.getLocale());
		final String message = parseMessageAdduser(event, confirmURL, html);
		return new EmailModel(EMAIL_SENDGRID, user.getEmail(), subject, message);
	}
	
	private final String parseMessageDeactivate(final OnRegisterEvent event, final boolean html) {
		final Locale locale = event.getLocale();
		if(html) {
			final String content = "<p>" + messages.getMessage("tool.dashboard.welcome1", null, locale) + " " + event.getUser().getFirstName() + ",</p>" 
					+ "<p style=\"margin-top:20px;\">" + messages.getMessage("txt.mail.body1.5.1", null, locale) + "</p><p style=\"margin-top:20px;\"><span style=\"font-weight:600\">" 
					+ messages.getMessage("tabs.reason", null, locale) + " : </span> " + messages.getMessage("txt.mail.body1.5.2", null, locale) + "</p>";
			return parseUnsubscribeTemplate(event.getAppurl(), locale, content);
		}
		return messages.getMessage("tool.dashboard.welcome1", null, locale) + " " + event.getUser().getFirstName() + System.lineSeparator() 
			+ messages.getMessage("txt.mail.body1.5.1", null, locale) + System.lineSeparator() + System.lineSeparator() 
			+ messages.getMessage("tabs.reason", null, locale) + " : " + messages.getMessage("txt.mail.body1.5.2", null, locale) + System.lineSeparator() 
			+ System.lineSeparator() + messages.getMessage("txt.mail.footer1.1", null, locale) + System.lineSeparator();
	}
	
	private final EmailModel generateDeactivate(final OnRegisterEvent event, final boolean html) {
		final User user = event.getUser();
		final String subject = messages.getMessage("txt.mail.subject1.5", null, event.getLocale());
		final String message = parseMessageDeactivate(event, html);
		return new EmailModel(EMAIL_SENDGRID, user.getEmail(), subject, message);
	}
	
	@Override
	public EmailModel generateUnsubscribeMail(final OnRegisterEvent event, final boolean html) {
		switch(event.getType()) {
		case confirmRegister: return generateConfirmRegister(event, html);
		case registerPassword: return generateRegisterPassword(event, html);
		case changePassword: return generateChangePassword(event, html);
		case updateLogin: return generateValidateMail(event, html, true);
		case editUser: return generateValidateMail(event, html, false);
		case addUser: return generateAdduserMail(event, html);
		case deactivateUser: return generateDeactivate(event, html);
		default: return null;
		}
	}
	
	private final String parseSubjectNotification(final EmailType type) {
		switch(type) {
		case contacts: return "txt.mail.subject2.1";
		case quotes: return "txt.mail.subject2.2";
		case ads: return "txt.mail.subject2.3";
		case infos: return "txt.mail.subject2.4";
		case jobs: return "txt.mail.subject2.5";
		default: return "txt.mail.subject2.6";
		}
	}
	
	private final String parseMessageNotification1(final EmailType type) {
		switch(type) {
		case contacts: return "txt.mail.body2.1.1";
		case quotes: return "txt.mail.body2.1.2";
		case ads: return "txt.mail.body2.1.3";
		case infos: return "txt.mail.body2.1.4";
		case jobs: return "txt.mail.body2.1.5";
		default: return "txt.mail.body2.1.6";
		}
	}
	
	private final String parseMessageNotification2(final EmailType type) {
		switch(type) {
		case messages: return "txt.mail.body2.2.2";
		default: return "txt.mail.body2.2.1";
		}
	}
	
	private final String parseNotificationURL(final EmailType type) {
		switch(type) {
		case contacts: return URL_CONTACTS;
		case quotes: return URL_QUOTES;
		case ads: return URL_ADS;
		case infos: return URL_INFOS;
		case jobs: return URL_JOBS;
		default: return URL_MESSAGES;
		}
	}
	
	private final String parseMessageNotificationURL(final EmailType type) {
		switch(type) {
		case messages: return "txt.mail.body2.3.2";
		default: return "txt.mail.body2.3.1";
		}
	}
	
	private final String parseSubscribeURL(final EmailType type) {
		switch(type) {
		case messages: return URL_SUBSCIBE2;
		default: return URL_SUBSCIBE1;
		}
	}
	
	private final String parseSubscribeTemplate(final OnEmailEvent event) {
		final Locale locale = event.getLocale();
		final String dir = event.getLocale().getLanguage().equals("ar") ? "rtl" : "ltr";
		final String left = event.getLocale().getLanguage().equals("ar") ? "right" : "left";
		final String appURL = event.getAppurl();
		final String notificationURL = event.getAppurl() + parseNotificationURL(event.getType());
		final String subscribeURL = event.getAppurl() + parseSubscribeURL(event.getType());
		final String politicURL = event.getAppurl() + ConstraintesURL.URL_POLITIC;
		final String cguURL = event.getAppurl() + ConstraintesURL.URL_CGU;
		final String template = "" 
				+ "<html dir=\"" + dir + "\">" 
				+ "<head><link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/></head>" 
				+ "<body style=\"font-family:'Open Sans',sans-serif;background:#f0f0f0;text-align:center;padding:10px;\">" 
					+ "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse:collapse;border-color:#f0f0f0;background:#f0f0f0;\">" 
					+ "<tr><td align=\"" + left + "\" style=\"padding:20px 0;\"><a href=\"" + appURL + "\"><img height=\"34\" src=\"cid:logo\"/></a></td></tr><tr><td align=\"" 
						+ left + "\" style=\"text-align:" + left + ";\">"
						+ "<div style=\"font-size:15px;padding:40px;color:#353c45;background:#ffffff;border:1px solid #c3c7ca;\">" 
							+ "<p>" + messages.getMessage("tool.dashboard.welcome1", null, locale) + ",</p>" 
							+ "<p style=\"margin-top:20px;\">" + messages.getMessage(parseMessageNotification1(event.getType()), null, locale) + " " 
							+ messages.getMessage(parseMessageNotification2(event.getType()), null, locale) + "</p><p style=\"margin-top:20px;\"><a href=\"" + notificationURL 
							+ "\" style=\"color:#1757b8\">" + messages.getMessage(parseMessageNotificationURL(event.getType()), null, locale) 
							+ "</a></p><p style=\"margin-top:20px;\">" + messages.getMessage("txt.mail.footer1.1", null, locale) + "</p></div></td></tr>"
					+ "<tr><td align=\"" + left + "\" style=\"text-align:" + left + ";\">"
						+ "<p style=\"margin-top:20px;font-size:12px;line-height:12px;color:#545e74;\">" + messages.getMessage("txt.mail.footer2.1", null, locale) + " <a href=\"" 
						+ subscribeURL + "\">" + messages.getMessage("txt.mail.footer2.2", null, locale) + "<a><br><br>" 
						+ "&copy;" + String.valueOf(ParseUtil.getCurrYear()) + " " + messages.getMessage("app.copyright", null, locale) 
						+ " <a href=\"" + politicURL + "\">" + messages.getMessage("explorer.mainmenu7.3", null, locale) + "</a> -- <a href=\"" + cguURL + "\">" 
						+ messages.getMessage("explorer.mainmenu7.2", null, locale) + "</a></p></td></tr>"
				+ "</table></body></html>";
		return template;
	}

	@Override
	public EmailModel generateSubscribeMail(final OnEmailEvent event) {
		final String subject = messages.getMessage(parseSubjectNotification(event.getType()), null, event.getLocale());
		final String message = parseSubscribeTemplate(event);
		return new EmailModel(EMAIL_SENDGRID, event.getEmail(), subject, message);
	}
	
	private final String parseAlertTemplate(final OnAlertEvent event) {
		final Locale locale = event.getLocale();
		final String dir = event.getLocale().getLanguage().equals("ar") ? "rtl" : "ltr";
		final String left = event.getLocale().getLanguage().equals("ar") ? "right" : "left";
		final String appURL = event.getAppurl();
		final String alertURL = event.getAppurl() + event.getAlert().getResultURL();
		final String subscribURL = event.getAppurl() + URL_SUBSCIBE2;
		final String politicURL = event.getAppurl() + ConstraintesURL.URL_POLITIC;
		final String cguURL = event.getAppurl() + ConstraintesURL.URL_CGU;
		final String template = "" 
				+ "<html dir=\"" + dir + "\">" 
				+ "<head><link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/></head>" 
				+ "<body style=\"font-family:'Open Sans',sans-serif;background:#f0f0f0;text-align:center;padding:10px;\">" 
					+ "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse:collapse;border-color:#f0f0f0;background:#f0f0f0;\">"
					+ "<tr><td align=\"" + left + "\" style=\"padding:20px 0;\"><a href=\"" + appURL + "\"><img height=\"34\" src=\"cid:logo\"/></a></td></tr>" 
					+ "<tr><td align=\"" + left + "\" style=\"text-align:" + left + ";\">"
						+ "<div style=\"font-size:15px;padding:40px;color:#353c45;background:#ffffff;border:1px solid #c3c7ca;\">"
							+ "<p>" + messages.getMessage("tool.dashboard.welcome1", null, locale) + " " + event.getAlert().getFirstname() + ",</p>"
							+ "<p style=\"margin-top:20px;\">" + messages.getMessage("txt.mail.subject3.1", null, event.getLocale()) + " \"<span style=\"font-weight:600\">" 
							+ event.getAlert().getName() + "</span>\" " + messages.getMessage("txt.mail.body3.2.1", null, event.getLocale()) + " <span style=\"font-weight:600\">" 
							+ event.getAlert().getCount() + " " + messages.getMessage("txt.mail.body3.1.".concat(String.valueOf(event.getType())), null, event.getLocale()) 
							+ "</span> " + messages.getMessage("txt.mail.body3.2.2", null, event.getLocale()) + " " + messages.getMessage("txt.mail.body2.2.1", null, event.getLocale()) 
							+ "</p><p style=\"margin-top:20px;\"><a href=\"" + alertURL + "\" style=\"color:#1757b8\">" 
							+ messages.getMessage("txt.mail.body3.2.3", null, event.getLocale()) + "</a></p><p style=\"margin-top:20px;\">" 
							+ messages.getMessage("txt.mail.footer1.1", null, locale) + "</p></div></td></tr>" 
					+ "<tr><td align=\"" + left + "\" style=\"text-align:" + left + ";\">"
						+ "<p style=\"margin-top:20px;font-size:12px;line-height:12px;color:#545e74;\">" + messages.getMessage("txt.mail.footer2.1", null, locale) + " <a href=\"" 
						+ subscribURL + "\">" + messages.getMessage("txt.mail.footer2.2", null, locale) + "<a><br><br>" 
						+ "&copy;" + String.valueOf(ParseUtil.getCurrYear()) + " " + messages.getMessage("app.copyright", null, locale) 
						+ " <a href=\"" + politicURL + "\">" + messages.getMessage("explorer.mainmenu7.3", null, locale) + "</a> -- <a href=\"" + cguURL + "\">" 
						+ messages.getMessage("explorer.mainmenu7.2", null, locale) + "</a></p></td></tr>"
				+ "</table></body></html>";
		return template;
	}
	
	@Override
	public EmailModel generateAlertMail(final OnAlertEvent event) {
		final String subject = messages.getMessage("txt.mail.subject3.1", null, event.getLocale()) + " : " + event.getAlert().getName();
		final String message = parseAlertTemplate(event);
		return new EmailModel(EMAIL_SENDGRID, event.getAlert().getEmail(), subject, message);
	}
	
	private final String parseNewsletterBlog(final Locale locale, final List<BlogNewsletterMini> lines) {
		String template = "";
		for (final BlogNewsletterMini line : lines) {
			final String item = "<div style=\"padding:30px 40px;color:#353c45;background:#ffffff;border:1px solid #c3c7ca;border-top:none;\">"
						+ "<a href=\"" + line.getIdentifyURL() + "\" style=\"font-size:18px;font-weight:600;color:#1757b8;\">" + line.getTitle() 
						+ "</a><p style=\"margin-top:5px;font-size:13px;color:#3dac4e;\">" + messages.getMessage("chose.blog.family".concat(line.getCategory()), null, locale) 
						+ "</p><p style=\"margin-top:20px;font-size:15px;\">" + line.getDescription() + "</p><p style=\"margin-top:15px;\"><a href=\"" + line.getIdentifyURL() 
						+ "\" style=\"display:inline-block;font-size:12px;font-weight:600;padding:12px 42px;color:#ffffff;background:#3dac4e;text-decoration:none;border-radius:3px;\">" 
						+ messages.getMessage("btn.explorer.blog", null, locale) + "</a></p></div>";
			template += item;
		}
		return template;
	}
	
	private final String parseSocialTemplate(final String appurl, final Locale locale) {
		final String left = locale.getLanguage().equals("ar") ? "right" : "left";
		final String subscribeURL = appurl + URL_SUBSCIBE2;
		final String template = "" 
				+ "<tr><td align=\"center\"><div style=\"padding:20px;text-align:center;border-bottom:1px solid #c3c7ca;\"><h3>" + messages.getMessage("tool.follow", null, locale) 
					+ "</h3><ul style=\"list-style:none;padding-left:0;margin-top:10px;\"><li style=\"display:inline-block;padding:5px;\"><a href=\"" 
					+ ConstraintesURL.URL_FACEBOOK + "\"><img height=\"32\" src=\"cid:facebook\"/></a></li><li style=\"display:inline-block;padding:5px;\"><a href=\"" 
					+ ConstraintesURL.URL_TWITTER + "\"><img height=\"32\" src=\"cid:twitter\"/></a></li><li style=\"display:inline-block;padding:5px;\"><a href=\"" 
					+ ConstraintesURL.URL_GOOGLE + "\"><img height=\"32\" src=\"cid:google\"/></a></li><li style=\"display:inline-block;padding:5px;\"><a href=\"" 
					+ ConstraintesURL.URL_LINKEDIN + "\"><img height=\"32\" src=\"cid:linkedin\"/></a></li></ul></div></td></tr>" 
				+ "<tr><td align=\"" + left + "\" style=\"text-align:" + left + ";\">"
					+ "<p style=\"margin-top:20px;font-size:12px;line-height:12px;color:#545e74;\">" + messages.getMessage("txt.mail.footer2.1", null, locale) + " <a href=\"" 
					+ subscribeURL + "\">" + messages.getMessage("txt.mail.footer2.2", null, locale) + "</a><br><br>" + "&copy;" 
					+ String.valueOf(ParseUtil.getCurrYear()) + " " + messages.getMessage("app.copyright", null, locale) + "</p></td></tr>";
		return template;
	}
	
	private final String parseNewsletterBlogTemplate(final HttpServletRequest request, final List<BlogNewsletterMini> lines) {
		final String appurl = RequestUtil.getAppurl(request);
		final Locale locale = RequestContextUtils.getLocale(request);
		final String dir = locale.getLanguage().equals("ar") ? "rtl" : "ltr";
		final String left = locale.getLanguage().equals("ar") ? "right" : "left";
		final String blogURL = ConstraintesURL.getMapsiteBlogURL();
		final String template = "" 
				+ "<html dir=\"" + dir + "\">"
				+ "<head><link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/></head>" 
				+ "<body style=\"font-family:'Open Sans',sans-serif;background:#f0f0f0;text-align:center;padding:10px;\">" 
					+ "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse:collapse;border-color:#f0f0f0;background:#f0f0f0;\">"
					+ "<tr><td align=\"" + left + "\" style=\"padding:20px 0;\"><a href=\"" + appurl + "\"><img height=\"34\" src=\"cid:logo\"/></a></td></tr>" 
					+ "<tr><td align=\"" + left + "\" style=\"text-align:" + left + ";\">"
						+ "<div style=\"font-size:20px;font-weight:600;padding:24px;color:#ffffff;background:#2c3f50;text-align:center;\">" 
							+ messages.getMessage("txt.admin.mailing1", null, locale) + "</div>" + parseNewsletterBlog(locale, lines) 
						+ "<div style=\"padding:20px;color:#ffffff;background:#2c3f50;text-align:center;\"><a href=\"" + blogURL  
							+ "\" style=\"display:inline-block;font-size:13px;font-weight:600;padding:14px 34px;color:#3dac4e;background:#ffffff;text-decoration:none;border-radius:3px;\">" 
							+ messages.getMessage("btn.explorer.blogs", null, locale) + "</a></div></td></tr>" + parseSocialTemplate(appurl, locale) 
					+ "</table></body></html>";
		return template;
	}
	
	@Override
	public EmailModel generateNewsletterBlog(final HttpServletRequest request, final List<BlogNewsletterMini> lines) {
		final String subject = messages.getMessage("txt.admin.mailing1", null, RequestContextUtils.getLocale(request));
		final String message = parseNewsletterBlogTemplate(request, lines);
		return new EmailModel(EMAIL_SENDGRID, null, subject, message);
	}
	
	private final String parseNewsletterAnnonce(final Locale locale, final List<AnnonceNewsletterMini> lines) {
		String template = "";
		for (final AnnonceNewsletterMini line : lines) {
			final String item = "<div style=\"padding:14px 24px;color:#353c45;background:#ffffff;border:1px solid #c3c7ca;border-top:none;\">" 
					+ "<a href=\"" + line.getIdentifyURL() + "\" style=\"font-size:18px;font-weight:600;color:#1757b8;\">" + line.getTitle() + "</a>" 
					+ "<p style=\"margin-top:5px;font-size:13px;color:#3dac4e;\">" + messages.getMessage("chose.annonce".concat(line.getType()), null, locale) 
					+ "</p><p style=\"margin-top:20px;font-size:15px;\">" + line.getDescription() + "</p></div>";
			template += item;
		}
		return template;
	}
	
	private final String parseNewsletterAnnonceTemplate(final HttpServletRequest request, final List<AnnonceNewsletterMini> lines, final int todays, final int offers) {
		final String appurl = RequestUtil.getAppurl(request);
		final Locale locale = RequestContextUtils.getLocale(request);
		final String dir = locale.getLanguage().equals("ar") ? "rtl" : "ltr";
		final String left = locale.getLanguage().equals("ar") ? "right" : "left";
		final String marketURL = ConstraintesURL.getMarketplaceAnnoncesMapsiteURL();
		final String template = "" 
				+ "<html dir=\"" + dir + "\">" 
				+ "<head><link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/></head>" 
				+ "<body style=\"font-family:'Open Sans',sans-serif;background:#f0f0f0;text-align:center;padding:10px;\">" 
					+ "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse:collapse;border-color:#f0f0f0;background:#f0f0f0;\">" 
					+ "<tr><td align=\"" + left + "\" style=\"padding:20px 0;\"><a href=\"" + appurl + "\"><img height=\"34\" src=\"cid:logo\"/></a></td></tr>" 
					+ "<tr><td align=\"" + left + "\" style=\"text-align:" + left + ";\">" 
						+ "<div style=\"font-size:20px;font-weight:600;padding:24px;color:#ffffff;background:#2c3f50;text-align:center;\"><p style=\"padding-top:10px;\">" 
							+ messages.getMessage("txt.admin.mailing2", null, locale) + "</p><p style=\"margin-top:20px;font-size:18px;\">" + todays 
							+ "<span style=\"display:inline-block;font-size:14px;color:#d5d5d5;padding:0 10px;\">" + messages.getMessage("lbl.sub.explorer7.1", null, locale) 
							+ "</span></p><p style=\"margin-top:5px;font-size:18px;\">" + offers + "<span style=\"display:inline-block;font-size:14px;color:#d5d5d5;padding:0 10px;\">" 
							+ messages.getMessage("lbl.sub.explorer7.2", null, locale) + "</span></p></div>" + parseNewsletterAnnonce(locale, lines) 
						+ "<div style=\"padding:20px;color:#ffffff;background:#2c3f50;text-align:center;\"><a href=\"" + marketURL  
						+ "\" style=\"display:inline-block;font-size:13px;font-weight:600;padding:14px 34px;color:#3dac4e;background:#ffffff;text-decoration:none;border-radius:3px;\">" 
						+ messages.getMessage("btn.explorer.ads", null, locale) + "</a></div></td></tr>" + parseSocialTemplate(appurl, locale) 
				+ "</table></body></html>";
		return template;
	}
	
	@Override
	public EmailModel generateNewsletterAnnonce(final HttpServletRequest request, final List<AnnonceNewsletterMini> lines, final int todays, final int offers) {
		final String subject = messages.getMessage("txt.admin.mailing2", null, RequestContextUtils.getLocale(request));
		final String message = parseNewsletterAnnonceTemplate(request, lines, todays, offers);
		return new EmailModel(EMAIL_SENDGRID, null, subject, message);
	}
	
	private final String parseNewsletterMailingTemplate(final HttpServletRequest request, final AdmMailingForm mailingForm) {
		final String appurl = RequestUtil.getAppurl(request);
		final Locale locale = RequestContextUtils.getLocale(request);
		final String dir = locale.getLanguage().equals("ar") ? "rtl" : "ltr";
		final String left = locale.getLanguage().equals("ar") ? "right" : "left";
		final String template = "" 
				+ "<html dir=\"" + dir + "\">" 
				+ "<head><link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/></head>" 
				+ "<body style=\"font-family:'Open Sans',sans-serif;background:#f0f0f0;text-align:center;padding:10px;\">" 
					+ "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse:collapse;border-color:#f0f0f0;background:#f0f0f0;\">" 
						+ "<tr><td align=\"" + left + "\" style=\"padding:20px 0;\"><a href=\"" + appurl + "\"><img height=\"34\" src=\"cid:logo\"/></a></td></tr>"
						+ "<tr><td align=\"" + left + "\" style=\"text-align:" + left + ";\">" 
							+ "<div style=\"font-size:20px;font-weight:600;padding:24px;color:#ffffff;background:#2c3f50;text-align:center;\">" 
								+ mailingForm.getTitle().replaceAll("\"", "'") 
							+ "</div><div style=\"padding:30px 34px;color:#353c45;background:#ffffff;border:1px solid #c3c7ca;border-top:none;\">" 
								+ mailingForm.getDetail() + "</div></td></tr>" + parseSocialTemplate(appurl, locale) 
				+ "</table></body></html>";
		return template;
	}
	
	@Override
	public EmailModel generateNewsletterMailing(final HttpServletRequest request, final AdmMailingForm mailingForm) {
		final String subject = mailingForm.getTitle();
		final String message = parseNewsletterMailingTemplate(request, mailingForm);
		return new EmailModel(EMAIL_SENDGRID, null, subject, message);
	}
	
	private final String parseHeaderCompanyTemplate(final NewsletterForm newsletterForm, final String left) {
		final String template = "<tr><td align=\"" + left + "\"><div style=\"display:flex;padding:10px 0;\"><div style=\"width:58px;\"><img height=\"44\" src=\"cid:logo\" style=\""
				+ "width:44px;height:44px;border:2px solid #c3c7ca;border-radius:100%;\"/></div><div style=\"padding-top:5px;\"><span style=\"display:block;font-size:14px;font-weight:600;color:#353c45;\">"
				+ newsletterForm.getTradename() + "</span><span style=\"display:inline-block;font-size:14px;color:#c1392b;\">@</span><span style=\"display:inline-block;font-size:12px;"
				+ "color:#545e74;padding:0 5px;\">" + newsletterForm.getCompanymail() + "</span></div></div></td></tr>";
		return template;
	}
	
	private final String parseCoverCompanyTemplate() {
		return "<tr><td><img src=\"cid:cover\" style=\"display:block;width:100%;height:auto;max-width:100%;\"/></td></tr>";
	}
	
	private final String parseTitleCompanyTemplate(final NewsletterForm newsletterForm) {
		final String template = "<tr><td><div style=\"font-size:20px;font-weight:600px;padding:24px;color:" + newsletterForm.getTextColor() 
				+ ";background:" + newsletterForm.getPaneColor() + ";text-align:center;\">" + newsletterForm.getTitle().replaceAll("\"", "'") + "</div></td></tr>";
		return template;
	}
	
	private final String parseItemsCompanyTemplate(final NewsletterForm newsletterForm, final String left, final Locale locale) {
		String template = "";
		final String paneColor = newsletterForm.getPaneColor();
		final String textColor = newsletterForm.getTextColor();
		for (int i = 0; i < newsletterForm.getItems().size(); i++) {
			final NewsletterItem newsletterItem = newsletterForm.getItems().get(i);
			final String newsURL = newsletterItem.getIdentifyURL();
			final String item = "<div style=\"display:flex;\">" 
					+ (!StringUtils.isEmpty(newsletterItem.getPhotoURL()) 
							? "<div style=\"width:30%;><img src=\"" + ConstraintesURL.URL_APPLICATION.concat(newsletterItem.getPhotoURL())
							+ "\" style=\"display:block;width:100%;height:auto;max-width:100%;\"/></div>" : "") + "<div style=\"width:" 
					+ (!StringUtils.isEmpty(newsletterItem.getPhotoURL()) ? "70%;padding-" + left + ":20px;" : "100%;") + "\"><a href=\"" + newsURL  
					+ "\" style=\"font-size:16px;color:" + paneColor + ";\">" + newsletterItem.getTitle() + "</a><p style=\"font-size:14px;margin:10px 0;\">" 
					+ newsletterItem.getDescription() + "</p><a href=\"" + newsURL + "\" style=\"display:inline-block;font-size:13px;font-weight:600;" 
					+ "padding:10px 25px;color:" + textColor + ";background:" + paneColor + ";text-decoration:none;border-radius:3px;\">" 
					+ messages.getMessage("btn.explorer.detail", null, locale) + "</a></div></div>";
			template += item;
			if(i + 1 < newsletterForm.getItems().size()) {
				template += "<div style=\"width:100%;height:1px;margin:20px 0;background:#c3c7ca\"></div>";
			}
		}
		return template;
	}
	
	private final String parseBodyCompanyTemplate(final NewsletterForm newsletterForm, final String left, final Locale locale) {
		final String template = "<tr><td align=\"" + left + "\" style=\"text-align:" + left + ";\"><div style=\"padding:20px 30px;color:#353c45;background:#ffffff;border:1px solid #c3c7ca;"
				+ (newsletterForm.getCover() != null || !StringUtils.isEmpty(newsletterForm.getTitle()) ? "border-top:none;" : "") + "\">" 
				+ (!StringUtils.isEmpty(newsletterForm.getDetail()) ? newsletterForm.getDetail() 
						: newsletterForm.getItems() != null ? parseItemsCompanyTemplate(newsletterForm, left, locale) : "") + "</div></td></tr>";
		return template;
	}
	
	private final String parseLabelCompanyTemplate(final NewsletterForm newsletterForm, final Locale locale) {
		final String targetURL = newsletterForm.getTarget();
		final String template = "<tr><td><div style=\"font-size:20px;font-weight:600px;padding:30px;color:" + newsletterForm.getTextColor() 
				+ ";background:" + newsletterForm.getPaneColor() + ";text-align:center;\">" 
				+ (!StringUtils.isEmpty(newsletterForm.getFooter()) ? "<p style=\"font-size:18px;padding-bottom:10px;\">" + newsletterForm.getFooter() + "</p>" : "") 
				+ "<a href=\"" + targetURL + "\" style=\"display:inline-block;font-size:13px;font-weight:600;padding:10px 26px;color:" 
				+ newsletterForm.getPaneColor() + ";background:" + newsletterForm.getTextColor() + ";text-decoration:none;border-radius:3px;\">" 
				+ messages.getMessage("chose.button".concat(String.valueOf(newsletterForm.getLabel())), null, locale) + "</a></div></td></tr>";
		return template;
	}
	
	private final String parseSocialCompanyTemplate(final NewsletterForm newsletterForm, final Locale locale) {
		final NewsletterSocial social = newsletterForm.getSocial();
		final String[] images = {"facebook", "twitter", "google", "linkedin"};
		String template = "<tr><td align=\"center\"><div style=\"padding:20px;text-align:center;border-bottom:1px solid #c3c7ca;\"><h3 style=\"color:#353c45;\">" 
				+ messages.getMessage("tool.newsletter.social", null, locale) + " " + newsletterForm.getTradename() + "</h3><ul style=\"list-style:none;padding-left:0;margin-top:10px;\">";
		for (final String image : images) {
			if(!StringUtils.isEmpty(social.buildSocial(image))) {
				final String soicalURL = social.buildSocial(image);
				template += "<li style=\"display:inline-block;padding:5px;\"><a href=\"" + soicalURL + "\"><img height=\"32\" src=\"cid:" + image + "\"/></a></li>";
			}
		}
		return template + "</ul></div></td></tr>";
	}
	
	private final String parseNewsletterCompanyTemplate(final HttpServletRequest request, final NewsletterForm newsletterForm) {
		final Locale locale = RequestContextUtils.getLocale(request);
		final String dir = locale.getLanguage().equals("ar") ? "rtl" : "ltr";
		final String left = locale.getLanguage().equals("ar") ? "right" : "left";
		final String template = "" 
				+ "<html dir=\"" + dir + "\">" 
				+ "<head><link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/></head>" 
				+ "<body style=\"font-family:'Open Sans',sans-serif;background:#f0f0f0;text-align:center;padding:10px;\">" 
					+ "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse:collapse;border-color:#f0f0f0;background:#f0f0f0;\">" 
						+ (newsletterForm.getLogo() != null ? parseHeaderCompanyTemplate(newsletterForm, left) : "") + (newsletterForm.getCover() != null ? parseCoverCompanyTemplate() : "")
						+ (!StringUtils.isEmpty(newsletterForm.getTitle()) ? parseTitleCompanyTemplate(newsletterForm)  : "") + parseBodyCompanyTemplate(newsletterForm, left, locale) 
						+ (newsletterForm.getLabel() != null ? parseLabelCompanyTemplate(newsletterForm, locale) : "") 
						+ (newsletterForm.getSocial() != null && newsletterForm.getSocial().hasPresent() ? parseSocialCompanyTemplate(newsletterForm, locale) : "")
						+ "<tr><td align=\"" + left + "\" style=\"text-align:" + left + ";font-size:12px;line-height:12px;color:#545e74;\">" 
						+ (!StringUtils.isEmpty(newsletterForm.getDescription()) ? "<p style=\"padding-top:10px;\">" + newsletterForm.getDescription() + "</p>" : "") 
						+ "<p style=\"padding-top:10px;\">" + messages.getMessage("txt.mail.footer2.1", null, locale) + "<br><br>" + "&copy;" + String.valueOf(ParseUtil.getCurrYear()) 
						+ " " + messages.getMessage("app.copyright", null, locale) + "</p></td></tr></table></body></html>";
		return template;
	}
	
	@Override
	public EmailModel generateNewsletterCompany(final HttpServletRequest request, final NewsletterForm newsletterForm) {
		final String subject = newsletterForm.getSubject();
		final String message = parseNewsletterCompanyTemplate(request, newsletterForm);
		return new EmailModel(EMAIL_SENDGRID, null, subject, message);
	}
	
}
