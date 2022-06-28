# Twitter Plays Wordle
 
Twitter Plays Wordle is a Twitter bot written with [Twitter4J](https://github.com/Twitter4J/Twitter4J) that allows users to play a collaborative game of [Wordle](https://www.nytimes.com/games/wordle/index.html) using replies. 

The code was written in 3 hours, so it isn't perfect! To see the current bugs, go to [Known Issues](#known-issues).

## How It Works

- For each turn, the bot sends out a tweet of the current game status.
- The top reply to the tweet is used as the guess for the next turn.
- The round ends after 6 turns or once the correct word is guessed.

## Screenshots
![](https://cdn.discordapp.com/attachments/583714435458400291/991129981306548335/unknown.png)
![](https://cdn.discordapp.com/attachments/583714435458400291/991130048155353199/unknown.png)

## Known Issues

- The game does not properly reset after a round is finished.

## Usage
### Self-hosting
Replace the constants in ``Bot.java`` with your bot's username, save directory, and respective keys/tokens from the [Twitter Developer Portal](https://developer.twitter.com/en/dashboard). Ensure your IDE has Maven support (ex: [IntelliJ IDEA](https://www.jetbrains.com/idea/)), and execute ``mvn clean install`` in the project directory.

## Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.
