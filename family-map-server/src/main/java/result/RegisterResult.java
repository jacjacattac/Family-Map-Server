package main.java.result;

/**
 * Contains information about the request to register.
 * Returns a message of whether or not request to register was successful
 */
public class RegisterResult {
    private String authtoken;
    private String username;
    private String personID;
    private String message;
    private Boolean success;

    /**
     * Default constructor
     */
    public RegisterResult(String error) {
        message = "Error: " + error;
        success = false;
    }

    /**
     * Initializing the result object
     * @param login
     */
    public RegisterResult(LoginResult login) {
        authtoken = login.getAuthtoken();
        username = login.getUsername();
        personID = login.getPersonID();
        success = login.getSuccess();
    }

    public void setMessage(String m) {
        message = m;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setSuccess(Boolean s) {
        success = s;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setAuthtoken(String at) {
        authtoken = at;
    }

    public void setPersonID(String s){
        personID = s;
    }

    public String getPersonID() { return personID; }

    public void setUsername(String u){
        username = u;
    }

    public String getUsername(){
        return username;
    }

}