package com.fpe.hibernate.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fpe.hibernate.entity.Student;

public class UpdateStudentDemo {

	public static void main(String[] args) {
		
		// create session factory
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();
		
		//create session
		Session session = factory.getCurrentSession(); 
		
		try {
			
			int studentId = 1;
			
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
			
			System.out.println("Updating student...");
			
			myStudent.setFirstName("Scooby");
			
			// commit transaction  
			//No hay necesidad de hace un update, un simple commit ya vuelca los datos
			//a la tabla y actualiza pq el objeto contiene el ID, asi q lo q hace es sustituirlo
			//al completo con todos sus fields actuales ( myStudent.setFirstName("Scooby"); )
			session.getTransaction().commit();
			
			//NUEVO UPDATE
			
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			// update email for students where their lastname contains an 's'
			System.out.println("\nUpdating student's emails where lastName contains an 's' letter");
			session.createQuery("UPDATE Student s SET email='foo.email.com' WHERE s.lastName LIKE '%s%'").executeUpdate();
			
			// commit transaction  
			session.getTransaction().commit();
			
			System.out.println(">>> Done!! <<<");
			
		}finally {
			factory.close();
		}

	}

}
