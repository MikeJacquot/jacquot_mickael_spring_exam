package com.example.jacquot_mickael_spring_exam.service;

import com.example.jacquot_mickael_spring_exam.model.Annonce;
import com.example.jacquot_mickael_spring_exam.repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnonceService {

    @Autowired
    private AnnonceRepository ar;

    public List<Annonce> findAllAnnonces() {
        return ar.findAll();
    }

    public Annonce findAnnonceById(Long id) {
        return ar.findAnnonceById(id);
    }

    public void saveOrUpdateAnnonce(Annonce annonce) {
        ar.save(annonce);
    }

    public void deleteAnnonce(Annonce annonce) {
        ar.delete(annonce);

    }
}
