package net.annakat.restapp.service;

import net.annakat.restapp.model.Event;
import net.annakat.restapp.model.UFile;
import net.annakat.restapp.model.User;
import net.annakat.restapp.repository.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {


    FileRepository fileRepository = Mockito.mock(FileRepository.class);
    EventService eventService = Mockito.mock(EventService.class);
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    FileService fileService = new FileService(fileRepository, eventService);

    @BeforeEach
    void init () {
        when(request.getHeader(anyString())).thenReturn("1");
    }


    private UFile getUFile() {
        return new UFile(1, "fileName", "file.path", new ArrayList<>());
    }


    @Test
    void getFile() {
        when(fileRepository.get(1)).thenReturn(getUFile());
        UFile uFile = fileService.getFile(1);
        assertEquals("fileName", uFile.getName());
    }

    @Test
    void createFile() {
        File file = new File("path");
        when(request.getHeader(anyString())).thenReturn("1");
        when(fileRepository.create(any(UFile.class))).thenReturn(getUFile());
        UFile uFile = fileService.addFile(file, request);
        assertEquals("fileName", uFile.getName());
    }

    @Test
    void updateFile() {
        when(fileRepository.update(any(UFile.class))).thenReturn(getUFile());
        UFile uFile = fileService.updateFile(new UFile(), request);
        assertEquals("fileName", uFile.getName());

    }

    @Test
    void deleteFile() {
        fileService.deleteFile(getUFile(), request);
        verify(fileRepository, times(1)).delete(anyInt());
    }

    @Test
    void getAll() {
        List<UFile> files = new ArrayList<>();
        when(fileRepository.getAll()).thenReturn(files);
        List<UFile> filesEntities = fileService.getAll();
        assertEquals(files, filesEntities);

    }

}
