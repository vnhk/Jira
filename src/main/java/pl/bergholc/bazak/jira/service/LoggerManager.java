package pl.bergholc.bazak.jira.service;

import org.apache.log4j.Logger;

public class LoggerManager {
    private static Logger applicationLogger = Logger.getLogger("Application");

    public static Logger getApplicationLogger(){
        return applicationLogger;
    }
}
