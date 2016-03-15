package edu.cs157b.hibernate;

import org.hibernate.Session;

public interface PizzaShopDAO {
	
	Customer insertCustomer(Customer customer);
	void insertTopping(Topping topping);
	void insertOrder(Order order);
	void updateOrder(Order order);
	void deleteOrder(Order order);
	
	
}
