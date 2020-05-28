package pl.bergholc.bazak.jira.exception;

public class PersistenceException extends Exception {
    private String exceptionMessage;
    private String message;

    public PersistenceException(String message, String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        this.message = message;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return exceptionMessage +
                ". " + message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
