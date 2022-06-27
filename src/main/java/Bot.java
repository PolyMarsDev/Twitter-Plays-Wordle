import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.*;

public class Bot {

    final static boolean DEBUG = false; //in debug mode, console messages are sent instead of tweets
    final static String USERNAME = "REPLACE_WITH_USERNAME";
    final static String CONSUMER_KEY = "REPLACE_WITH_CONSUMER_KEY";
    final static String CONSUMER_SECRET = "REPLACE_WITH_OAUTH_CONSUMER_SECRET";
    final static String ACCESS_TOKEN = "REPLACE_WITH_OATH_ACCESS_TOKEN";
    final static String ACCESS_TOKEN_SECRET = "REPLACE_WITH_OATH_ACCESS_TOKEN_SECRET";

    static Scanner scan = new Scanner(System.in);
    public static Twitter twitter = getTwitterInstance();

    static GameManager gameManager = new GameManager();
    public static void main(String[] args)
    {

        if (DEBUG)
        {
            System.out.println(gameManager.display() + "\nReply with a 5-letter guess!");
            while (true)
            {
                System.out.println(gameManager.run(scan.nextLine()));
            }
        }
        else
        {
            sendTweet(gameManager.display() + "\nReply with a 5-letter guess!", twitter);
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new ScheduledTweet(), 60000, 60000);
        }
    }

    public static void sendTweet(String line, Twitter twitter) {
        Status status;
        try {
            status = twitter.updateStatus(line);
        } catch (TwitterException e) {;
            e.printStackTrace();
        }
    }
    public static Status getLatestTweet(Twitter twitter) {
        List<Status> statusList = null;

        try {
            statusList = twitter.getUserTimeline("@" + USERNAME);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return statusList.get(0);
    }
    private static Twitter getTwitterInstance()
    {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }
}
