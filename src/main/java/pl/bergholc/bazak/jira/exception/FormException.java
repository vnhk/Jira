package pl.bergholc.bazak.jira.exception;

public class FormException extends Exception {
    private String message;

    public FormException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message +": Incorrect data. Form can't be empty or start with spaces.";
    }
}
