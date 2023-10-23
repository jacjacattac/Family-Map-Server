package main.java.model;

import java.util.Objects;

/** Creates object for everything in the authtoken database tables.
 * Models the information in the database.
 */
public class AuthToken {
    private String authtoken;
    private String username;

    public AuthToken(String authtoken, String username){
        this.authtoken = authtoken;
        this.username = username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(authtoken, authToken.authtoken) &&
                Objects.equals(username, authToken.username);
    }
}
