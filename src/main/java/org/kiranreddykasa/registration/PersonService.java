package org.kiranreddykasa.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private  JavaMailSender  javaMailService;
	
	public void savePerson(Person person){
		
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		mailMessage.setTo(person.getEmail());
		mailMessage.setSubject("Registration");
		mailMessage.setText("Hello " +person.getUserName() +"\n Your registration is successfull");
		javaMailService.send(mailMessage);
		
		personRepository.save(person);
	}

	public Iterable<Person> findAll() {
		
		return personRepository.findAll();
	}
}
