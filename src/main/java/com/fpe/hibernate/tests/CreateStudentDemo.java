package com.fpe.hibernate.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fpe.hibernate.entity.Student;

public class CreateStudentDemo {

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
			Student tempStudent = new Student("Paul", "Fuentes", "paulfuentes@gmail.com");
			
			// start a transaction
			session.beginTransaction();
			
			// save the student object
			System.out.println("Saving the student " + tempStudent.toString());
			session.save(tempStudent);
			
			// commit transaction
			session.getTransaction().commit();
			System.out.println(">>> Done!! <<<");
			
		}finally {
			factory.close();
		}

	}

}
