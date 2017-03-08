package com.jacobzim.LiftingLevel.interfaces;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jacobzim.LiftingLevel.models.User;

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, String> {
	public User findByName(String name);

}
