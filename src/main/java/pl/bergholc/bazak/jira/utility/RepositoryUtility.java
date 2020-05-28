package pl.bergholc.bazak.jira.utility;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.service.LoggerManager;
import pl.bergholc.bazak.jira.persistence.Persistable;

import java.util.ArrayList;
import java.util.List;

public class RepositoryUtility {

    public static <T extends Persistable> List<T> castFromPersistable(List<Persistable> persistables) throws ClassCastException{
        Logger logger = LoggerManager.getApplicationLogger();
        logger.trace("castFromPersistable");
        List<T> projects = new ArrayList<>();
        for (Persistable project : persistables) {
            projects.add((T ) project);
        }
        return projects;
    }

}
