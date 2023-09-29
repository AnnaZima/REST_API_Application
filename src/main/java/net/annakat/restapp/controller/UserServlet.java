package net.annakat.restapp.controller;

import net.annakat.restapp.model.Event;
import net.annakat.restapp.model.User;
import net.annakat.restapp.service.EventService;
import net.annakat.restapp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final EventService eventService = new EventService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        String docType = "<!DOCTYPE html>";

        writer.println(docType + "<html><head><title>" + "User Info" + "</title></head><body>");
        writer.println("<h1>USER DATA</h1>");
        writer.println("<br/>");
        User user = userService.getUser(Integer.valueOf(request.getParameter("id")));
        String name = user.getName();
        List<Event> events = user.getEvents();
        writer.println("User name: " + name);
        writer.println("User events: " + events);
        writer.println("</body></html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("name");

//        List<Event> eventList = Arrays.stream(events.split(" ")).toList().stream().map(s -> {
//            Event event = new Event();
//            event.setName(s);
//            return event;
//        }).toList();
//        List<Event> userEvent = eventList.stream().map(eventService::createEvent).toList();

        User user = new User();
        user.setName(name);
        User createdUser = userService.createUser(user);
        PrintWriter writer = response.getWriter();
        writer.println("Пользователь: " + createdUser + " создан");
        writer.close();


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //что можно менять?

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        userService.deleteUser(id);
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        writer.println("<html><body>");
        writer.println("Пользователь с идентификатором: " + id + " удален");
        writer.println("</body></html>");
        writer.close();

    }
}
