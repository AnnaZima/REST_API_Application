package net.annakat.restapp.controller;

import com.google.gson.Gson;
import net.annakat.restapp.model.Event;
import net.annakat.restapp.model.UFile;
import net.annakat.restapp.model.User;
import net.annakat.restapp.service.EventService;
import net.annakat.restapp.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@WebServlet("/api/v1/files/*")
public class FileControllerV1 extends HttpServlet {
    private final FileService fileService = new FileService();
    private final EventService eventService = new EventService();

    private File createFile() {
        File file = new File("src/main/resources/files");
        if (!file.exists()) {
            file.mkdirs();
        }
        String name = UUID.randomUUID().toString();
        return new File(file, name);
    }

    private Event makeEvent(HttpServletRequest request, String nameEvent, UFile savedFile) {
        User user = new User();
        user.setId(Integer.parseInt(request.getHeader("user-id")));
        Event event = new Event();
        event.setName(nameEvent);
        event.setCreated(LocalDateTime.now());
        event.setUFile(savedFile);
        event.setUser(user);
        return eventService.createEvent(event);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String requestURI = request.getRequestURI();
        String substring = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        Gson gson = new Gson();
        if (!substring.isEmpty()) {
            int id = Integer.parseInt(substring);
            UFile file = fileService.getFile(id);
            writer.println(gson.toJson(file));
        } else {
            List<UFile> allFiles;
            allFiles = fileService.getAll();
            writer.println(gson.toJson(allFiles));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("пришел запрос на загрузку файла ");
        File file = createFile();
        try (ServletInputStream inputStream = req.getInputStream();
             FileWriter fileWriter = new FileWriter(file)) {
            int b;
            while ((b = inputStream.read()) != -1) {
                fileWriter.write(b);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        UFile uFile = new UFile();
        uFile.setName(file.getName());
        uFile.setFilePath(file.getPath());
        UFile savedFile = fileService.addFile(uFile);
        Event event = makeEvent(req, "Upload", savedFile);
        PrintWriter writer = resp.getWriter();
        writer.println("Файл " + file.getName() + " загружен" + " ID: " + savedFile.getId() + " событие: " + event);


    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String requestURI = request.getRequestURI();
        String substring = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        UFile uFile = new UFile();
        String collect;
        Gson gson = new Gson();
        if (!substring.isEmpty()) {
            int id = Integer.parseInt(substring);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
                collect = bufferedReader.lines().collect(Collectors.joining());
                uFile = gson.fromJson(collect, UFile.class);
                uFile.setId(id);
            }
        } else {
            writer.println("Укажите File ID для изменения ");
        }
        UFile updatedFile = fileService.updateFile(uFile);
        makeEvent(request, "Update", updatedFile);
        writer.println(updatedFile);

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String requestURI = request.getRequestURI();
        String substring = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        int id = Integer.parseInt(substring);
        UFile uFile = fileService.getFile(id);
        String filePath = uFile.getFilePath();
        File file = new File(filePath);
        boolean isDelete = file.delete();
        if (isDelete) {
            fileService.deleteFile(id);
            makeEvent(request, "Delete", uFile);
            writer.println("Файл успешно удален");
        } else {
            writer.println("Не удалось удалить файл, проверьте правильность вводимых данных");
        }


    }
}
