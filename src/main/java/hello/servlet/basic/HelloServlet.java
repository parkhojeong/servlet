package hello.servlet.basic;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Iterator;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("req = " + req);
        System.out.println("resp = " + resp);
        Iterator<String> iterator = req.getParameterNames().asIterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            System.out.println(name + " = " + req.getParameter(name));
        }

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println("Hello Servlet " + req.getParameter("name"));

        System.out.println("this.hashCode() = " + this.hashCode());
        System.out.println("req.hashCode() = " + req.hashCode());
    }
}
