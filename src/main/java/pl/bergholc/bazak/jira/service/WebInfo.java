package pl.bergholc.bazak.jira.service;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebInfo implements InfoService {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Logger logger = LoggerManager.getApplicationLogger();

    public WebInfo(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public void error(String message) {
        displayMessage("error", message);
    }

    @Override
    public void warning(String message) {
        displayMessage("warning", message);
    }

    private void displayMessage(String type, String message) {
        request.setAttribute(type, message);
        try {
            request.getRequestDispatcher("/info.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    @Override
    public void info(String message) {
        displayMessage("message", message);
    }
}