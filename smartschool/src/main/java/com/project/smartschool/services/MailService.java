package com.project.smartschool.services;

import javax.mail.MessagingException;
import java.util.Map;

public interface MailService {

	void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel,
			String htmlFile) throws MessagingException;
}
