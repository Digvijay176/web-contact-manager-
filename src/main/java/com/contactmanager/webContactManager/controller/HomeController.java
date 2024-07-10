package com.contactmanager.webContactManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contactmanager.webContactManager.cofiguration.Message;
import com.contactmanager.webContactManager.entities.User;
import com.contactmanager.webContactManager.service.UserService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/contactmanager/public")
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//about page
	@RequestMapping("/about")
	public String about(Model m) {
		m.addAttribute("title","about page");
		return "about";
	}
	
	//sign up page 
	@RequestMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("user",new User());
		return "signup";
	}
	
	//register the user
	@PostMapping("/register")
	public String Register(@Valid @ModelAttribute("user")User user,BindingResult result , @RequestParam(value="turms", defaultValue ="false")boolean turms,Model model,HttpSession session ) {
		
		try {
			if(!(user.getTurms())){	
				System.out.print("you have not agreeded turms and conditions");
				System.out.print(user);
				throw new Exception("you have not agreeded turms and conditions");
			}
			
			if(result.hasErrors()) {
				
				System.out.print(result.toString());
				
				model.addAttribute("user", user);
				return "signup";
			}
				user.setActive(true);
				user.setRole("USER");
				String pass =passwordEncoder.encode(user.getPassword());
				user.setPassword(pass);
				
				User user2 =userService.addUser(user);

				model.addAttribute("user", new User());
				session.setAttribute("message",new Message("Register successfully","alert-success"));
				
				return "signup";
				
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(user);
			session.setAttribute("message", new Message(e.getMessage(), "alert-danger"));
			session.setAttribute("message", new Message("somthing went wrong", "alert-danger"));
			return "signup";
		}
	}
	
	
}
