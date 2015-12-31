package org.kiranreddykasa.registration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PersonService personService;
	
	@RequestMapping(value="/")
	public String loadHomePage(Model model){
		
		model.addAttribute("persons", personService.findAll());
		
		return "index";
	}
	
	
	@RequestMapping(value="/addPerson",method=RequestMethod.POST)
	public String addEmployee(@ModelAttribute("person")Person person, Model model){
		logger.info(person.toString());
		personService.savePerson(person);
		
		return "redirect:/";
	}
	
}
