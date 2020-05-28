package pl.bergholc.bazak.jira.view.web;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.service.LoggerManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DisplayFormView {
    private Logger logger = LoggerManager.getApplicationLogger();
    private HttpServletRequest request;
    private HttpServletResponse response;

    public DisplayFormView(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void display(String site) {
        try {
            request.getRequestDispatcher(site).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public Object getParameter(String attribute){
        return request.getParameter(attribute);
    }
    public void setAttribute(String attributeName, Object attribute){
        request.setAttribute(attributeName,attribute);
    }


}
