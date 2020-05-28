package pl.bergholc.bazak.jira.service;


import javax.servlet.http.HttpSession;

public class WebSession implements SessionService {
    private HttpSession session;

    public WebSession(HttpSession session) {
        this.session = session;
        session.getId();
    }

    @Override
    public int getUserId() {
        return (Integer) session.getAttribute("id");
    }

    @Override
    public void setUserId(int userId) {
        session.setAttribute("id", userId);
    }

    public boolean isLogged() {
        if (session.getAttribute("id") == null) {
            return false;
        }
        return true;
    }


    @Override
    public void clear() {
        session.setAttribute("id", null);
    }

    @Override
    public String getUserLogin() {
        return (String) session.getAttribute("login");
    }

    @Override
    public void setUserLogin(String login) {
        session.setAttribute("login", login);
    }
}
