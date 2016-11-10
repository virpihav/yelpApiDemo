package fi.virpihav.yelpdemo.model;

public class Token {

    private String access_token;
    private String token_type;
    private String expires_in;

    public String getToken() {
        return access_token;
    }

    public String getTokenType() {
        return token_type;
    }

    public String getExpiresIn() {
        return expires_in;
    }
}
