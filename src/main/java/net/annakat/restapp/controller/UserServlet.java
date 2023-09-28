package net.annakat.restapp.controller;

import net.annakat.restapp.model.Event;
import net.annakat.restapp.model.User;
import net.annakat.restapp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
  private final UserService service = new UserService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        String docType = "<!DOCTYPE html>";

        writer.println(docType + "<html><head><title>" + "User Info" + "</title></head><body>");
        writer.println("<h1>USER DATA</h1>");
        writer.println("<br/>");
        User user = service.getUser(Integer.valueOf(request.getParameter("id")));
        String name = user.getName();
        List<Event> events = user.getEvents();
        writer.println("User name: " + name);
        writer.println("User events: " + events);
        writer.println("</body></html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String events = request.getParameter("events");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
