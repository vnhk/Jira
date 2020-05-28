package pl.bergholc.bazak.jira.service;

public class ConsoleInfo implements InfoService{
    @Override
    public void error(String message){
        System.err.println(message);
    }

    @Override
    public void warning(String message) {
        System.err.println(message);
    }

    @Override
    public void info(String message) {
        System.out.println(message);
    }
}
