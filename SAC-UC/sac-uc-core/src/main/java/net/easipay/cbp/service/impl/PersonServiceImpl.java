package net.easipay.cbp.service.impl;

import net.easipay.cbp.dao.PersonDao;
import net.easipay.cbp.model.Person;
import net.easipay.cbp.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personService")
public class PersonServiceImpl implements PersonService {


	
	@Autowired
	private PersonDao personDao;
	
	@Override
	public void save(Person p) {
	
		
		personDao.save(p);
	}

}
