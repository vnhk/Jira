package pl.bergholc.bazak.jira.view.console;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.model.Document;
import pl.bergholc.bazak.jira.service.ConsoleTextManager;
import pl.bergholc.bazak.jira.service.LoggerManager;
import pl.bergholc.bazak.jira.view.DocumentView;

import java.util.List;

public class DocumentConsoleView implements DocumentView {

    private Logger logger = LoggerManager.getApplicationLogger();
    private ConsoleTextManager consoleTextManager = new ConsoleTextManager();

    @Override
    public void display(List<Document> documents) {
        logger.trace("display");
        for (Document document : documents) {
            display(document);
        }
    }

    public String getData(String message) {
        return consoleTextManager.loadFromConsole(message);
    }

    @Override
    public int getDocumentId() {
        return consoleTextManager.getInt("Set id of Document");
    }

    @Override
    public void display(Document document) {
        logger.trace("display");
        System.out.println(document);
    }

    @Override
    public int getProjectId() {
        logger.trace("getProjectId");
        return consoleTextManager.getInt("Set id of Project");
    }

    @Override
    public int getDocumentId(List<Document> documents) {
        logger.trace("displayAndGetId");
        for (int i = 0; i < documents.size(); i++) {
            System.out.println(i + " : " + documents.get(i).getTitle());
            System.out.println("----------------");
        }
        return consoleTextManager.getInt("Set number of Document");
    }
}
