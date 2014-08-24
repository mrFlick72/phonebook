package it.valeriovaudi.security;

/**
 * Created by Valerio on 22/08/2014.
 */
public enum PhoneBookSecurityRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String role;

    PhoneBookSecurityRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
