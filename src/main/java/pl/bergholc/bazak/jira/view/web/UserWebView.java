package pl.bergholc.bazak.jira.view.web;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.service.LoggerManager;
import pl.bergholc.bazak.jira.view.UserView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserWebView implements UserView {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Logger logger = LoggerManager.getApplicationLogger();

    public UserWebView(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public String getData(String attribute) {
        return request.getParameter(attribute);
    }
}
