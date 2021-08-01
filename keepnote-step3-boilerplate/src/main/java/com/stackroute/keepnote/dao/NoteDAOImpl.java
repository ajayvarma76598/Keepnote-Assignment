package com.stackroute.keepnote.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.model.Note;

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
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new note
	 */
	
	public boolean createNote(Note note) {
		Session session =sessionFactory.getCurrentSession();
		session.save(note);
		return true;

	}

	/*
	 * Remove an existing note
	 */
	
	public boolean deleteNote(int noteId) {
		boolean flag = true;
		try {
			if(getNoteById(noteId)==null) {
				flag = false;
			}else {
				Session session = sessionFactory.getCurrentSession();
				session.delete(getNoteById(noteId));
			
			}
		} catch (HibernateException e) {
System.out.println(e);
} catch (NoteNotFoundException e) {
	System.out.println(e);
		}
		return flag;
		
	}

	/*
	 * Retrieve details of all notes by userId
	 */
	
	public List<Note> getAllNotesByUserId(String userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Note note where createdBy =  :userId ").setParameter("userId", userId);
		List result = query.getResultList();
		return result;

	}

	/*
	 * Retrieve details of a specific note
	 */
	
	

	public Note getNoteById(int noteId) throws NoteNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Note note =session.get(Note.class, noteId);
		if(note==null)
			throw new NoteNotFoundException("NoteNotFoundException");
		else {
		
			return note;
		}

	}

	/*
	 * Update an existing note	
	 */

	public boolean UpdateNote(Note note) {
		boolean flag=true;
		try {
			if(getNoteById(note.getNoteId())==null) {
				flag=false;
			}else {
				sessionFactory.getCurrentSession().update(note);
			}
		} catch (HibernateException e) {
			System.out.println(e);		} catch (NoteNotFoundException e) {
			System.out.println(e);		}
		return flag;
	}

}