package org.progettol.webserver;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;
import org.progettol.webserver.beans.User;

import centralsystem.Stub;
import items.Product;
import items.Sale;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Calendar;
import machineonline.TicketOnline;
import singleton.JSONOperations;

/**
 * Servlet implementation class WebServer
 */
@Path("/web/*")
public class WebServer extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = (String) request.getParameter("action");
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");

        System.out.println(" @ azione: " + action);
        switch (action) {
            case "login":
                if (user == null) {
                    if (Stub.getInstance().userLogin(request.getParameter("username"), request.getParameter("password"))) {
                        user = new User(request.getParameter("username"));
                        session.setAttribute("user", user);
                        session.setAttribute("result", "true");
                        newRequest(request, response, "/profile.jsp");
                    } else {
                        session.setAttribute("result", "false");
                        newRequest(request, response, "/login.jsp");
                    }
                } else {
                    session.setAttribute("result", "true");
                    response.sendRedirect("/login.jsp");
                }
                break;
            case "buy":
                if (user != null) {
                    //TODO verificare input delle tipologie di biglietti
                    boolean addSaleResult = TicketOnline.getInstance().makeSale(user.getUsername(), request.getParameter("cardNumber"), request.getParameter("ticketType"));
                    if (addSaleResult) {
                        session.setAttribute("result", "true");
                        newRequest(request, response, "/buy.jsp");
                    } else {
                        session.setAttribute("result", "false");
                        newRequest(request, response, "/buy.jsp");
                    }
                } else {
                    response.sendRedirect("/login.jsp");
                }

                break;
            case "registration":
                String result = Stub.getInstance().createUser(request.getParameter("name"), request.getParameter("surname"), request.getParameter("cf"), request.getParameter("username"), request.getParameter("password"), request.getParameter("email"));
                if (JSONOperations.getInstance().booleanParser(result)) {
                    session.setAttribute("result", "true");
                    newRequest(request, response, "/profile.jsp");
                } else {
                    session.setAttribute("result", "false");
                    newRequest(request, response, "/registration.jsp");
                }
                break;
            default:

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = (String) request.getParameter("action");
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");

        System.out.println(" @ azione get : " + action);
        if (action == null) {
            response.sendRedirect("/ticket/");
            return;
        }
        switch (action) {
            case "logout":
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect("/ticket/");
                break;
            case "check":
                String value = request.getParameter("value");
                if (value != null) {
                    session.setAttribute("value", value);
                    newRequest(request, response, "/ticketCheck.jsp");
                } else {
                    PrintWriter out = response.getWriter();
                    out.print("valueError");
                }

                break;
            default:
                PrintWriter out = response.getWriter();
                out.print("ERRORE ACTION");
        }

    }

    //TODO fare pattern COMMAND
    private void newRequest(HttpServletRequest request, HttpServletResponse response, String jspPage)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspPage);
        dispatcher.forward(request, response);
    }
}
