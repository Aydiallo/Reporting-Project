package com.sigif.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.AbstractDAO;
import com.sigif.dto.AbstractDTO;
import com.sigif.modele.AbstractModele;
import com.sigif.util.ReflectionUtil;

/**
 * Implémentation de base des services.
 *
 * @param <E> type de l'entité
 * @param <D> type de DTO manipulé
 */
@Transactional
public abstract class AbstractServiceImpl<E extends AbstractModele, D extends AbstractDTO>
		implements AbstractService<D> {

    /**
     * Mapper Dozer.
     */
	@Autowired
	private Mapper mapper;

	/**
	 * DAO.
	 */
	@Autowired
	private AbstractDAO<E> dao;

	/**
	 * Classe générique de l'entité.
	 */
	protected Class<E> entityClass;
	
	/**
	 * Classe générique du DTO.
	 */
	protected Class<D> dtoClass;

	/**
	 * Constructeur permettant de définir automatiquement les classes de l'entité et du DTO utilisés.
	 */
	@SuppressWarnings("unchecked")
	public AbstractServiceImpl() {
		// Définit automatiquement le type générique manipulé par le DAO
		this.entityClass = (Class<E>) ReflectionUtil.getTypeParametreGenerique(getClass(), 0);
		this.dtoClass = (Class<D>) ReflectionUtil.getTypeParametreGenerique(getClass(), 1);
	}

	/**
	 * Récupère le DAO.
	 * @return le DAO.
	 */
	protected AbstractDAO<E> getDao() {
		return dao;
	}

	/* Méthodes génériques */

	@Override
	public D getById(int id) {
		return toDTO(dao.getById(id));
	}

	@Override
	public List<D> getAll() {
		return toDTOs(dao.getAll());
	}

	@Override
	public int save(D dto) {
		return dao.save(toEntite(dto));
	}

	@Override
	public void deleteById(int id) {
		dao.deleteById(id);
	}

	@Override
	public void delete(D dto) {
		dao.delete(toEntite(dto));
	}

	@Override
	public void update(D dto) {
		dao.merge(toEntite(dto));
	}

/* Méthodes de conversion Entité / DTO */
	/**
	 * Convertit une entité en DTO.
	 * @param entity Entité à convertir
	 * @return DTO correspondant à l'entité
	 */
	protected D toDTO(E entity) {
		if (entity != null) {
			return mapper.map(entity, dtoClass);
		} else {
			return null;
		}
	}

	/**
	 * Convertit une liste d'entités en DTOs.
	 * @param entities les entités à convertir
	 * @return La liste des DTOs correspondant aux entités en paramètre 
	 */
	protected List<D> toDTOs(List<E> entities) {
		List<D> dtos = new ArrayList<>(entities.size());
		for (E entity : entities) {
			if (entity != null) {
				dtos.add(mapper.map(entity, dtoClass));
			} else {
				dtos.add(null);
			}
		}
		return dtos;
	}

	/**
	 * Convertit un DTO en entité.
	 * @param dto Le DTO à convertir
	 * @return l'entité correspondant au DTO
	 */
	protected E toEntite(D dto) {
		if (dto != null) {
			return mapper.map(dto, entityClass);
		} else {
			return null;
		}
	}

	/**
	 * Convertit une liste de DTOs en entités.
	 * @param dtos La liste des DTOs à convertir
	 * @return La liste des entités correspondant aux DTOs
	 */
	protected List<E> toEntites(List<D> dtos) {
		List<E> entites = new ArrayList<>(dtos.size());
		for (D dto : dtos) {
			if (dto != null) {
				entites.add(mapper.map(dto, entityClass));
			} else {
				entites.add(null);
			}
		}
		return entites;
	}
}
