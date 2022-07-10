import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.*;

public class Bot
{
	public final boolean DEBUG = false; //in debug mode, console messages are sent instead of tweets
	public static final String USERNAME = "REPLACE_WITH_USERNAME";
	private final String CONSUMER_KEY = "REPLACE_WITH_CONSUMER_KEY";
	private final String CONSUMER_SECRET = "REPLACE_WITH_OAUTH_CONSUMER_SECRET";
	private final String ACCESS_TOKEN = "REPLACE_WITH_OATH_ACCESS_TOKEN";
	private final String ACCESS_TOKEN_SECRET = "REPLACE_WITH_OATH_ACCESS_TOKEN_SECRET";

	public final Twitter twitter = this.getTwitterInstance();

	public final GameManager gameManager = new GameManager();

	public Bot()
	{
		if(this.DEBUG)
		{
			System.out.println(this.gameManager.display() + "\nReply with a 5-letter guess!");
			while(true)
			{
				Scanner scan = new Scanner(System.in);
				System.out.println(this.gameManager.run(scan.nextLine()));
			}
		}
		else
		{
			this.sendTweet(this.gameManager.display() + "\nReply with a 5-letter guess!", this.twitter);
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new ScheduledTweet(this), 60000, 60000);
		}
	}

	public void sendTweet(String line, Twitter twitter)
	{
		try
		{
			twitter.updateStatus(line);
		} catch(TwitterException e)
		{
			e.printStackTrace();
		}
	}

	public Status getLatestTweet(Twitter twitter)
	{
		try
		{
			return twitter.getUserTimeline("@" + USERNAME).get(0);
		} catch(TwitterException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private Twitter getTwitterInstance()
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey(this.CONSUMER_KEY)
				.setOAuthConsumerSecret(this.CONSUMER_SECRET)
				.setOAuthAccessToken(this.ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(this.ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf.getInstance();
	}

	public static void main(String[] args)
	{
		new Bot();
	}
}
