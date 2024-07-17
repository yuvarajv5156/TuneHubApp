package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entites.Songs;
import com.example.demo.entites.Users;
import com.example.demo.services.SongsService;
import com.example.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class SongsController {
	
	@Autowired
	SongsService sserv;

	@Autowired
	UsersService userv;

	@GetMapping("/map-addsongs")
	public String addsongsMapping(HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
		Users userName = userv.getUserName(email);
		model.addAttribute("userName", userName);
		return "addsongs";
	}

	@PostMapping("/addsongs")
	public String addSongs(@ModelAttribute Songs song) {
		boolean status = sserv.songNameExist(song.getName());

		if (status == false) {
			sserv.addSongs(song);
			return "addsongssuccess";

		} else {
			return "addsongsfail";
		}
	}

	@GetMapping("/adminsongs")
	public String fetchAllSongsToAdmin(HttpSession session, Model model) {
		List<Songs> listsongs = sserv.fetchAllSongs();
		model.addAttribute("songs", listsongs);

		String email = (String) session.getAttribute("email");
		Users userName = userv.getUserName(email);
		model.addAttribute("userName", userName);
		return "adminsongs";
	}

	@GetMapping("/customersongs")
	public String fetchAllSongsToCustomer(HttpSession session, Model model) {

		boolean primeStatus = true;

		if (primeStatus == true) {
			List<Songs> listsongs = sserv.fetchAllSongs();
			model.addAttribute("songs", listsongs);
			String email = (String) session.getAttribute("email");
			Users userName = userv.getUserName(email);
			model.addAttribute("userName", userName);

			return "customersongs";
		} else {
			return "payment";
		}
	}

	@GetMapping("/delete")
	public String deleteSongs(@RequestParam int id) {
		sserv.findById(id);

		return "redirect:/adminsongs";
	}
	
	

}
