package com.cdw.web;

import java.util.Date;
import java.util.Calendar;
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
import com.cdw.business.event.Event;
import com.cdw.business.event.EventRepository;
import com.cdw.util.CDWMaintenanceReturn;

@CrossOrigin
@Controller    
@RequestMapping(path="/Events")
public class EventController extends BaseController{
	@Autowired 
	private EventRepository eventRepository;
	
	//untested: no errors present I'm not sure if the java.sql date and java.util Date will be compatible??  Will test from front end
	@GetMapping(path="/Clean")
	public void cleanEvents() {
		Iterable<Event> events = eventRepository.findAll();
		Date today = Calendar.getInstance().getTime();
		for (Event event : events) {
			if(event.getEventDate().before(today)) {
				eventRepository.deleteById(event.getId());
			}
		}
	}

	@GetMapping(path="/List")
	public @ResponseBody Iterable<Event> getAllEvents() {
		return eventRepository.findAll();
	}
	
	@GetMapping(path="/Get")
	public @ResponseBody List<Event> getEvent(@RequestParam int id) {
		Optional<Event> u = eventRepository.findById(id);
		return getReturnArray(u);
	}
	
	@PostMapping(path="/Add") 
	public @ResponseBody CDWMaintenanceReturn addNewEvent (@RequestBody Event event) {
		try {
			eventRepository.save(event);
			return CDWMaintenanceReturn.getMaintReturn(event);
		}
		catch (DataIntegrityViolationException dive) {
			return CDWMaintenanceReturn.getMaintReturnError(event, dive.getRootCause().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			return CDWMaintenanceReturn.getMaintReturnError(event, e.getMessage());
		}
	}
	
	@GetMapping(path="/Remove")
	public @ResponseBody CDWMaintenanceReturn deleteEvent (@RequestParam int id) {
		
		Optional<Event> event = eventRepository.findById(id);
		try {
			eventRepository.delete(event.get());
			return CDWMaintenanceReturn.getMaintReturn(event.get());
		}
		catch (DataIntegrityViolationException dive) {
			return CDWMaintenanceReturn.getMaintReturnError(event, dive.getRootCause().toString());
		}
		catch (Exception e) {
			return CDWMaintenanceReturn.getMaintReturnError(event, e.toString());
		}
		
	}

	@PostMapping(path="/Change") 
	public @ResponseBody CDWMaintenanceReturn updateEvent (@RequestBody Event event) {
		try {
			eventRepository.save(event);
			return CDWMaintenanceReturn.getMaintReturn(event);
		}
		catch (DataIntegrityViolationException dive) {
			return CDWMaintenanceReturn.getMaintReturnError(event, dive.getRootCause().toString());
		}
		catch (Exception e) {
			return CDWMaintenanceReturn.getMaintReturnError(event, e.toString());
		}
		
	}
	
	
}
