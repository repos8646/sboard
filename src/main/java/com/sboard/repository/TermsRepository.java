package com.sboard.repository;

import com.sboard.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermsRepository extends JpaRepository<Terms, String> {

    //public Terms findTermsByTerms(String terms);
    public List<Terms> findTermsByTerms(String terms);

}
