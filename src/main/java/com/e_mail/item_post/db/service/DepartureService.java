package com.e_mail.item_post.db.service;

import com.e_mail.item_post.constants.Constants;
import com.e_mail.item_post.entity.Departure;
import com.e_mail.item_post.db.repository.DepartureRepository;
import com.e_mail.item_post.util.DtoBadRequestException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
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
    public void updateDepartureInfo(int id, Departure updtdDeparture){
        var departure = findOne(id);
        departure = updtdDeparture;
        departure.setId(id);
        departureRepository.save(departure);
    }

    @Transactional
    public void delete(int id) {
        var foundDeparture = findOne(id);
        log.info(foundDeparture.toString() + " будет удален");
        departureRepository.delete(foundDeparture);

    }
}
