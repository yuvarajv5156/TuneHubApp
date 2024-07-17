package com.example.demo.services;

import com.example.demo.entites.Users;

public interface UsersService {

	public String addUsers(Users user); // for register

	public boolean emailExists(String email); // for register

	public boolean validateUser(String email, String password); // for login

	public String findRole(String email); // for login

	public Users getUser(String email);

	public void updateUser(Users user);

	public String getUserName(Users user);

	public Users getUserName(String email);

}
