package main.java.result;

/**
 * Contains information about login result. Returns a message about whether or not the
 * request to login was successful.
 */
public class LoginResult {
    /**
     * Default constructor
     */
    public LoginResult() {
        message = "Error: there was an error.";
        success = false;
    }

    /**
     * Initializing the result object
     * @param a
     * @param u
     * @param p
     */
    public LoginResult(String a, String u, String p) {
        authtoken = a;
        username = u;
        personID = p;
        success = true;
    }
    private String authtoken;
    private String username;
    private String personID;
    private String message;

    private Boolean success;

    public void setAuthtoken(String s) { authtoken = s; }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setUsername(String s) { username = s; }

    public String getUsername() {
        return username;
    }

    public void setPersonID(String s) { personID = s; }

    public String getPersonID() {
        return personID;
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
