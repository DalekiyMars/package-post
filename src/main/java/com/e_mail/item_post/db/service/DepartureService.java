package com.e_mail.item_post.db.service;

import com.e_mail.item_post.constants.Constants;
import com.e_mail.item_post.entity.Departure;
import com.e_mail.item_post.db.repository.DepartureRepository;
import com.e_mail.item_post.util.DtoBadRequestException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
        return departureRepository.findById(id).orElseThrow(() -> new DtoBadRequestException(Constants.ExceptionMessages.INCORRECT_DEPARTURE));
    }

    @Transactional
    public Departure save(Departure departure) {
        if (Objects.nonNull(departure)){
            return departureRepository.save(departure);
        } else {
            return null;
        }
    }

    @Transactional
    public void update(int id, Departure departure) {
        if (departureRepository.findById(id).isEmpty()) {
            throw new DtoBadRequestException(Constants.ExceptionMessages.INCORRECT_DEPARTURE);
        }
        departure.setId(id);
        departureRepository.save(departure);
    }

    @Transactional
    public void delete(int id) {
        if (departureRepository.findById(id).isPresent()){
            departureRepository.deleteById(id);
        }
        throw new DtoBadRequestException(Constants.ExceptionMessages.INCORRECT_DEPARTURE);
    }
}
