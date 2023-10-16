package net.annakat.restapp.service;

import net.annakat.restapp.model.Event;
import net.annakat.restapp.model.UFile;
import net.annakat.restapp.model.User;
import net.annakat.restapp.repository.EventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    EventRepository eventRepository;
    @InjectMocks
    EventService eventService;

    private Event getEventEntities() {
        return new Event(1, "name", LocalDateTime.now(), new User(), new UFile());
    }

    @Test
    void getEvent() {
        when(eventRepository.get(anyInt())).thenReturn(getEventEntities());
        Event event = eventService.getEvent(1);
        Assertions.assertEquals("name", event.getName());
    }

    @Test
    void createEvents() {
        when(eventRepository.create(any(Event.class))).thenReturn(getEventEntities());
        Event event = eventService.createEvent(new Event());
        Assertions.assertEquals("name", event.getName());
    }

}
