package com.cdw.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdw.business.user.User;
import com.cdw.business.user.UserRepository;
import com.cdw.util.CDWMaintenanceReturn;

@CrossOrigin
@Controller    
@RequestMapping(path="/Writers")
public class UserController extends BaseController{
	@Autowired 
	private UserRepository userRepository;

	@GetMapping(path="/List")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping(path="/Get")
	public @ResponseBody List<User> getUser(@RequestParam int id) {
		Optional<User> u = userRepository.findById(id);
		return getReturnArray(u);
	}
	
	@PostMapping(path="/Add") 
	public @ResponseBody CDWMaintenanceReturn addNewUser (@RequestBody User user) {
		try {
			userRepository.save(user);
			return CDWMaintenanceReturn.getMaintReturn(user);
		}
		catch (DataIntegrityViolationException dive) {
			return CDWMaintenanceReturn.getMaintReturnError(user, dive.getRootCause().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			return CDWMaintenanceReturn.getMaintReturnError(user, e.getMessage());
		}
	}
	
	@GetMapping(path="/Remove")
	public @ResponseBody CDWMaintenanceReturn deleteUser (@RequestParam int id) {
		
		Optional<User> user = userRepository.findById(id);
		try {
			userRepository.delete(user.get());
			return CDWMaintenanceReturn.getMaintReturn(user.get());
		}
		catch (DataIntegrityViolationException dive) {
			return CDWMaintenanceReturn.getMaintReturnError(user, dive.getRootCause().toString());
		}
		catch (Exception e) {
			return CDWMaintenanceReturn.getMaintReturnError(user, e.toString());
		}
		
	}

	@PostMapping(path="/Change") 
	public @ResponseBody CDWMaintenanceReturn updateUser (@RequestBody User user) {
		try {
			userRepository.save(user);
			return CDWMaintenanceReturn.getMaintReturn(user);
		}
		catch (DataIntegrityViolationException dive) {
			return CDWMaintenanceReturn.getMaintReturnError(user, dive.getRootCause().toString());
		}
		catch (Exception e) {
			return CDWMaintenanceReturn.getMaintReturnError(user, e.toString());
		}
		
	}
	
	@GetMapping(path = "/Authenticate")
	public @ResponseBody List<User> authenticate(@RequestParam String uname, @RequestParam String pwd) {
		User user = new User();
		user = userRepository.findByUserNameAndPassword(uname, pwd);
		return BaseController.getReturnArray(user);
	}
	//this is unnacceptable as a get method.  maybe take requestparams and try as requestbodies?  If that doesn't work I'm not sure what to do
	
}
