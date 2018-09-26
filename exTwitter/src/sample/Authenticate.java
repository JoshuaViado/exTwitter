package sample;

public class Authenticate {
    private String OAuthConsumerKey, OAuthConsumerSecret, OAuthAccessToken, OAuthAccessTokenSecret;

    Authenticate() {

    }

    Authenticate(String OAuthConsumerKey, String OAuthConsumerSecret, String OAuthAccessToken, String OAuthAccessTokenSecret) {
        this.OAuthConsumerKey = OAuthConsumerKey;
        this.OAuthConsumerSecret = OAuthConsumerSecret;
        this.OAuthAccessToken = OAuthAccessToken;
        this.OAuthAccessTokenSecret = OAuthAccessTokenSecret;
    }

    public String getOAuthConsumerKey() {
        return OAuthConsumerKey;
    }

    public void setOAuthConsumerKey(String OAuthConsumerKey) {
        this.OAuthConsumerKey = OAuthConsumerKey;
    }

    public String getOAuthConsumerSecret() {
        return OAuthConsumerSecret;
    }

    public void setOAuthConsumerSecret(String OAuthConsumerSecret) {
        this.OAuthConsumerSecret = OAuthConsumerSecret;
    }

    public String getOAuthAccessToken() {
        return OAuthAccessToken;
    }

    public void setOAuthAccessToken(String OAuthAccessToken) {
        this.OAuthAccessToken = OAuthAccessToken;
    }

    public String getOAuthAccessTokenSecret() {
        return OAuthAccessTokenSecret;
    }

    public void setOAuthAccessTokenSecret(String OAuthAccessTokenSecret) {
        this.OAuthAccessTokenSecret = OAuthAccessTokenSecret;
    }
}
