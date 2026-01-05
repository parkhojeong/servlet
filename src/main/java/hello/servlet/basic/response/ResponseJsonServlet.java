package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/response-json")
public class ResponseJsonServlet extends HttpServlet {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Content-Type: application/json; charset=UTF-8
        resp.setContentType("application/json");
        // Note. json spec is [Content-Type: application/json] without charset
        // resp.setCharacterEncoding("UTF-8");

        // body
        HelloData helloData = new HelloData();
        helloData.setUsername("hello");
        helloData.setAge(20);
        // Note. getWriter() auto append charset. resp.getOutputStream() doesn't append
        // resp.getWriter().write("{\"username\":\"hello\", \"age\":20}");
        resp.getWriter().write(objectMapper.writeValueAsString(helloData));
    }
}
