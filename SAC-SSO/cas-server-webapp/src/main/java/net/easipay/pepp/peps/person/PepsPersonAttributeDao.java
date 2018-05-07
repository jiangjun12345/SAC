/**
 * 
 */
package net.easipay.pepp.peps.person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.jasig.services.persondir.IPersonAttributes;
import org.jasig.services.persondir.support.AbstractDefaultAttributePersonAttributeDao;
import org.jasig.services.persondir.support.AttributeNamedPersonImpl;
import org.jasig.services.persondir.support.CaseInsensitiveAttributeNamedPersonImpl;
import org.jasig.services.persondir.support.CaseInsensitiveNamedPersonImpl;
import org.jasig.services.persondir.support.IUsernameAttributeProvider;
import org.jasig.services.persondir.support.MultivaluedPersonAttributeUtils;
import org.jasig.services.persondir.support.NamedPersonImpl;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * @author Administrator
 * 
 */
public class PepsPersonAttributeDao extends
		AbstractDefaultAttributePersonAttributeDao {

	private Map<String, Set<String>> queryAttributeMapping;

	private Set<String> possibleUserAttributes;

	private Map<String, Set<String>> resultAttributeMapping;

	private final SimpleJdbcTemplate simpleJdbcTemplate;

	private String queryTemplate;

	private String unmappedUsernameAttribute;

	public PepsPersonAttributeDao(DataSource datasource, String sql) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(datasource);
		this.queryTemplate = sql;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jasig.services.persondir.IPersonAttributeDao#getAvailableQueryAttributes
	 * ()
	 */
	public Set<String> getAvailableQueryAttributes() {
		return Collections.unmodifiableSet(this.queryAttributeMapping.keySet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jasig.services.persondir.IPersonAttributeDao#
	 * getPeopleWithMultivaluedAttributes(java.util.Map)
	 */
	public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(
			Map<String, List<Object>> query) {
		Validate.notNull(query, "query may not be null.");

		IUsernameAttributeProvider usernameAttributeProvider = getUsernameAttributeProvider();
		String username = usernameAttributeProvider.getUsernameFromQuery(query);
		
		List unmappedPeople = queryPersonAttributesByUsername(username);
		if (unmappedPeople == null) {
			return null;
		}

		Set mappedPeople = new LinkedHashSet();
		for (Iterator it = unmappedPeople.iterator(); it.hasNext();) {
			IPersonAttributes unmappedPerson = (IPersonAttributes) it.next();
			IPersonAttributes mappedPerson = mapPersonAttributes(unmappedPerson);
			mappedPeople.add(mappedPerson);
		}

		return Collections.unmodifiableSet(mappedPeople);
	}
	
	/**
	 * 根据用户名查询IPersonAttributes
	 * @param username
	 * @return
	 */
	protected List<IPersonAttributes> queryPersonAttributesByUsername(String username){
		
		List<Map<String,Object>> queryResults = this.simpleJdbcTemplate.queryForList(
				this.queryTemplate, StringUtils.upperCase(username), username);
		
		List peopleAttributes = new ArrayList(queryResults.size());

	    for (Iterator it = queryResults.iterator(); it.hasNext(); ) { IPersonAttributes person;
	      Map queryResult = (Map)it.next();
	      Map multivaluedQueryResult = MultivaluedPersonAttributeUtils.toMultivaluedMap(queryResult);

	      String userNameAttribute = getConfiguredUserNameAttribute();
	      person = new CaseInsensitiveAttributeNamedPersonImpl(userNameAttribute, multivaluedQueryResult);

	      peopleAttributes.add(person);
	    }

	    return peopleAttributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jasig.services.persondir.IPersonAttributeDao#
	 * getPossibleUserAttributeNames()
	 */
	public Set<String> getPossibleUserAttributeNames() {
		return this.possibleUserAttributes;
	}

	public void setResultAttributeMapping(Map<String, ?> resultAttributeMapping) {
		Map parsedResultAttributeMapping = MultivaluedPersonAttributeUtils
				.parseAttributeToAttributeMapping(resultAttributeMapping);

		if (parsedResultAttributeMapping.containsKey("")) {
			throw new IllegalArgumentException(
					"The map from attribute names to attributes must not have any empty keys.");
		}

		Collection userAttributes = MultivaluedPersonAttributeUtils
				.flattenCollection(parsedResultAttributeMapping.values());

		this.resultAttributeMapping = parsedResultAttributeMapping;
		this.possibleUserAttributes = Collections
				.unmodifiableSet(new LinkedHashSet(userAttributes));
	}

	protected final IPersonAttributes mapPersonAttributes(
			IPersonAttributes person) {
		Map mappedAttributes;
		IPersonAttributes newPerson;
		Map personAttributes = person.getAttributes();

		if (this.resultAttributeMapping == null) {
			mappedAttributes = personAttributes;
		} else {
			mappedAttributes = new LinkedHashMap();

			for (Iterator i$ = this.resultAttributeMapping.entrySet()
					.iterator(); i$.hasNext();) {
				Map.Entry resultAttrEntry = (Map.Entry) i$.next();
				String dataKey = (String) resultAttrEntry.getKey();

				if (personAttributes.containsKey(dataKey)) {
					Set resultKeys = (Set) resultAttrEntry.getValue();

					if (resultKeys == null) {
						resultKeys = Collections.singleton(dataKey);
					}

					List value = (List) personAttributes.get(dataKey);
					for (Iterator it = resultKeys.iterator(); it.hasNext();) {
						String resultKey = (String) it.next();
						if (resultKey == null) {
							mappedAttributes.put(dataKey, value);
						} else
							mappedAttributes.put(resultKey, value);
					}

				}

			}

		}

		String name = person.getName();
		if (name != null) {
			newPerson = new NamedPersonImpl(name, mappedAttributes);
		} else {
			String userNameAttribute = getConfiguredUserNameAttribute();
			newPerson = new AttributeNamedPersonImpl(userNameAttribute,
					mappedAttributes);
		}

		return newPerson;
	}

	protected String getConfiguredUserNameAttribute() {
		if (this.unmappedUsernameAttribute != null) {
			return this.unmappedUsernameAttribute;
		}

		IUsernameAttributeProvider usernameAttributeProvider = getUsernameAttributeProvider();
		return usernameAttributeProvider.getUsernameAttribute();
	}

	public String getUnmappedUsernameAttribute() {
		return this.unmappedUsernameAttribute;
	}

	public void setUnmappedUsernameAttribute(String userNameAttribute) {
		this.unmappedUsernameAttribute = userNameAttribute;
	}
}
