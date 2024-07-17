package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entites.Playlist;
import com.example.demo.repositories.PlaylistRepository;

@Service
public class PlaylistServiceImplementation implements PlaylistService{

	@Autowired
	PlaylistRepository playlistrepo;

	@Override
	public void addPlaylist(Playlist playlist) {
			playlistrepo.save(playlist);
	}

	@Override
	public List<Playlist> fetchPlaylist() {
		return playlistrepo.findAll();
	}
}
