package com.cdw.business.event;

import java.sql.Date;

import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event,Integer> {

	Iterable<Event> findByEventDateGreaterThanEqual(Date today);
}