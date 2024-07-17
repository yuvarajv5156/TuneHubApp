package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entites.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer>{

}
