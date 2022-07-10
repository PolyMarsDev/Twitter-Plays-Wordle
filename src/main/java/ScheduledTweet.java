import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;

import java.util.*;

public class ScheduledTweet extends TimerTask {

    private final Bot bot;

    public ScheduledTweet(Bot bot){
        this.bot = bot;
    }
    @Override
    public void run() {
        Status tweet = this.bot.getLatestTweet(this.bot.twitter);
        Status topReply = null;
        int topLikes = -1;
        for (Status t : this.getReplies(Bot.USERNAME, tweet.getId()))
        {
            if (t.getText() != null && this.isValidGuess(t.getText().split(" ")[1].toLowerCase()) && t.getFavoriteCount() > topLikes)
            {
                topLikes = t.getFavoriteCount();
                topReply = t;
            }
        }

        if (topReply != null)
            this.bot.sendTweet(this.bot.gameManager.run(topReply.getText().split(" ")[1].toLowerCase()), this.bot.twitter);
        else
            this.bot.sendTweet(this.bot.gameManager.run("skip"), this.bot.twitter);
    }

    private boolean isValidGuess(String word)
    {
        List<String> words = GameManager.getWordList();
        return word.length() == 5 && words.contains(word);
    }

    public List<Status> getReplies(String screenName, long tweetID) {
        List<Status> replies = new ArrayList<>();

        try {
            Query query = new Query("to:" + screenName + " since_id:" + tweetID);
            QueryResult results;

            do {
                results = this.bot.twitter.search(query);
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
