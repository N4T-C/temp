package com;

import java.io.PrintWriter;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) {

        String name = request.getParameter("name");
        response.setContentType("text/html");

        try {
            PrintWriter out = response.getWriter();
            out.println("<h3>Hello " + name + "!!</h3>");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
