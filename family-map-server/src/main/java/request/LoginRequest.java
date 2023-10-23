package main.java.request;

/**
 * Submits request to login
 */
public class LoginRequest {
    /**
     * Default constructor
     */
    public LoginRequest() {}

    /**
     * Initializing information of request object
     * @param u
     * @param p
     */
    public LoginRequest(String u, String p) {
        username = u;
        password = p;
    }
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
