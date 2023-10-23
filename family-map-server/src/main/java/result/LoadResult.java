package main.java.result;

/**
 * Contains information about the request to load.
 * Returns a message about whether or not load was successful.
 */
public class LoadResult {
    /**
     * Default constructor
     */
    public LoadResult() {
        success = false;
    }

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