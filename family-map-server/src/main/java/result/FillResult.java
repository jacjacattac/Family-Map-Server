package main.java.result;

/**
 * Contains information about the fill request.
 * Returns a message about whether or not the fill request was a success.
 */
public class FillResult {
    private String message;
    private Boolean success;

    public FillResult() {
        message = "Error: there was an error.";
        success = false;
    }

    public FillResult(String successMessage) {
        message = successMessage;
        success = true;
    }

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

