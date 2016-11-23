package com.mdamine.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.mdamine.dao.GenericDao;
import com.mdamine.dao.exceptions.EntityNotFoundException;

/**
 * Classe de base pour tous les DAOs. Elle impl�mente les m�thodes CRUD
 * g�n�riques. Cette impl�mentation est bas�e sur la classe HibernateDaoSupport
 * de Spring
 * 
 * @param <T>
 *            le type d'objet m�tier manipul�
 * @param <PK>
 *            le type utilis� pour l'indentifiant d'un objet m�tier
 */

public class GenericDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {

	/** Utilis� par tous les DAOs pour tracer les �v�nements */
	protected final Logger TRACER = Logger.getLogger(getClass());

	/** Repr�sente la classe de l'objet m�tier manipul� */
	private Class<T> persistentClass;

	/**
	 * Constructeur pr�cisant la classe de l'objet m�tier manipul�
	 * 
	 * @param pPersistentClass
	 *            la classe de l'objet m�tier manipul�
	 */

	public GenericDaoImpl(final Class<T> pPersistentClass) {
		TRACER.trace("a DAO has been initialised to handle objects of type  " + persistentClass);
		persistentClass = pPersistentClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public T create(T pObject) {

		getHibernateTemplate().save(pObject);
		return pObject;
	}

	/**
	 * {@inheritDoc}
	 */
	public void update(T pObject) {

//		Session s = getHibernateTemplate().getSessionFactory().openSession();
//		Transaction tx = s.beginTransaction();
//		System.out.println("<>"+pObject);
//		s.merge(pObject);
//		tx.commit();
//		s.close();

		getHibernateTemplate().merge(pObject);
	}

	// public void update(PK pId) {
	// Session s = getHibernateTemplate().getSessionFactory().openSession();
	// Transaction tx = s.beginTransaction();
	// T lEntity = (T) s.get(this.persistentClass, pId);
	// tx.commit();
	// s.close();
	//
	// System.out.println(lEntity);
	// getHibernateTemplate().update(lEntity);
	// }

	/**
	 * {@inheritDoc}
	 */
	public List<T> getAll() {
		return getHibernateTemplate().loadAll(persistentClass);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws EntityNotFoundException
	 */
	public void delete(PK pId) throws EntityNotFoundException {

		 T lEntity = null;
		
		 lEntity = findById(pId);
		
		 getHibernateTemplate().delete(lEntity);

//		Session s = getHibernateTemplate().getSessionFactory().openSession();
//		Transaction tx = s.beginTransaction();
//		T lEntity = (T) s.get(this.persistentClass, pId);
//		s.delete(lEntity);
//		tx.commit();
//		s.close();

	}

	/**
	 * {@inheritDoc}
	 */
	public boolean exists(PK id) {
		try {
			findById(id);
		} catch (EntityNotFoundException e) {
			return false;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public T findById(PK pId) throws EntityNotFoundException {
		T lEntity = (T) getHibernateTemplate().get(this.persistentClass, pId);

		if (lEntity == null) {

			TRACER.trace("an exception EntityNotFoundException : No entity with ID  = " + pId + " is found");
			throw new EntityNotFoundException("The entity with ID  = " + pId + " is not found");
		}

		return lEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> getAll(int maxResults) {
		HibernateTemplate ht = getHibernateTemplate();
		ht.setMaxResults(maxResults);

		return ht.loadAll(persistentClass);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> getAllDistinct() {

		Collection<T> result = new LinkedHashSet<T>(getAll());
		return new ArrayList<T>(result);

	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> getAllDistinct(int maxResult) {
		Collection<T> result = new LinkedHashSet<T>(getAll(maxResult));
		return new ArrayList<T>(result);
	}

	@Override
	public List<T> getEntityByColumn(String ClassName, String pColumnName, String pValue) {

		// Une requete HQL simple pour faire la selection
		String HqlQuery = "from " + ClassName + " where " + pColumnName + " = ?";

		List l = getHibernateTemplate().find(HqlQuery, pValue);
		if (l == null || l.size() == 0)
			throw new ObjectRetrievalFailureException(persistentClass, null);

		return l;

	}

}
