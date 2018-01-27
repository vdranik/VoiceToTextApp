package com.voicetotext.service.impl;

import com.voicetotext.entity.AudioText;
import com.voicetotext.dao.ATDao;
import com.voicetotext.service.ATService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ATServiceImpl implements ATService {

    @Autowired
    private ATDao atDao;

    public AudioText saveAudio(AudioText audioText) {
        return atDao.insert(audioText);
    }

    public AudioText getAudioTextById(int id) {
        return atDao.findById(id);
    }

    public List<AudioText> findAll(){
        return atDao.findAll();
    }
}
