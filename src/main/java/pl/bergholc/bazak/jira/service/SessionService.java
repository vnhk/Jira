package pl.bergholc.bazak.jira.service;

public interface SessionService {
    int getUserId();
    void setUserId(int userId);
    boolean isLogged();
    void clear();

    void setUserLogin(String login);
    String getUserLogin();

}
