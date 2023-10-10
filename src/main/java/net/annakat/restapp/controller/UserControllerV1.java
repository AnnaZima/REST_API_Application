package net.annakat.restapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.annakat.restapp.model.User;
import net.annakat.restapp.service.UserService;
import net.annakat.restapp.util.LocalDateTimeAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api/v1/users/*")
public class UserControllerV1 extends HttpServlet {
    private final UserService userService = new UserService();
    private final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String requestURI = request.getRequestURI();
        String substring = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        String json = "";
        if (!substring.isEmpty()) {
            int id = Integer.parseInt(substring);
            User user = userService.getUser(id);
            json = GSON.toJson(user);
            writer.println(json);
        } else {
            List<User> users = userService.getAll();
            users.stream().map(GSON::toJson).forEach(writer::println);
          writer.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String collect;
        User user;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            collect = reader.lines().collect(Collectors.joining());
            user = GSON.fromJson(collect, User.class);
        }
        User created = userService.createUser(user);
        PrintWriter writer = response.getWriter();
        writer.println(created);
        writer.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String collect;
        User user;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()))) {
            collect = reader.lines().collect(Collectors.joining());
            user = GSON.fromJson(collect, User.class);
        }
        User updated = userService.updateUser(user);
        PrintWriter writer = resp.getWriter();
        writer.println(updated);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String requestURI = req.getRequestURI();
        String substring = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        int id = Integer.parseInt(substring);
        userService.deleteUser(id);
        writer.println("Complete");
        writer.close();

    }
}
