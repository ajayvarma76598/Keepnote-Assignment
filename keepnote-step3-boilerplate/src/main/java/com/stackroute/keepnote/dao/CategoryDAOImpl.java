package com.stackroute.keepnote.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.User;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class CategoryDAOImpl implements CategoryDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public CategoryDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new category
	 */
	public boolean createCategory(Category category) {
		Session session = sessionFactory.getCurrentSession();
		session.save(category);
			return true;

	}

	/*
	 * Remove an existing category
	 */
	public boolean deleteCategory(int categoryId) {
		boolean flag = true;
		try {
			if(getCategoryById(categoryId)==null) {
				flag = false;
			}else {
				Session session = sessionFactory.getCurrentSession();
				session.delete(getCategoryById(categoryId));
			}
		} catch (HibernateException e) {
			System.out.println(e);		} catch (CategoryNotFoundException e) {
			System.out.println(e);		}
		return flag;

	}
	/*
	 * Update an existing category
	 */

	public boolean updateCategory(Category category) {
		boolean flag = true;
		try {
			if(getCategoryById(category.getCategoryId())==null) {
				flag = false;
			}else {
				sessionFactory.getCurrentSession().update(category);
			}
		} catch (HibernateException e) {
			System.out.println(e);		} catch (CategoryNotFoundException e) {
			System.out.println(e);		}
		return flag;
	}
	/*
	 * Retrieve details of a specific category
	 */

	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Category category = session.get(Category.class, categoryId);
		if(category==null)
			throw new CategoryNotFoundException("CategoryNotFoundException");
		else {
			return category;
		}
			

	}

	/*
	 * Retrieve details of all categories by userId
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
		Query query = sessionFactory.getCurrentSession().createQuery("From Category category where categoryCreatedBy = :userId").setParameter("userId", userId);
		List result = query.getResultList();
		return result;
	}

}