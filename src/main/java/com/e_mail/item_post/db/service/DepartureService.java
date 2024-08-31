package com.e_mail.item_post.db.service;

import com.e_mail.item_post.constants.Constants;
import com.e_mail.item_post.entity.Departure;
import com.e_mail.item_post.db.repository.DepartureRepository;
import com.e_mail.item_post.util.ItemPostException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
// TODO посмотреть про swagger
public class DepartureService {
    private final DepartureRepository departureRepository;

    public List<Departure> findAll() {
        return departureRepository.findAll();
    }

    public Departure findOne(int id) {
        return departureRepository.findById(id).orElseThrow(() -> new ItemPostException(Constants.ExceptionMessages.INCORRECT_DEPARTURE));
    }

    @Transactional
    public void save(Departure departure) {
        departureRepository.save(departure);
    }

    @Transactional
    public void update(int id, Departure departure) {
        if (departureRepository.findById(id).isEmpty()) {
            throw new ItemPostException(Constants.ExceptionMessages.INCORRECT_DEPARTURE);
        }
        departure.setId(id);
        departureRepository.save(departure);
    }

    @Transactional
    public void delete(int id) {
        departureRepository.deleteById(id);
    }
}
