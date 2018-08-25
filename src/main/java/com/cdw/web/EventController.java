package com.cdw.web;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
	
	@GetMapping(path="/List")
	public @ResponseBody Iterable<Event> getAllEvents() {
		Iterable<Event> events = eventRepository.findAll();
		LocalDateTime now = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
		long oneDay = 86400000;
		Timestamp tomorrow = new Timestamp(System.currentTimeMillis() + oneDay);
		LocalDateTime tomorrowMidnight = tomorrow.toLocalDateTime().withHour(00).withMinute(00).withSecond(0).withNano(0);
		for (Event event : events) {
			LocalDateTime start = event.getEventStart().toLocalDateTime().plusHours(4);
			if(event.getEventEnd() == null) {
				if(start.isBefore(tomorrowMidnight)) {
					eventRepository.deleteById(event.getId());
				}
			}else {
				LocalDateTime end = event.getEventEnd().toLocalDateTime().plusHours(4);
				if(end.isBefore(now)){
					eventRepository.deleteById(event.getId());
				}
			}
		}
		return eventRepository.findAll();
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
