package org.kiranreddykasa.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping(value="/")
	public String loadHomePage(Model model){
		
		model.addAttribute("persons", personService.findAll());
		
		return "index";
	}
	
	
	@RequestMapping(value="/addPerson",method=RequestMethod.POST)
	public String addEmployee(@ModelAttribute("person")Person person, Model model){
		
		personService.savePerson(person);
		
		return "redirect:/";
	}
	
}
