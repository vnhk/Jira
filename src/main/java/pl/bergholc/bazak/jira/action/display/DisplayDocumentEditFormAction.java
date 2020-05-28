package pl.bergholc.bazak.jira.action.display;

import pl.bergholc.bazak.jira.action.Action;
import pl.bergholc.bazak.jira.exception.PersistenceException;
import pl.bergholc.bazak.jira.model.Document;
import pl.bergholc.bazak.jira.model.Role;
import pl.bergholc.bazak.jira.repository.DocumentRepository;
import pl.bergholc.bazak.jira.service.InfoService;
import pl.bergholc.bazak.jira.view.web.DisplayFormView;

import java.util.Arrays;
import java.util.List;

public class DisplayDocumentEditFormAction implements Action {
    private DisplayFormView displayFormView;
    private DocumentRepository documentRepository;
    private InfoService info;

    public DisplayDocumentEditFormAction(DisplayFormView displayFormView, DocumentRepository documentRepository, InfoService info) {
        this.displayFormView = displayFormView;
        this.documentRepository = documentRepository;
        this.info = info;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public void execute() {
        try {
            int documentId = Integer.parseInt((String) displayFormView.getParameter("documentId"));
            Document document = documentRepository.findById(documentId);
            displayFormView.setAttribute("document", document);
            displayFormView.display("/editDocumentForm.jsp");
        } catch (PersistenceException e) {
            info.error(e.getMessage());
        }
    }

    @Override
    public boolean onlyAdminAccess() {
        return false;
    }

    @Override
    public List<Role> getAllowedRoles() {
        return Arrays.asList(Role.EMPLOYEE, Role.MANAGER);
    }
}


