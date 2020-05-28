package pl.bergholc.bazak.jira.service;

public class ConsoleSession implements SessionService {
    private final int USER_LOGOUT = -1;
    private int userId = USER_LOGOUT;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean isLogged() {
        return userId != USER_LOGOUT;
    }

    @Override
    public void clear() {
        userId = USER_LOGOUT;
    }

    @Override
    public void setUserLogin(String login) {

    }

    @Override
    public String getUserLogin() {
        return null;
    }
}
