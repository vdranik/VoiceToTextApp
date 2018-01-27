package com.voicetotext.dao;

import com.voicetotext.entity.AudioText;

import java.util.List;

public interface ATDao {

    AudioText findById(int id);
    AudioText update(AudioText audioText);
    AudioText insert(AudioText audioText);
    void deleteById(int id);
    List<AudioText> findAll();

}
