package net.annakat.restapp.service;

import net.annakat.restapp.model.Event;
import net.annakat.restapp.repository.EventRepository;
import net.annakat.restapp.repository.impl.EventRepositoryImpl;

import java.util.List;

public class EventService {
    private final EventRepository eventRepository = new EventRepositoryImpl();

    public Event createEvent(Event event) {
        return eventRepository.create(event);
    }

    public void deleteEvent(Integer id) {
        eventRepository.delete(id);
    }

    public Event getEvent(Integer id) {
        return eventRepository.get(id);
    }

    public Event updateEvent(Event event) {
        return eventRepository.update(event);
    }

    public List<Event> getAll() {
        return eventRepository.getAll();
    }

}
