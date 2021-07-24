package com.jawiscript.kiminoframes.service;

import org.jcodec.api.JCodecException;
import twitter4j.TwitterException;

import java.io.IOException;


public interface IFrameRipperService {

    void ripImageFromVideo() throws IOException, TwitterException, JCodecException;
}
