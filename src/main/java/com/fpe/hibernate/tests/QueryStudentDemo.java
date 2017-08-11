package com.fpe.hibernate.tests;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fpe.hibernate.entity.Student;

public class QueryStudentDemo {

	public static void main(String[] args) {
		
		// create session factory
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();
		
		//create session
		Session session = factory.getCurrentSession(); 
		
		try {
			
			// start a transaction
			session.beginTransaction();
			
			// query students
			List<Student> theStudents = session.createQuery("from Student").getResultList();
			//cuidado con esto que student no es valido, es Student, el nombre de la clase Entity
			
			// display students
			displayStudents(theStudents);
			
			// query students: lastName='Brown' (nótese q se usa el field, no el nombre de la campo)
			theStudents.clear();
			theStudents = session.createQuery("from Student s Where s.lastName='Brown'").getResultList();
			
			// display students = lastName = Brown
			System.out.println("\nStudents who have the lastName of Brown");
			displayStudents(theStudents);
			
			// query students = lastName = Brown firstName = Luis
			theStudents.clear();
			theStudents = session.createQuery("from Student s Where s.lastName='Brown' OR s.firstName='Luis'").getResultList();
			
			// display students: lastName = Brown
			System.out.println("\nStudents who have the lastName of Brown or firstName Luis");
			displayStudents(theStudents);
			
			// query students = whre email contains gmail
			theStudents.clear();
			theStudents = session.createQuery("from Student s WHERE s.email LIKE '%gmail%'").getResultList();
			
			// display students: lastName = Brown
			System.out.println("\nStudents who have a gmail email account");
			displayStudents(theStudents);
			
			// commit transaction
			session.getTransaction().commit();
			//Hay que realizar el commit aunque ya tengamos la lista pq sino eñ
			//factory.close da un error de una conexión aún abierta si utilizar.
			System.out.println(">>> Done!! <<<");
			
		}finally {
			factory.close();
		}

	}

	private static void displayStudents(List<Student> theStudents) {
		for (Student tempStudent : theStudents) {
			System.out.println(tempStudent);
		}
	}

}
