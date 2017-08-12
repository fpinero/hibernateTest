package com.fpe.hibernate.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fpe.hibernate.entity.Student;

public class DeleteStudentDemo {

	public static void main(String[] args) {
		
		// create session factory
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();
		
		//create session
		Session session = factory.getCurrentSession(); 
		
		try {
			
			int studentId = 2;
			
			// Modifiquemos a lo bruto el regitro con el Id 1
			System.out.println("Saved student. Genterated id: " + studentId);
			
			// ---------------------------------------------------------------------- 
			// IMPORTANTE!! ESTO SÓLO FUNCIONA CORRECTAMENE SI AL CAMPO ID DE LA ENTITY
			// SE LE HA AÑADIDO QUE EL CAMPO ES AUTO INCREMENTADO POR MYSQL
			// @GeneratedValue(strategy=GenerationType.IDENTITY)
			// MUY IMPORTANTE AÑADIRSELO SIEMPRE AL FIELD QUE MAPEA LA PRIMAY KEY
			// ---------------------------------------------------------------------- 
			
			// now get a new session and start transaction
			// por cada nueva operación hay q solicitar la session y comenzar una transaccion
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			// retrieve student based on the id primary key
			System.out.println("\nGetting student with id: " + studentId + " hardcoded");
			Student myStudent = session.get(Student.class, studentId);
			
			System.out.println("Deleting student: " + myStudent);
			session.delete(myStudent);
			
			// commit transaction  
			//No hay necesidad de hace un update, un simple commit ya vuelca los datos
			//a la tabla y actualiza pq el objeto contiene el ID, asi q lo q hace es sustituirlo
			//al completo con todos sus fields actuales ( myStudent.setFirstName("Scooby"); )
			session.getTransaction().commit();
			
			//NUEVO DELETE
			
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			// delete email for students where their lastname contains an 'p'
			System.out.println("\nDeleting students where lastName contains an 'p' letter");
			session.createQuery("DELETE FROM Student s WHERE s.lastName LIKE '%p%'").executeUpdate();
			
			// commit transaction  
			session.getTransaction().commit();
			
			System.out.println(">>> Done!! <<<");
			
		}finally {
			factory.close();
		}

	}

}
