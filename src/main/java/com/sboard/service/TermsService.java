package com.sboard.service;

import com.sboard.dto.TermsDTO;
import com.sboard.entity.Terms;
import com.sboard.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class TermsService {

    private final TermsRepository termsRepository;

    public List<TermsDTO> selectTerms(){
        List<Terms> termsAll = termsRepository.findAll();
        //System.out.println("termsAll" + termsAll);

        List<TermsDTO> terms = termsAll
                .stream()
                .map(entity -> entity.toDTO())
                .collect(Collectors.toList());

        return terms;
    }

}
