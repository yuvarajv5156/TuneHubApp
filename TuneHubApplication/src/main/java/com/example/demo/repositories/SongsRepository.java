package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entites.Songs;

public interface SongsRepository extends JpaRepository<Songs, Integer> {
	// for addSongs()
	public Songs findByName(String name);
}
