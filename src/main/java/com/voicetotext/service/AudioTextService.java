package com.voicetotext.service;

import com.voicetotext.entity.AudioText;
import com.voicetotext.repository.AudioTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by User on 1/27/2018.
 */
@Service
public class AudioTextService {

    @Autowired
    private AudioTextRepository audioTextRepository;

    public AudioText saveAudio(AudioText audioText) {
        return audioTextRepository.insert(audioText);
    }

    public AudioText getAudioTextById(int id) {
        return audioTextRepository.findById(id);
    }
}
