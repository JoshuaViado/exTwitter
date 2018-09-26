package sample;

import java.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import twitter4j.*;
import javax.print.DocFlavor;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller extends Application{

    static BuildTwitterAccess buildTwitterAccess;

    static Authenticate authenticate;

    private Trend[] trend_list;

    //For Access-login page

    @FXML
    private PasswordField oAuthConsumerKeyField;

    @FXML
    private PasswordField oAuthConsumerSecretField;

    @FXML
    private PasswordField oAuthAccessTokenField;

    @FXML
    private PasswordField oAuthAccessTokenSecretField;

    @FXML
    private Label wrongPassword;

    @FXML
    TextFlow trend_container;

    @FXML
    Label trendTitle;

    @FXML
    TextField searchKeywordField;

    @FXML
    Label keywordLabel;

    @FXML
    Label keySearched;

    @FXML
    ProgressBar progressTweet;

    @FXML
    Label liveTweetTitle;

    @FXML
    Label liveTweetLabel;

    @FXML
    VBox liveTweets;

    @FXML
    Label liveFeedTitle;

    //Location Side

    @FXML
    TextField latField;

    @FXML
    TextField longField;

    @FXML
    Label userLabel;

    @FXML
    TilePane profileImageView;

    @FXML
    Label liveLocationLabel;

    @FXML
    VBox liveLocationFeed;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("access.fxml"));
        primaryStage.setTitle("exTwitter");
        primaryStage.setScene(new Scene(root, 850, 400));
        primaryStage.show();
    }

    //For Login-Access page

    public void login (javafx.event.ActionEvent event) throws Exception{

        authenticate = new Authenticate(oAuthConsumerKeyField.getText(), oAuthConsumerSecretField.getText(),
                oAuthAccessTokenField.getText(), oAuthAccessTokenSecretField.getText());

        buildTwitterAccess = new BuildTwitterAccess(authenticate);

        try {
            User user = buildTwitterAccess.twitter.verifyCredentials();

            Stage loginStage = (Stage) oAuthAccessTokenField.getScene().getWindow();
                loginStage.close();

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
                primaryStage.setTitle("exTwitter");
                primaryStage.setScene(new Scene(root, 1280, 720));
                primaryStage.show();

                Platform.setImplicitExit(true);

        } catch (TwitterException e) {
            //e.printStackTrace();
            System.out.println("access denied");
            oAuthAccessTokenField.clear();
            oAuthAccessTokenSecretField.clear();
            oAuthConsumerKeyField.clear();
            oAuthConsumerSecretField.clear();
            wrongPassword.setVisible(true);
        }

    }

    public void helpAuthenticate (javafx.event.ActionEvent event)  {
        getHostServices().showDocument("https://developer.twitter.com/en/docs/basics/authentication/guides/access-tokens.html");
    }

    //For Main page

    public void selectFlag(MouseEvent event) {
        String id = event.getPickResult().getIntersectedNode().getId();
        switch (id) {
            case "ukFlag":
                
                System.out.println("its the uk flag");
                trendTitle.setText("U.K");
                getTrendsByCountry(23424829);
                displayTrends(trend_list);
                break;
            case "franceFlag":
                System.out.println("its the france flag");
                trendTitle.setText("France");
                getTrendsByCountry(23424819);
                displayTrends(trend_list);
                break;
            case "usFlag":
                System.out.println("its the US flag");
                trendTitle.setText("U.S.A");
                getTrendsByCountry(23424977);
                displayTrends(trend_list);
                break;
            case "spainFlag":
                System.out.println("its the Spain flag");
                trendTitle.setText("Spain");
                getTrendsByCountry(23424950);
                displayTrends(trend_list);
                break;
            case "germanyFlag":
                System.out.println("its the Germany flag");
                trendTitle.setText("Germany");
                getTrendsByCountry(23424829);
                displayTrends(trend_list);
                break;
            case "phFlag":
                System.out.println("its the Philippines flag");
                trendTitle.setText("Philippines");
                getTrendsByCountry(23424934);
                displayTrends(trend_list);
                break;
        }

    }

    public void getTrendsByCountry(int oeid) {

        try {
            trend_list =  buildTwitterAccess.getTwitter().getPlaceTrends(oeid).getTrends();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public void displayTrends(Trend[] trends) {
        trend_container.getChildren().clear();

        for(Trend trend : trends) {
            Label t = new Label(trend.getName());
            t.setStyle("-fx-text-fill: #A9CAD8;");
            t.setPadding(new Insets(30, 30,30,30));
            trend_container.getChildren().add(t);
        }
    }

    public void searchKeyword(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {

            progressTweet.setProgress(0.0);
            buildTwitterAccess.twitterStream.clearListeners();
            liveTweets.getChildren().removeAll();

            keywordLabel.setVisible(true);
            progressTweet.setVisible(true);
            liveTweetTitle.setVisible(true);
            liveFeedTitle.setVisible(true);

            keySearched.setText(searchKeywordField.getText());

            KeywordStatusListener myStatusListener = new KeywordStatusListener();
            buildTwitterAccess.twitterStream.addListener(myStatusListener);
            FilterQuery twitterFilterQuery = new FilterQuery();
            twitterFilterQuery.language("en");
            twitterFilterQuery.track(searchKeywordField.getText());
            buildTwitterAccess.twitterStream.filter(twitterFilterQuery);
            searchKeywordField.clear();
        }

    }

    public void searchLocation() {

        buildTwitterAccess.twitterStream2.clearListeners();

        userLabel.setVisible(true);
        liveLocationLabel.setVisible(true);

        LocationStatusListener locationStatusListener = new LocationStatusListener();
        buildTwitterAccess.twitterStream2.addListener(locationStatusListener);
        FilterQuery locationFilterQuery = new FilterQuery();
        locationFilterQuery.language("en");

        Double lat = Double.parseDouble(latField.getText());
        Double longi = Double.parseDouble(longField.getText());

        locationFilterQuery.locations(new double[]{lat - 5, longi - 5}, new double[]{lat, longi});

        buildTwitterAccess.twitterStream2.filter(locationFilterQuery);
    }

    public void longLangLink (javafx.event.ActionEvent event)  {
        getHostServices().showDocument("https://www.latlong.net");
    }

    public class KeywordStatusListener implements StatusListener {

        Double liveTweetProgress = 0.0;
        int tweetCount = 0;
        @Override
        public void onStatus(Status status) {
            progressTweet.setProgress(liveTweetProgress++/1000);
            tweetCount++;
            if (!status.isRetweet()) {
                Label ltweet = new Label(status.getText());
                ltweet.setStyle("-fx-text-fill: #A9CAD8;");
                ltweet.setPadding(new Insets(0, 0,10,0));
                Platform.runLater(() ->liveTweets.getChildren().add(0, ltweet));
                if(liveTweets.getChildren().size() >= 10) {
                Platform.runLater(() ->liveTweets.getChildren().remove(10));
                }
            }

            //System.out.println(tweetCount);
            //needed to add this because fxlabel can only be updated in the application thread
            //..... not in the background like in a java listener
            Platform.runLater(() -> liveTweetLabel.setText(Integer.toString(tweetCount)));

        }

        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

        }

        @Override
        public void onTrackLimitationNotice(int i) {

        }

        @Override
        public void onScrubGeo(long l, long l1) {

        }

        @Override
        public void onStallWarning(StallWarning stallWarning) {

        }

        @Override
        public void onException(Exception e) {

        }
    }

    public class LocationStatusListener implements StatusListener {



        @Override
        public void onStatus(Status status) {
            User user = status.getUser();
            MyImageView profile= new MyImageView(user.getProfileImageURL(), user.getScreenName());
            javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView();
            imageView.setImage(profile);
            imageView.setStyle("-fx-effect: dropshadow(two-pass-box, #1F395F, 0, 0, 0, 0);");

            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                boolean isSelected = false;

                @Override
                public void handle(MouseEvent event) {

                    javafx.scene.image.ImageView profile = (javafx.scene.image.ImageView)event.getSource();
                    User usr = status.getUser();

                    if(!isSelected) {
                        isSelected = true;
                        profile.setStyle("-fx-effect: dropshadow(two-pass-box, #A9CAD8, 10, 0.5, 0, 0);");
                        System.out.println("its selected");


//                        for(Node pf : profileImageView.getChildren()) {
//                            ImageView img = (ImageView) pf;
//                            try {
//                                if(img != profile && !buildTwitterAccess.twitter.showFriendship(user.getScreenName(), ((MyImageView)img.getImage()).description).isSourceFollowedByTarget()) {
//                                    img.setVisible(false);
//                                }
//                            } catch (TwitterException e) {
//                                e.printStackTrace();
//                            }
//                        }

                    } else {
                        isSelected = false;
                        profile.setStyle("-fx-effect: dropshadow(two-pass-box, #1F395F, 0, 0, 0, 0);");
                        System.out.println("its unselected");

//                        for(Node pf : profileImageView.getChildren()) {
//                            pf.setVisible(true);
//                        }
                    }
                    //event.consume();
                }

                public boolean isSelected() {
                    return isSelected;
                }
            });
            Platform.runLater(() -> profileImageView.getChildren().add(imageView));



                Label ltweet = new Label(status.getText());
                ltweet.setStyle("-fx-text-fill: #A9CAD8;");
                ltweet.setPadding(new Insets(0, 0,10,0));
                Platform.runLater(() ->liveLocationFeed.getChildren().add(0, ltweet));
                if(liveLocationFeed.getChildren().size() >= 5) {
                    Platform.runLater(() ->liveLocationFeed.getChildren().remove(5));
                }

        }

        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

        }

        @Override
        public void onTrackLimitationNotice(int i) {

        }

        @Override
        public void onScrubGeo(long l, long l1) {

        }

        @Override
        public void onStallWarning(StallWarning stallWarning) {

            System.out.println("its loading");
        }

        @Override
        public void onException(Exception e) {

        }
    }

    public class ImageMouseListener implements MouseListener {
        boolean isSelected = false;


        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
            javafx.scene.image.ImageView profile = (javafx.scene.image.ImageView)e.getSource();

            if(!isSelected) {
                isSelected = true;
                profile.setStyle("-fx-border-color: #A9CAD8;");
            } else {
                isSelected = false;
                profile.setStyle("-fx-border-color: transparent;");
            }

        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {

        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {

        }
    }



    //Main

    public static void main(String[] args) {
        launch(args);
    }


}
class MyImageView extends Image{

    String description;

    public MyImageView(String url, String description) {
        super(url);
        this.description = description;
    }
}