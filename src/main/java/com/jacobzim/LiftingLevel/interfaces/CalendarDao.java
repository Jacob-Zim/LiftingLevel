package com.jacobzim.LiftingLevel.interfaces;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jacobzim.LiftingLevel.models.Date;

@Transactional
@Repository
public interface CalendarDao extends CrudRepository<Date, String> {

}
