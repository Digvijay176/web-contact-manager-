package com.contactmanager.webContactManager.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.contactmanager.webContactManager.cofiguration.Message;
import com.contactmanager.webContactManager.entities.Contact;
import com.contactmanager.webContactManager.entities.User;
import com.contactmanager.webContactManager.helper.HandleMultipartFile;
import com.contactmanager.webContactManager.repository.ContactRepository;
import com.contactmanager.webContactManager.repository.UserRepository;
import com.contactmanager.webContactManager.service.ContactService;
import com.contactmanager.webContactManager.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private HandleMultipartFile hmpf;
	@Autowired
	private ContactService contactService;

	
	//------------------add comman data---------------------------
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String name = principal.getName();
		System.out.println("this is the name of login person" + name);
		Optional<User> user = userRepository.findByEmail(name);
		model.addAttribute(user.get());
	}

	//------------------------------------get user dash-board-------------------------
	@RequestMapping("/dashboard")
	public String userIndex(Model model, Principal principal) {

		String name = principal.getName();
		System.out.println("this is the name of login person" + name);
		Optional<User> user = userRepository.findByEmail(name);

		if (user.isPresent()) {
			User u = user.get();
			String role = u.getRole();

			model.addAttribute("user", u);

			switch (role) {
			case "USER":
				return "/normal/userdashboard";
			case "ADMIN":
				return "/admin/dashboard";
			default:
				return "/contactmanager/public/";
			}
		} else {
			return "/contactmanager/public/";
		}

	}

	//-------------------------------------open add form handler------------------------------------------//
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {

		model.addAttribute("title", "add contact page");
		model.addAttribute("contact", new Contact());

		return "/normal/add_contact_form";
	}

	//-------------------------------------- get view page handler--------------------------------------//
	@GetMapping("/view-contact/{page}")
	public String openViewContact(@PathVariable("page") Integer page, Model m, Principal principal) {
		String username = principal.getName();
		Optional<User> uopt = userRepository.findByEmail(username);

		User user = uopt.get();
		org.springframework.data.domain.Pageable p = PageRequest.of(page, 7);
		org.springframework.data.domain.Page<Contact> contacts = contactRepository.findContactByUser(user.getId(), p);
		m.addAttribute("contacts", contacts);
		m.addAttribute("currentpage", page);
		m.addAttribute("totalPages", contacts.getTotalPages());
		m.addAttribute("title", "view contact");
		return "/normal/viewcontact";
	}
	
	
	//---------------------------------------------add contact--------------------------------------------------//
	@PostMapping("/contact-handler")
	public String addContact(@Valid @ModelAttribute Contact contact, @RequestParam("imageFile") MultipartFile file,
			BindingResult result, Model model, Principal principal, HttpSession session) throws IOException {

		Optional<User> user = userRepository.findByEmail(principal.getName());
		contact.setUser(user.get());

		try {

			if (file != null) {
				System.out.print(file.getOriginalFilename());

				if (hmpf.addFile(file)) {
					contact.setImageUrl(file.getOriginalFilename());
				}
//				File saveFile = new ClassPathResource("/static/user-image").getFile();
//
//				Path paths = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
//				Files.copy(file.getInputStream(), paths, StandardCopyOption.REPLACE_EXISTING);
			}
			if (result.hasErrors()) {
				model.addAttribute("contact", contact);
				return "/normal/add_contact_form";
			}
			if (file == null) {
				contact.setImageUrl("empty_profile.png");
			}

			System.out.print(contact);
			contactService.addContact(contact);
			model.addAttribute("contact", new Contact());
			session.setAttribute("message", new Message("Contact Added Successfully !", "alert-success"));
			return "/normal/add_contact_form";

		} catch (Exception e) {
			session.setAttribute("message", new Message("something went wrong", "alert-warning"));
			return "/normal/add_contact_form";
		}
	}

	
	//------------------------------------------------------------view single contact ------------------------------------------//
	// view single contact
	@GetMapping("/contact/{id}")
	public String getContactById(@PathVariable("id") int id, Model model, HttpSession session, Principal principal) {

		// get contact by user id
		Optional<Contact> contactOpt = contactService.getContactById(id);

		// get user by user name
		String name = principal.getName();
		User user = userRepository.findByEmail(name).get();

		Contact contact = contactOpt.get();
		try {
			if (user.getId() == contact.getUser().getId()) {
				model.addAttribute("contactDetails", contactOpt.get());
				return "/normal/contact_profile";
			}
			throw new Exception("user not available!");

		} catch (Exception e) {
			e.printStackTrace();

			return "redirect:/user/view-contact/0";
		}
	}

	//---------------------------------------------delete the contact----------------------------------------------/
	
	// delete contact
	@GetMapping("/contact/delete/{id}")
	public String deleteContactById(@PathVariable("id") int id, Model model, Principal principal) {
		// get user............
		User user = userRepository.findByEmail(principal.getName()).get();
		Contact contact = contactService.getContactById(id).get();

		System.out.print("delete api is called ........");

		// check point in case user try to delete any other contact......
		if (user.getId() == contact.getUser().getId()) {
			// remove photo from file before deleting user......
			// ...............
			// ...............
			contactService.deleteContactById(id);
		}
		model.addAttribute("contact");
		return "redirect:/user/view-contact/0";
	}

	//---------------------------------------------get page to update profile------------------------------//
	
	// get page to update
	@GetMapping("/contact/update/{id}")
	public String updateConatct(@PathVariable("id")int id, Model model,Principal principal) {
		
		//get contact by user id
		Contact contact= contactService.getContactById(id).get();
		System.out.print(contact);
		String name = principal.getName();
		User user = userRepository.findByEmail(name).get();
		
		//check point 
		if(contact.getUser().getId()==user.getId()) {
			model.addAttribute("contact",contact);
		}
		return "/normal/update_page";
	}

	
	//----------------------------------------------update contact by user----------------------------------------------------------------------------//
	@PostMapping("/contact/update-handler/{id}")
	public String updateContactHandler(@PathVariable("id")int id, @ModelAttribute("contact") Contact contact, Model model, Principal principal,HttpSession session) {
	    
		System.out.print(contact);
		System.out.println("Inside update contact handler......");
	    
	    try {
	        User user = userRepository.findByEmail(principal.getName())
	                                   .orElseThrow(() -> new RuntimeException("User not found"));
	        
	        Contact existingContact = contactService.getContactById(id)
	                                                .orElseThrow(() -> new RuntimeException("Contact not found"));

	        if (user.getId()==(existingContact.getUser().getId())) {
	            Contact updated = contactService.updateContact(contact,id);   
	        	model.addAttribute("contact", existingContact);
	        	session.setAttribute("message", new Message("Contact has been Update sucessfully !", "alert-success"));
	            return "redirect:/user/contact/" + id;
	        } else {
	            model.addAttribute("error", "You do not have permission to update this contact");
	            return "error"; // 
	        }
	    } catch (Exception e) {
	        model.addAttribute("error", "An error occurred: " + e.getMessage());
	        return "error"; 
	    }
	    
	}
	
	//------------------------------------------------------------------------------------------------------------------//
	
	
	
	
	//get profile page
	@GetMapping("/profile-page")
	public String getProfilePage(Model model,Principal principal)
	{
	 String name = principal.getName();
	 User user = userRepository.findByEmail(name).get();
	 model.addAttribute("user",user);
		return "/normal/profile_page";
	}
	
	//-------------------update profile photo of contact by user---------------------------------
	@PostMapping("/contact/profile/update/{id}")
	@ResponseBody
	public String updateProfilePhoto(@PathVariable("id")int id, Model model, Principal principal, MultipartFile file) {
		System.out.print("api  update/contact/profile/id hit by api");
		if(file.isEmpty()) {
            
		}
		return "update/contact/profile/id hit by api";
	}
	
}
