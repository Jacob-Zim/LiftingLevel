package com.jacobzim.LiftingLevel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jacobzim.LiftingLevel.interfaces.UserDao;
import com.jacobzim.LiftingLevel.models.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiftingLevelApplicationTests {
	
	@Autowired
	private UserDao userDao;

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void LoginShouldReturnNullWithWrongUserInput(String name) {
		User nameTaken = userDao.findByName(name);
		assertEquals(null, nameTaken);
	}

}
