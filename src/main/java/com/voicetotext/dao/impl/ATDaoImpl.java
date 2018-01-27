package com.voicetotext.dao.impl;

import com.voicetotext.dao.ATDao;
import com.voicetotext.entity.AudioText;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class ATDaoImpl implements ATDao {

    @PersistenceContext
    private EntityManager entityManager;

    public AudioText findById(int id) {
        return entityManager.find(AudioText.class, id);
    }

    public AudioText update(AudioText audioText){
        return entityManager.merge(audioText);
    }

    public AudioText insert(AudioText audioText){
        return entityManager.merge(audioText);
    }

    public void deleteById(int id) {
        AudioText audioText = findById(id);
        if(audioText != null) {
            entityManager.remove(audioText);
        }
    }

    public List<AudioText> findAll(){
        TypedQuery<AudioText> namedQuery = entityManager.createNamedQuery("find_all_audiotexts", AudioText.class);
        return namedQuery.getResultList();
    }
}
