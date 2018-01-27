package com.voicetotext.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import static com.voicetotext.conf.ATConfigurationProps.*;

/**
 * Created by User on 1/27/2018.
 */
public interface ATController {

    public String process(InputStream is) throws IOException;
    public String process(Path filepath) throws IOException;

    public RecognitionMode getMode();
    public void setMode(RecognitionMode mode);
    public Language getLanguage();
    public void setLanguage(Language language);
    public OutputFormat getFormat();
    public void setFormat(OutputFormat format);

}
