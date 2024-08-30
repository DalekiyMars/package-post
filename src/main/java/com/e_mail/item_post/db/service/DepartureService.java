package com.e_mail.item_post.db.service;

import com.e_mail.item_post.common.Departure;
import com.e_mail.item_post.db.repository.DepartureRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DepartureService {
    private final DepartureRepository departureRepository;

    public List<Departure> findAll(){
        return departureRepository.findAll();
    }

    public Departure findOne(int id){
        return departureRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Departure departure){
        departureRepository.save(departure);
    }
    @Transactional
    public void update(int id, Departure departure){
        departure.setId(id);
        departureRepository.save(departure);
    }
    @Transactional
    public void delete(int id){
        departureRepository.deleteById(id);
    }
}
