package com.nexeo.kata.bank_account.repo;

import java.util.List;

import com.nexeo.kata.bank_account.repo.exceptions.DataAccessException;

/**
 * Base repository interface.
 * @author aymens
 *
 * @param <T> Entiy type.
 */
/**
 */
public interface Repository<T> {

	/**
	 * Retrieve the entity.
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	T get(Integer id) throws DataAccessException;
	/**
	 * Test whether an entity with such an ID exists.
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	boolean exists(Integer id) throws DataAccessException;
	/**
	 * Create new entity.
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	T add(T entity) throws DataAccessException;
	/**
	 * Fetch all entities.
	 * @return
	 * @throws DataAccessException
	 */
	List<T> getAll() throws DataAccessException;
}