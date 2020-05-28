package pl.bergholc.bazak.jira.view.web;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.exception.AccessException;
import pl.bergholc.bazak.jira.service.LoggerManager;
import pl.bergholc.bazak.jira.view.DocumentView;
import pl.bergholc.bazak.jira.model.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DocumentWebView implements DocumentView {
    private Logger logger = LoggerManager.getApplicationLogger();
    private HttpServletRequest request;
    private HttpServletResponse response;

    public DocumentWebView(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public int getDocumentId(List<Document> documents) {
        int id = Integer.valueOf(request.getParameter("documentId"));
        int iteration = 0;
        for (Document document : documents) {
            if (document.getId() == id) {
                break;
            }
            iteration++;
        }
        return iteration;
    }

    @Override
    public void display(List<Document> document) {
        try {
            request.setAttribute("documents", document);
            request.setAttribute("projectId", request.getParameter("projectId"));
            request.getRequestDispatcher("/documents.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void display(Document document) {
        try {
            request.setAttribute("document", document);
            request.getRequestDispatcher("/document.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error(e.getMessage());
        }
    }


    public Document updateDocument(int userId,int creatorId,int docId) throws AccessException {
        logger.trace("updateDocument");

        if (userId != creatorId) {
            throw new AccessException();
        }

        String topic = request.getParameter("documentTopic");
        String title = request.getParameter("documentTitle");
        String content = request.getParameter("documentContent");
        String description = request.getParameter("documentDescription");

        Document document = new Document();
        document.setCreatorId(userId);
        document.setId(docId);
        document.setContent(content);
        document.setTopic(topic);
        document.setDescription(description);
        document.setTitle(title);

        return document;
    }

    @Override
    public int getDocumentId() {
        return Integer.valueOf(request.getParameter("documentId"));
    }


    public Document createDocument(int userId, int projectId) {
        String topic = request.getParameter("documentTopic");
        String title = request.getParameter("documentTitle");
        String content = request.getParameter("documentContent");
        String description = request.getParameter("documentDescription");

        Document document = new Document();
        document.setProjectId(projectId);
        document.setContent(content);
        document.setTopic(topic);
        document.setDescription(description);
        document.setTitle(title);
        document.setCreatorId(userId);

        return document;
    }

    @Override
    public String getData(String attribute) {
        return request.getParameter(attribute);
    }

    @Override
    public int getProjectId() {
        String result = request.getParameter("projectId");
        if (result == null) {
            return (Integer) (request.getAttribute("projectId"));
        }
        return Integer.valueOf(result);
    }
}
