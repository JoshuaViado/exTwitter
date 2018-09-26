package sample;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;


public class BuildTwitterAccess {

    ConfigurationBuilder configBuilder = new ConfigurationBuilder();
    ConfigurationBuilder configBuilderStream = new ConfigurationBuilder();
    ConfigurationBuilder configBuilderStream2 = new ConfigurationBuilder();
    TwitterFactory twitterFactory;
    Twitter twitter;
    TwitterStream twitterStream;
    TwitterStream twitterStream2;
    BuildTwitterAccess() {

    }

    BuildTwitterAccess(Authenticate authenticate) {
        configBuilder.setOAuthConsumerKey(authenticate.getOAuthConsumerKey());
        configBuilder.setOAuthConsumerSecret(authenticate.getOAuthConsumerSecret());
        configBuilder.setOAuthAccessToken(authenticate.getOAuthAccessToken());
        configBuilder.setOAuthAccessTokenSecret(authenticate.getOAuthAccessTokenSecret());

        configBuilderStream.setOAuthConsumerKey(authenticate.getOAuthConsumerKey());
        configBuilderStream.setOAuthConsumerSecret(authenticate.getOAuthConsumerSecret());
        configBuilderStream.setOAuthAccessToken(authenticate.getOAuthAccessToken());
        configBuilderStream.setOAuthAccessTokenSecret(authenticate.getOAuthAccessTokenSecret());

        configBuilderStream2.setOAuthConsumerKey(authenticate.getOAuthConsumerKey());
        configBuilderStream2.setOAuthConsumerSecret(authenticate.getOAuthConsumerSecret());
        configBuilderStream2.setOAuthAccessToken(authenticate.getOAuthAccessToken());
        configBuilderStream2.setOAuthAccessTokenSecret(authenticate.getOAuthAccessTokenSecret());

        twitterFactory = new TwitterFactory(configBuilder.build());
        twitter = twitterFactory.getInstance();
        twitterStream = new TwitterStreamFactory(configBuilderStream.build()).getInstance();
        twitterStream2 = new TwitterStreamFactory(configBuilderStream2.build()).getInstance();

    }

    public Twitter getTwitter() {
        return twitter;
    }

}
