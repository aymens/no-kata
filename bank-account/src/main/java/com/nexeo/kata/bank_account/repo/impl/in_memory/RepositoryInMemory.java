package com.nexeo.kata.bank_account.repo.impl.in_memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexeo.kata.bank_account.model.BaseEntity;
import com.nexeo.kata.bank_account.repo.Repository;
import com.nexeo.kata.bank_account.repo.exceptions.DataAccessException;
import com.nexeo.kata.bank_account.repo.exceptions.DuplicateEntityException;
import com.nexeo.kata.bank_account.repo.exceptions.NotFoundEntityException;

/**
 * Base class for in-memory entities repositories.
 * 
 * TODO in this context, we suppose given entities in arguments are not null.
 * 
 * @author aymens
 *
 * @param <T>
 */
public abstract class RepositoryInMemory<T extends BaseEntity> implements Repository<T> {

	private static Logger log = LoggerFactory.getLogger(RepositoryInMemory.class);

	protected final List<T> entities = new ArrayList<>();
	
	protected Integer nextId() {
		return 1 + entities.size();
	}

	@Override
	public T add(T entity) throws DataAccessException {
		setIdIfNecessary(entity);
		if (entities.stream().noneMatch(e -> e.getId().intValue() == entity.getId().intValue())
				&& entities.add(SerializationUtils.clone(entity))) {
			return entity;
		} else {
			log.error("Error creating entity {}", entity);
			throw new DuplicateEntityException();
		}
	}

	private void setIdIfNecessary(T entity) {
		if (Objects.isNull(entity.getId())) {
			entity.setId(nextId());
		}
	}

	@Override
	public T get(Integer id) throws DataAccessException {
		return entities.stream().filter(e -> e.getId().intValue() == id.intValue()).findFirst().map(e -> SerializationUtils.clone(e))
				.orElseThrow(() -> new NotFoundEntityException());
	}

	@Override
	public boolean exists(Integer id) throws DataAccessException {
		return entities.stream().anyMatch(e -> e.getId().intValue() == id.intValue());
	}

	@Override
	public List<T> getAll() throws DataAccessException {
		return entities.stream().map(SerializationUtils::clone).collect(Collectors.toList());
	}
}
