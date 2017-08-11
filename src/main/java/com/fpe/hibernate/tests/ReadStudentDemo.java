package com.fpe.hibernate.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fpe.hibernate.entity.Student;

public class ReadStudentDemo {

	public static void main(String[] args) {
		
		// create session factory
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();
		
		//create session
		Session session = factory.getCurrentSession(); 
		
		try {
			// use the session object to save Java Object
			// create a student object
			System.out.println("Creating new student object...");
			Student tempStudent = new Student("Perico", "Palotes", "pericopalotes@gmail.com");
			
			// start a transaction
			session.beginTransaction();
			
			// save the student object
			System.out.println("Saving the student " + tempStudent.toString());
			session.save(tempStudent);
			
			// commit transaction
			session.getTransaction().commit();
			
			// ---------------------------------------------------------------------- 
			// IMPORTANTE!! ESTO SÓLO FUNCIONA CORRECTAMENE SI AL CAMPO ID DE LA ENTITY
			// SE LE HA AÑADIDO QUE EL CAMPO ES AUTO INCREMENTADO POR MYSQL
			// @GeneratedValue(strategy=GenerationType.IDENTITY)
			// MUY IMPORTANTE AÑADIRSELO SIEMPRE AL FIELD QUE MAPEA LA PRIMAY KEY
			// ---------------------------------------------------------------------- 
			
			// find out the student's id primare key
			System.out.println("Saved student. Genterated id: " + tempStudent.getId());
			
			// now get a new session and start transaction
			// por cada nueva operación hay q solicitar la session y comenzar una transaccion
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			// retrieve student based on the id primary key
			System.out.println("\nGetting student with id: " + tempStudent.getId());
			Student myStudent = session.get(Student.class, tempStudent.getId());
			System.out.println("Get complete: " + myStudent);
			
			//commit the transaction
			session.getTransaction().commit();
			
			System.out.println(">>> Done!! <<<");
			
		}finally {
			factory.close();
		}

	}

}
