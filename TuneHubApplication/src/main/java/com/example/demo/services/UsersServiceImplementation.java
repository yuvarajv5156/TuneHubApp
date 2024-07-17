package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entites.Users;
import com.example.demo.repositories.UsersRepository;

@Service
public class UsersServiceImplementation implements UsersService {

	@Autowired
	UsersRepository urepo;

	@Override
	public String addUsers(Users user) { // for register
		urepo.save(user);
		return "user is created and saved";
	}

	@Override
	public boolean emailExists(String email) { // for register
		if (urepo.findByEmail(email) == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean validateUser(String email, String password) { // for login
		Users user = urepo.findByEmail(email);
		if (user == null) {
			return false;
		}
		String db_password = user.getPassword();
		if (db_password.equals(password)) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public String findRole(String email) { // for login
		return urepo.findByEmail(email).getRole();
	}

	@Override
	public Users getUser(String email) {
		return urepo.findByEmail(email);
	}

	@Override
	public void updateUser(Users user) {
		urepo.save(user);
	}

	@Override
	public String getUserName(Users user) {
		return user.getUsername();
	}

	@Override
	public Users getUserName(String email) {
		Users user = urepo.findByEmail(email);
		return user;
	}

}
