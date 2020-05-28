package pl.bergholc.bazak.jira.persistence;
import pl.bergholc.bazak.jira.exception.PersistenceException;

import java.util.List;

public interface PersistenceManager {
	
	Persistable create(Persistable persistable) throws PersistenceException;
	
	void update(Persistable persistable) throws PersistenceException;

	void delete(Persistable persistable) throws PersistenceException;

	List<Persistable> find(QueryBuilder queryBuilder) throws PersistenceException;
	
}
