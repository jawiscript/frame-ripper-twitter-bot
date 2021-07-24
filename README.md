<h1 align="center">Frame Ripper Bot</h1>
<h3 align="center">Simple as that, its a bot that rips random frames of a video and posts it on Twitter. </h3>

It's currently working at <a href="https://twitter.com/FramesBot" target="_blank"> **Kimi no Frames** </a> Twitter
account!

<h3>Usage:</h3>
You can download and use the docker image
at <a href="https://hub.docker.com/repository/docker/jawiscript/framesbot" target="_blank">jawiscript/framesbot</a>
DockerHub. To use the docker image you need to do the following things:

- Create a volume called video in the app folder: *app/video*

- Add a .mp4 video to the created volume to get his frames.
    - *NOTE: If there is more than one video the bot uses the first found.*

- Inform the next environment variables to use the twitter api:
    - TWITTER_OAUTH_CONSUMER_KEY = Your auth consumer.
    - TWITTER_OAUTH_CONSUMER_SECRET = Your auth consumer secret.
    - TWITTER_OAUTH_ACCESS_TOKEN = Your auth access token.
    - TWITTER_OAUTH_ACCESS_TOKEN_SECRET = Your auth access token.

- Inform the cron expression to determine the delay between tweets:
    - CRON_FRAME_RIPPER_EXPRESSION = Your cron expression.

<h3 align="left">Languages and Tools used to develop the bot:

JAVA (11.0.12)
<a href="https://www.java.com" target="_blank">
<img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="30" height="30"/>
</a>
SPRING
<a href="https://spring.io/" target="_blank">
<img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="30" height="30"/>
</a>
</h3>

<h3>NOTE: You need your own <a href="https://developer.twitter.com/en/products/twitter-api" targer="_blank">Twitter
developer</a> account to use this bot, because you need to inform the twitter </h3>
