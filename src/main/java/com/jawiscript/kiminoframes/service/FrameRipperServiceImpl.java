package com.jawiscript.kiminoframes.service;


import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.DemuxerTrack;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.containers.mp4.demuxer.MP4Demuxer;
import org.jcodec.scale.AWTUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Log4j2
public class FrameRipperServiceImpl implements IFrameRipperService {

    public static final int FIRST_FRAME = 0;
    public static final String AUTH_CONSUMER_KEY = "TWITTER_OAUTH_CONSUMER_KEY";
    public static final String AUTH_CONSUMER_SECRET = "TWITTER_OAUTH_CONSUMER_SECRET";
    public static final String AUTH_ACCESS_TOKEN = "TWITTER_OAUTH_ACCESS_TOKEN";
    public static final String AUTH_ACCESS_TOKEN_SECRET = "TWITTER_OAUTH_ACCESS_TOKEN_SECRET";
    public static final String FILE_EXTENSION_MP4 = "mp4";
    public static final String VIDEO_PATH = "./video";
    public static final String LAST_GENERATED_FRAME_PNG = "last_frame.png";
    public static final String CRON_FRAME_RIPPER_EXPRESSION = "CRON_FRAME_RIPPER_EXPRESSION";

    private String oAuthConsumerKey;
    private String oAuthConsumerSecret;
    private String oAuthAccessToken;
    private String oAuthAccessTokenSecret;

    private Twitter twitter;
    private File dir;
    private File[] files;

    public FrameRipperServiceImpl() {
        Properties props = System.getProperties();
        props.put(CRON_FRAME_RIPPER_EXPRESSION, System.getenv(CRON_FRAME_RIPPER_EXPRESSION));

        this.dir = new File(VIDEO_PATH);
        this.files = dir.listFiles();

        this.oAuthConsumerKey = System.getenv(AUTH_CONSUMER_KEY);
        this.oAuthConsumerSecret = System.getenv(AUTH_CONSUMER_SECRET);
        this.oAuthAccessToken = System.getenv(AUTH_ACCESS_TOKEN);
        this.oAuthAccessTokenSecret = System.getenv(AUTH_ACCESS_TOKEN_SECRET);

        this.twitter = getTwitterInstance();
    }

    @Override
    @Scheduled(cron = "${CRON_FRAME_RIPPER_EXPRESSION}")
    public void ripImageFromVideo() throws IOException, TwitterException, JCodecException {
        File sourceFile = (Objects.nonNull(files) && files.length > 0) ? files[0] : null;

        if (isMp4ValidFile(sourceFile)) {
            Picture picture = FrameGrab.getFrameFromFile(sourceFile, getRandomFrame(sourceFile));

            File outputfile = new File(LAST_GENERATED_FRAME_PNG);
            ImageIO.write(AWTUtil.toBufferedImage(picture), MimeTypeUtils.IMAGE_PNG.getSubtype(), outputfile);
            tweetFrame(outputfile);
        }
    }

    private int getRandomFrame(File sourceFile) throws IOException {
        int totalAmountOfFrames = getAmountOfFrames(sourceFile);
        int randomFrame = ThreadLocalRandom.current().nextInt(FIRST_FRAME, totalAmountOfFrames);
        log.info("Random frame {} of {}", randomFrame, totalAmountOfFrames);
        return randomFrame;
    }

    private boolean isMp4ValidFile(File sourceFile) {
        return Objects.nonNull(sourceFile) &&
                FILE_EXTENSION_MP4.equalsIgnoreCase(FilenameUtils.getExtension(sourceFile.getName()));
    }

    private void tweetFrame(File frameFile) throws TwitterException {
        StatusUpdate status = new StatusUpdate("");
        status.setMedia(frameFile);
        twitter.updateStatus(status);
        log.info("Image succesfully tweeted!");
    }

    private int getAmountOfFrames(File sourceFile) throws IOException {
        FileChannelWrapper fileChannelWrapper = NIOUtils.readableFileChannel(sourceFile.getPath());
        MP4Demuxer demuxer = MP4Demuxer.createMP4Demuxer(fileChannelWrapper);
        DemuxerTrack videoTrack = demuxer.getVideoTrack();
        return videoTrack.getMeta().getTotalFrames();
    }

    private Twitter getTwitterInstance() {
        ConfigurationBuilder configBuilder = new ConfigurationBuilder();
        configBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(this.oAuthConsumerKey)
                .setOAuthConsumerSecret(this.oAuthConsumerSecret)
                .setOAuthAccessToken(this.oAuthAccessToken)
                .setOAuthAccessTokenSecret(this.oAuthAccessTokenSecret);
        TwitterFactory twitterFactory = new TwitterFactory(configBuilder.build());
        return twitterFactory.getInstance();
    }
}

