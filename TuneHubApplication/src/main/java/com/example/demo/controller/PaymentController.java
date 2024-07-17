package com.example.demo.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entites.Playlist;
import com.example.demo.entites.Songs;
import com.example.demo.entites.Users;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongsService;
import com.example.demo.services.UsersService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
	@Autowired
	 UsersService userv;
	
	@Autowired
	 SongsService sserv;
	
	@Autowired
	 PlaylistService pserv;

	@PostMapping("/createOrder")
	@ResponseBody
	public String createOrder() {
		
		Order order = null;
		
		try {
			RazorpayClient razorpay = new RazorpayClient("rzp_test_IrCWakyaG99yGA", "RIXK9gKaJjqgp3kNfXIz2A91");

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", 11900);
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "receipt#1");
			JSONObject notes = new JSONObject();
			notes.put("notes_key_1", "Tea, Earl Grey, Hot");
			orderRequest.put("notes", notes);

			order = razorpay.orders.create(orderRequest);
			
		} catch (Exception e) {
			System.out.println("exception while creating order");
		}
		
		return order.toString();
	}

	@PostMapping("/verify")
	@ResponseBody
	public boolean verifyPayment(@RequestParam String orderId, @RequestParam String paymentId,
			@RequestParam String signature) {
		try {
			// Initialize Razorpay client with your API key and secret
			RazorpayClient razorpayClient = new RazorpayClient("rzp_test_IrCWakyaG99yGA", "RIXK9gKaJjqgp3kNfXIz2A91");
			
			// Create a signature verification data string
			String verificationData = orderId + "|" + paymentId;

			// Use Razorpay's utility function to verify the signature
			boolean isValidSignature = Utils.verifySignature(verificationData, signature, "RIXK9gKaJjqgp3kNfXIz2A91");

			return isValidSignature;
			
		} catch (RazorpayException e) {
			e.printStackTrace();
			return false;
		}
	}

	// payment success -> update to premium user
	@GetMapping("payment-success")
	public String paymentSuccess(HttpSession session, Model model) {
		
		String email = (String) session.getAttribute("email");
		Users user = userv.getUser(email);
		user.setPremium(true);
		userv.updateUser(user);
		
		List<Songs> listsongs = sserv.fetchAllSongs();
		model.addAttribute("songs", listsongs);
		List<Playlist> plist = pserv.fetchPlaylist();
		model.addAttribute("plist", plist);
		Users userName = userv.getUserName(email);
		model.addAttribute("userName", userName);
		
		return "customerhome2";
	}

	// payment failure -> redirect to login
	@GetMapping("payment-failure")
	public String paymentFailure() {
		//payment-error page
		return "index";

	}
	
}
