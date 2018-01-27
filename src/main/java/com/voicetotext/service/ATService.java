package com.voicetotext.service;

import com.voicetotext.entity.AudioText;

import java.util.List;

public interface ATService {

    public AudioText saveAudio(AudioText audioText);
    public AudioText getAudioTextById(int id);
    public List<AudioText> findAll();

}
