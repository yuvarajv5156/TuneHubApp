package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entites.Playlist;
import com.example.demo.entites.Songs;
import com.example.demo.entites.Users;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongsService;
import com.example.demo.services.UsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {


	@Autowired
	UsersService userv;

	@Autowired
	SongsService sserv;

	@Autowired
	PlaylistService pserv;

	@GetMapping("/map-home")
	public String home(HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
		Users userName = userv.getUserName(email);
		model.addAttribute("userName", userName);
		return "customerhome";
	}
	@GetMapping("/map-adminhome")
	public String adminhome(HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
		Users userName = userv.getUserName(email);
		model.addAttribute("userName", userName);
		return "adminhome";
	}
	
	

	@PostMapping("/register")
	public String addUser(@ModelAttribute Users user) {

		/*
		 * checking whether the emailId already exist in the DB or not.
		 * 
		 * if emailExist method return false means email is not exist in DB. Then return
		 * the control to registersuccess.html page. if emailExist method return true
		 * means email is already exist in DB. Then return the control to
		 * registerfail.html page.
		 */

		boolean userstatus = userv.emailExists(user.getEmail());

		if (userstatus == false) {
			userv.addUsers(user);
			return "registersuccess";

		} else {
			return "registerfail";
		}
	}

	@PostMapping("/login")
	public String validateUser(@RequestParam String email, @RequestParam String password, HttpSession session,
			Model model) {
		/*
		 * Invoking validateUser() in service
		 * 
		 * Validating user using email & password parameters. If the validateUser method
		 * return true means given email & password is matches with DB email & password
		 * correctly.
		 */

		if (userv.validateUser(email, password) == true) {
			session.setAttribute("email", email);
			// checking whether the user is admin or customer
			if (userv.findRole(email).equals("admin")) {
				Users userName = userv.getUserName(email);
				model.addAttribute("userName", userName);
				return "adminhome";
			} else {

				Users userName = userv.getUserName(email);
				model.addAttribute("userName", userName);
				return "customerhome";
			}
		} else {
			return "loginfail";
		}

	}

	@GetMapping("/exploreSongs")
	public String exploreSongs(HttpSession session, Model model) {

		String email = (String) session.getAttribute("email");

		Users user = userv.getUser(email);

		boolean userStatus = user.isPremium();

		if (userStatus == true) {
			List<Songs> listsongs = sserv.fetchAllSongs();
			model.addAttribute("songs", listsongs);
			List<Playlist> plist = pserv.fetchPlaylist();
			model.addAttribute("plist", plist);
			Users userName = userv.getUserName(email);
			model.addAttribute("userName", userName);
			return "customerhome2";
		} else {
			return "payment";
		}
	}

	@PostMapping({ "/logout" })
	public String logoutDomin(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return "index";
	}
}
