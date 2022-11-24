package com.library.repository;

import com.library.entities.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface RentalDao extends CrudRepository<Rental, Long> {
    List<Rental> findAll();

    Rental save(Rental rental);
}
