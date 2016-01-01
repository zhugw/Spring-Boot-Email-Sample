package org.kiranreddykasa.registration;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class PersonService {
	private static Logger logger = LoggerFactory.getLogger(PersonService.class);
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private  JavaMailSender  javaMailService;
	@Autowired
	private  Configuration  freemarkerConfiguration;
	
	private static final String DEFAULT_ENCODING = "utf-8";
	
	public void savePerson(Person person){
		
//		sendSimpleMessage(person);
		sendHtmlMessage(person);
		
		personRepository.save(person);
	}

	private void sendHtmlMessage(Person person) {
		try {
			MimeMessage msg = javaMailService.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);

			helper.setFrom("no-reply@foo.com");
			helper.setTo(person.getEmail());
			helper.setSubject("Registration");

			String content = generateContent(person.getUserName());
			helper.setText(content, true);
			javaMailService.send(msg);
		} catch (MessagingException e) {
			logger.error("build email failed", e);
		} catch (Exception e) {
			logger.error("send email failed", e);
		}
	}
	/**
	 * 使用Freemarker生成html格式内容.
	 */
	private String generateContent(String userName) throws MessagingException {

		try {
			Map context = Collections.singletonMap("userName", userName);
			Template template = freemarkerConfiguration.getTemplate("mailTemplate.ftl", DEFAULT_ENCODING);
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
		} catch (IOException e) {
			logger.error("FreeMarker template not exist", e);
			throw new MessagingException("FreeMarker template not exist", e);
		} catch (TemplateException e) {
			logger.error("FreeMarker process failed", e);
			throw new MessagingException("FreeMarker process failed", e);
		}
	}
	private void sendSimpleMessage(Person person) {
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		mailMessage.setTo(person.getEmail());
		mailMessage.setSubject("Registration");
		mailMessage.setText("Hello " +person.getUserName() +"\n Your registration is successfull");
		javaMailService.send(mailMessage);
	}

	public Iterable<Person> findAll() {
		
		return personRepository.findAll();
	}
}
