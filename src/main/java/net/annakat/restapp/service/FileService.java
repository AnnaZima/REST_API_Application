package net.annakat.restapp.service;

import net.annakat.restapp.model.Event;
import net.annakat.restapp.model.UFile;
import net.annakat.restapp.model.User;
import net.annakat.restapp.repository.EventRepository;
import net.annakat.restapp.repository.FileRepository;
import net.annakat.restapp.repository.impl.HibernateEventRepositoryImpl;
import net.annakat.restapp.repository.impl.HibernateFileRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class FileService {
    private final FileRepository fileRepository;
    private final EventService eventService;

    public FileService() {
        fileRepository = new HibernateFileRepositoryImpl();
        eventService = new EventService();

    }

    public FileService(FileRepository fileRepository, EventService eventService) {
        this.fileRepository = fileRepository;
        this.eventService = eventService;
    }

    public Event makeEvent(HttpServletRequest request, String nameEvent, UFile file) {
        User user = new User();
        user.setId(Integer.parseInt(request.getHeader("user-id")));
        Event event = new Event();
        event.setName(nameEvent);
        event.setCreated(LocalDateTime.now());
        event.setUFile(file);
        event.setUser(user);
        return eventService.createEvent(event);
    }

    public UFile addFile(File file, HttpServletRequest request) {
        UFile uFile = new UFile();
        uFile.setName(file.getName());
        uFile.setFilePath(file.getPath());
        UFile created = fileRepository.create(uFile);
        Event event = makeEvent(request, "Upload", created);
        eventService.createEvent(event);

        return created;
    }

    public UFile getFile(Integer id) {
        return fileRepository.get(id);
    }

    public List<UFile> getAll() {
        return fileRepository.getAll();
    }

    public UFile updateFile(UFile uFile, HttpServletRequest request) {
        UFile update = fileRepository.update(uFile);
        Event event = makeEvent(request, "Update", update);
        eventService.createEvent(event);
        return update;
    }

    public void deleteFile(UFile uFile, HttpServletRequest request) {
        Integer id = uFile.getId();
        fileRepository.delete(id);
        makeEvent(request, "Delete", uFile);
    }
}
