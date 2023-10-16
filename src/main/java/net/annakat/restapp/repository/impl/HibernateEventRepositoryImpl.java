package net.annakat.restapp.repository.impl;

import net.annakat.restapp.model.Event;
import net.annakat.restapp.repository.EventRepository;
import net.annakat.restapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateEventRepositoryImpl implements EventRepository {
    @Override
    public Event create(Event object) {
        Event event;
        try(Session session = HibernateUtil.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(object);
             event = session.get(Event.class, object.getName());
            transaction.commit();
        }
        return event;
    }

    @Override
    public Event get(Integer id) {
        Event event;
        try(Session session = HibernateUtil.openSession()) {
            event = session.get(Event.class, id);
        }
        return event;
    }

    @Override
    public Event update(Event object) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        Event event = new Event();
        event.setId(id);
        try(Session session = HibernateUtil.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(event);
            transaction.commit();
        }
    }

    @Override
    public List<Event> getAll() {
        List<Event> eventList;
        try(Session session = HibernateUtil.openSession()) {
            Query<Event> events = session.createQuery("From Event", Event.class);
            eventList = events.list();
        }
        return eventList;
    }
}
