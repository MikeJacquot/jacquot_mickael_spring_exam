package com.example.jacquot_mickael_spring_exam.repository;

import com.example.jacquot_mickael_spring_exam.model.Annonce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceRepository extends CrudRepository<Annonce, Long> {


    @Override
    List<Annonce> findAll();

    Annonce findAnnonceById(Long id);

    Annonce findAnnonceByTitre(String nom);

    Page<Annonce> findAll(Pageable page);


}
