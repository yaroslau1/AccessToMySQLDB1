package com.work.servlets;

import com.work.DAO.CityConnectDAO;
import com.work.entity.City;
import com.work.exeption.DAOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Actual logic goes here.
        PrintWriter out = resp.getWriter();
        out.println("<h1>" + "HELLO" + "</h1>");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        City city = new City();
        String cityName = req.getParameter("cityName");
        int population = Integer.parseInt(req.getParameter("population"));
        String countryCode = req.getParameter("countryCode");
        PrintWriter out = resp.getWriter();

        city.setName(cityName);
        city.setPopularion(population);
        city.setCountryCode(countryCode);

        try (CityConnectDAO cityConnectDAO  = new CityConnectDAO()){
            cityConnectDAO.insert(city);

            out.println("<h2>" + cityConnectDAO.getAll() + "</h2>");

        } catch (DAOException e) {
            e.printStackTrace();
        }

        out.println("<a href=\"" + req.getServletContext().getContextPath() + "/index.html\">Go To Index Page</a>");
        //PrintWriter out = resp.getWriter();
       // out.println("<h2>" + cityName + "</h2>");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
