package main.java.result;

/**
 * Contains information about the clear result.
 * Returns a message about whether or not the request to clear was successful.
 */
public class ClearResult {
    private String message;

    private Boolean success;

    public void setMessage(String m) {
        message = m;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(Boolean s) {
        success = s;
    }

    public Boolean getSuccess() {
        return success;
    }
}
