<h1 align="center">Frame Ripper Bot</h1>
<h3 align="center">Simple as that, its a bot that rips random frames of a video and posts it on Twitter. </h3>

It's currently working at <a href="https://twitter.com/KimiNoScreens" target="_blank"> **Kimi no Frames** </a> Twitter
account!

<h3>Usage:</h3>

You can download and use the docker image
at <a href="https://hub.docker.com/repository/docker/jawiscript/framesbot" target="_blank">jawiscript/framesbot</a> DockerHub.

Quick run:
```
docker run -d -it \
-v /home/user/video:/app/video \
-e TWITTER_OAUTH_CONSUMER_KEY='consumerkey' \
-e TWITTER_OAUTH_CONSUMER_SECRET='consumersecret' \
-e TWITTER_OAUTH_ACCESS_TOKEN='accesstoken' \
-e TWITTER_OAUTH_ACCESS_TOKEN_SECRET='accesstokensecret' \
--name twitterBot jawiscript/framesbot:latest
```

To use the docker image you need to do the following things:

- Bind the container's path `/app/video` to any folder on your host machine, for example: `/home/user/video`, you'll use this folder to upload the video you want the bot to get the frames from.
- Make sure you upload only one video on your binded folder, and its `.mp4` format.
  - *NOTE: If there is more than one video, the bot will use the first one it finds (Alphabetic order)*

- Set up the following environment variables with your twitter developer api keys:
  - TWITTER_OAUTH_CONSUMER_KEY = Your auth consumer key.
  - TWITTER_OAUTH_CONSUMER_SECRET = Your auth consumer secret.
  - TWITTER_OAUTH_ACCESS_TOKEN = Your auth access token.
  - TWITTER_OAUTH_ACCESS_TOKEN_SECRET = Your auth access token secret.

- Set up the following environment variable with your cron expression to determine the delay between tweets:
  - CRON_FRAME_RIPPER_EXPRESSION = Your cron expression.

<h3 align="left">Languages and Tools used to develop the bot:</h3>

- <a href="https://www.java.com" target="_blank">
    <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="17" height="17"/>
    JAVA (11.0.12)
</a>

- <a href="https://spring.io/" target="_blank">
    <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="17" height="17"/> SPRING
</a>


<h3>NOTE: You need your own <a href="https://developer.twitter.com/en/products/twitter-api" targer="_blank">Twitter
developer</a> account to use this bot, because you need to set up the environment variables with twitter's api keys. </h3>