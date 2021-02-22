package kz.edu.nu.cs.exercise;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/myservlet" })
public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ArrayList<String> logsList = new ArrayList<>();

    public MyServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String log = "Time of access: " + LocalTime.now() + " host: " + request.getRemoteHost() + " path: " + request.getContextPath() + "\n";
        logsList.add(log);

        for(int i = 0; i < logsList.size(); i++) {
            response.getWriter().append(logsList.get(i));
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
