package com.jacobzim.LiftingLevel.interfaces;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jacobzim.LiftingLevel.models.Lift;
import com.jacobzim.LiftingLevel.models.User;

@Transactional
@Repository
public interface LiftDao extends CrudRepository<Lift, Integer> {
	
	public List<Lift> findAllByUser(User user);
}
