/**
 * 
 */
package net.easipay.cbp.dao.impl;

import org.springframework.stereotype.Repository;

import net.easipay.cbp.dao.PersonDao;
import net.easipay.cbp.dao.base.GenericDaoiBatis;
import net.easipay.cbp.model.Person;

/**
 * @author Administrator
 *
 */

@Repository("personDao")
public class PersonDaoImpl extends GenericDaoiBatis<Person,Long> implements PersonDao{

	public PersonDaoImpl(){
		super(Person.class);
	}
	
	public PersonDaoImpl(Class<Person> persistentClass) {
		super(persistentClass);
	}

	
}
