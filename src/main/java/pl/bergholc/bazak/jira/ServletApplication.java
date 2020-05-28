package pl.bergholc.bazak.jira;


import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.config.Config;
import pl.bergholc.bazak.jira.exception.DatabaseConnectionException;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.service.AccessService;
import pl.bergholc.bazak.jira.service.LoggerManager;
import pl.bergholc.bazak.jira.service.WebSession;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/index.html")
public class ServletApplication extends HttpServlet {
    private static Logger logger = LoggerManager.getApplicationLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doOperation(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        doOperation(request, response);
    }

    private boolean isActionSelected(Action action, String actionName) {
        return action.getClass().getSimpleName().toLowerCase().equals(actionName + "action");
    }

    private void doOperation(HttpServletRequest request, HttpServletResponse response) {
        logger.trace("servlet started");
        try {
            Config config = new Config().withSqlPersistence().withWebView(request, response);

            String actionName = request.getParameter("action");
            WebSession session = (WebSession) config.getSession();

            if (actionName == null) {
                actionName = "showprojects";
            }

            if (!session.isLogged()) {
                if (!actionName.equals("login")) {
                    actionName = "displaylogin";
                }
            } else {
                try {
                    AccessService access = config.getAccessService();
                    if ((access.isAdmin(session.getUserId()))) {
                        request.setAttribute("adminActions", config.getOnlyAdminActions());
                    }
                } catch (PersistenceException e) {
                    e.printStackTrace();
                }
            }
            for (Action action : config.getActions()) {
                if (isActionSelected(action, actionName)) {
                    action.execute();
                }
            }
            config.closeConnection();
        } catch (DatabaseConnectionException e) {
            logger.info("Access denied for database");
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
