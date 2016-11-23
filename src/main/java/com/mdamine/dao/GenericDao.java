package com.mdamine.dao;

import java.util.List;

import com.mdamine.dao.exceptions.EntityNotFoundException;

/**
 * Generic DAO (Data Access Object) d�finie les m�thodes CRUD communes � tous
 * les DAOs
 * 
 * @param <T>
 *            le type de l'objet m�tier manipul�
 * @param <PK>
 *            le type utilis� pour la cl� primaire de l'objet m�tier
 */
public interface GenericDao<T, PK> {

	/**
	 * M�thode g�n�rique pour sauvgarder un objet m�tier
	 * 
	 * @param pObject
	 *            l'obhet � sauvgarder
	 * 
	 * @return l'objet persistant
	 */
	T create(T pObject);

	/**
	 * M�thode g�n�rique pour supprimer un objet en connaissant son identifiant
	 * 
	 * @param pId
	 *            l'identifiant (cl� primaire) de l'objet � supprimer
	 * @throws EntityNotFoundException
	 */
	void delete(PK pId) throws EntityNotFoundException;

	/**
	 * M�thode g�n�rique pour mettre � jour un objet modifi� en dehors de la
	 * session
	 * 
	 * @param pObject
	 *            nouvelle version de l'objet
	 */
	void update(T pObject);
	// void update(PK pId);

	/**
	 * M�thode g�n�rique permetant de r�cup�rer tous les objets d'un type donn�
	 * 
	 * @return liste des objets
	 */
	List<T> getAll();

	/**
	 * M�thode g�n�rique permetant de r�cup�rer tous les objets d'un type donn�
	 * 
	 * @param maxResult
	 *            nombre maximal des r�sultats
	 * @return liste des objets
	 */

	List<T> getAll(int maxResults);

	/**
	 * M�thode g�n�rique permetant de r�cup�rer tous les objets d'un type donn�,
	 * sans avoir des doublants
	 * <p>
	 * Noter que pour cette m�thode fonctionne correctement, les objets m�tiers
	 * doivent avoir des m�thodes equalset hashCode correctement d�finies
	 * </p>
	 * 
	 * @return liste des objets
	 */
	List<T> getAllDistinct();

	/**
	 * M�thode g�n�rique permetant de r�cup�rer tous les objets d'un type donn�
	 * sans avoir des doublants et avec limitation du nombre des r�sultats
	 * <p>
	 * Noter que pour cette m�thode fonctionne correctement, les objets m�tiers
	 * doivent avoir des m�thodes equalset hashCode correctement d�finies
	 * </p>
	 * 
	 * @param maxResult
	 *            nombre maximal des r�sultats
	 * @return liste des objets
	 */
	List<T> getAllDistinct(int maxResults);

	/**
	 * M�thode g�n�rique pour obtenir un objet par son identifiant, une
	 * exception de type ObjectRetrievalFailureException sera lev�e si l'objet
	 * est intouvable
	 * 
	 * @param pId
	 *            l'identifiant de l'objet
	 * @return l'objet recherch�
	 * @throws EntityNotFoundException
	 *             si aucun objet n'est trouv�
	 */
	T findById(PK pId) throws EntityNotFoundException;

	/**
	 * V�rifie si un objet, de type T et identifiant pId, existe
	 * 
	 * @param pId
	 *            identifiant de l'objet
	 * @return true si un objet existe, fase sinon
	 */
	boolean exists(PK pId);

	/**
	 * retourne une entit� par la valeur d'une colonne
	 * 
	 * @param pColumnName
	 *            le nom de la colonne
	 * @param pValue
	 *            la valeur de la colonne sur laquelle il faut faire la
	 *            selection
	 * @return l'entit� recherch�e
	 */
	List<T> getEntityByColumn(String pClassName, String pColumnName, String pValue);
}
