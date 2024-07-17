package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entites.Songs;
import com.example.demo.repositories.SongsRepository;

@Service
public class SongsServiceImplementation implements SongsService{
	@Autowired
	SongsRepository songsrepo;

	@Override
	public String addSongs(Songs song) {
		songsrepo.save(song);
		return "Song Saved Successfully";
	}

	@Override
	public boolean songNameExist(String name) {
		
		if(songsrepo.findByName(name)== null) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public List<Songs> fetchAllSongs() {
		List<Songs> listsongs = songsrepo.findAll();
		return listsongs;
	}

	@Override
	public void updateSong(Songs song) {
		songsrepo.save(song);
	}

	@Override
	public void findById(int id) {
		songsrepo.deleteById(id);
		
	}

	

}
