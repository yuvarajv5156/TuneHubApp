package com.example.demo.services;

import java.util.List;

import com.example.demo.entites.Playlist;

public interface PlaylistService {
	public void addPlaylist(Playlist playlist);

	public List<Playlist> fetchPlaylist();

}
