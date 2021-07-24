# syntax=docker/dockerfile:1

FROM openjdk:11
MAINTAINER jawiscript "saorindev@gmail.com"
COPY ./target/*.jar /app/kiminoframes.jar
WORKDIR /app

ENV TWITTER_OAUTH_CONSUMER_SECRET=Your auth consumer secret.
ENV TWITTER_OAUTH_CONSUMER_KEY=Your auth consumer.
ENV TWITTER_OAUTH_ACCESS_TOKEN_SECRET=Your auth access token
ENV TWITTER_OAUTH_ACCESS_TOKEN=Your auth access token.
ENV CRON_FRAME_RIPPER_EXPRESSION=Your cron expression.

CMD ["java" "-jar" "./kiminoframes.jar" "--nogui"]

