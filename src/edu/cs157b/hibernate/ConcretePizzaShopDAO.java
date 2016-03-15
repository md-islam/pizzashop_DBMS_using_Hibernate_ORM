package edu.cs157b.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ConcretePizzaShopDAO implements PizzaShopDAO {

	private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	//private static SessionFactory sessionFactory;
	
	
	public Customer insertCustomer(Customer customer){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(customer);
		session.getTransaction().commit();
		session.close();
		return customer;
	}
	
	
	public void insertTopping(Topping topping){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(topping);
		session.getTransaction().commit();
		session.close();
	}
	
	public void insertOrder(Order order){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(order);
		session.getTransaction().commit();
		session.close(); 	
	}
	
	
	
	
	public void updateOrder(Order order){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.flush();
		
		session.saveOrUpdate(order);
		session.getTransaction().commit();
		session.close();
		
	}
	
	
	public void deleteOrder(Order order){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(order);
		session.getTransaction().commit();
		session.close();
	}
	
}
