package net.annakat.restapp.service;

import net.annakat.restapp.model.Event;
import net.annakat.restapp.repository.EventRepository;
import net.annakat.restapp.repository.impl.EventRepositoryImpl;

public class EventService {
    private final EventRepository eventRepository = new EventRepositoryImpl();

    public Event createEvent(Event event) {
        return eventRepository.create(event);

    }
}
