package com.contactmanager.webContactManager.cofiguration;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {
	
	public  void  removeMessageFromSession() {
		try {
				System.out.print("removing session message from.............. ");
				   ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		            HttpServletRequest request = attr.getRequest();
		            HttpSession session = request.getSession();
		            session.removeAttribute("message");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
