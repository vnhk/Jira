package pl.bergholc.bazak.jira.service;

public interface InfoService {
    void error(String message);
    void warning(String message);
    void info(String message);
}
