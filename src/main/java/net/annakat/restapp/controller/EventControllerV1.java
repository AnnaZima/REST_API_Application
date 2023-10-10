package net.annakat.restapp.controller;

import com.google.gson.Gson;
import net.annakat.restapp.model.Event;
import net.annakat.restapp.service.EventService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/v1/events/*")
public class EventControllerV1 extends HttpServlet {
    private final EventService eventService = new EventService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

        String substring = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1);
        if(!substring.isEmpty()) {
            int id = Integer.parseInt(substring);
            Event event = eventService.getEvent(id);
            String s = gson.toJson(event);
            writer.println(s);
        } else {
            List<Event> all = eventService.getAll();
            String s = gson.toJson(all);
            writer.println(s);
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();

        String substring = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1);
        if (!substring.isEmpty()) {
            int id = Integer.parseInt(substring);
            eventService.deleteEvent(id);
            writer.println("Событие удалено");
        } else {
            writer.println("Проверьте правильность вводимых данных, " +
                    " идентификационный номер события отсутствует ");
        }
        writer.close();
    }
}
