import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;

import java.util.*;

public class ScheduledTweet extends TimerTask {

    @Override
    public void run() {
        Status tweet = Bot.getLatestTweet(Bot.twitter);
        Status topReply = null;
        int topLikes = -1;
        for (Status t : getReplies(Bot.USERNAME, tweet.getId()))
        {
            if (t.getText() != null && isValidGuess(t.getText().split(" ")[1].toLowerCase()) && t.getFavoriteCount() > topLikes)
            {
                topLikes = t.getFavoriteCount();
                topReply = t;
            }
        }
        if (topReply != null)
        {
            Bot.sendTweet(Bot.gameManager.run(topReply.getText().split(" ")[1].toLowerCase()), Bot.twitter);
        }
        else
        {
            Bot.sendTweet(Bot.gameManager.run("skip"), Bot.twitter);
        }
    }

    boolean isValidGuess(String word)
    {
        List<String> words = GameManager.getWordList();
        if (word.length() != 5 || !words.contains(word)) return false;
        return true;
    }

    public ArrayList<Status> getReplies(String screenName, long tweetID) {
        ArrayList<Status> replies = new ArrayList<Status>();

        try {
            Query query = new Query("to:" + screenName + " since_id:" + tweetID);
            QueryResult results;

            do {
                results = Bot.twitter.search(query);
                System.out.println("Results: " + results.getTweets().size());
                List<Status> tweets = results.getTweets();

                for (Status tweet : tweets)
                    if (tweet.getInReplyToStatusId() == tweetID)
                        replies.add(tweet);
            } while ((query = results.nextQuery()) != null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return replies;
    }

}
