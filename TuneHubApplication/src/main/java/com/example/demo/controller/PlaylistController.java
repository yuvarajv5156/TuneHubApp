package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entites.Playlist;
import com.example.demo.entites.Songs;
import com.example.demo.entites.Users;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongsService;
import com.example.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PlaylistController {
	@Autowired
	PlaylistService pserv;

	@Autowired
	SongsService sserv;

	@Autowired
	UsersService userv;

	@GetMapping("/createplaylist")
	public String createPlaylist(HttpSession session, Model model) {

		// fetching the songs using songs service
		List<Songs> songslist = sserv.fetchAllSongs();

		// Adding the songs in the model
		model.addAttribute("songslist", songslist);

		String email = (String) session.getAttribute("email");
		Users userName = userv.getUserName(email);
		model.addAttribute("userName", userName);

		// sending createplaylist
		return "createplaylist";
	}

	@PostMapping("/addplaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist, Model model) {

		// updating song table
		List<Songs> songsList = playlist.getSong();

		// Checking If the songsList null or not
		if (songsList == null || songsList.isEmpty()) {
			// Handle the case where songsList is null or empty
			model.addAttribute("error", "No songs selected for the playlist.");
			return "playlistfail";
		}

		// adding playlist
		pserv.addPlaylist(playlist);

		for (Songs song : songsList) {
			song.getPlaylist().add(playlist);
			sserv.updateSong(song);
		}

		return "playlistsuccess";

	}

	@GetMapping("/adminplaylist")
	public String adminplaylist(HttpSession session, Model model) {

		List<Playlist> plist = pserv.fetchPlaylist();
		// System.out.println(plist);
		model.addAttribute("plist", plist);

		String email = (String) session.getAttribute("email");
		Users userName = userv.getUserName(email);
		model.addAttribute("userName", userName);
		return "adminplaylist";
	}

	@GetMapping("/customerplaylist")
	public String customerplaylist(HttpSession session, Model model) {

		List<Playlist> plist = pserv.fetchPlaylist();
		// System.out.println(plist);
		model.addAttribute("plist", plist);

		String email = (String) session.getAttribute("email");
		Users userName = userv.getUserName(email);
		model.addAttribute("userName", userName);
		return "customerplaylist";
	}
	

}
