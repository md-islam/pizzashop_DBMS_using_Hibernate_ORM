package edu.cs157b.hibernate;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;

public class ServiceLayer{
	
	private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public ServiceLayer(){
		
	}
	
	public void createToppings(){
		PizzaShopDAO pizzaShopDAO = new ConcretePizzaShopDAO();
		Topping topping = new Topping();
		topping.setToppingName("Pepperoni");
		topping.setToppingPrice(5);
		pizzaShopDAO.insertTopping(topping);
		
		topping.setToppingName("Mushrooms");
		topping.setToppingPrice(1);
		pizzaShopDAO.insertTopping(topping);
		
		topping.setToppingName("Onions");
		topping.setToppingPrice(1);
		pizzaShopDAO.insertTopping(topping);
		
		topping.setToppingName("Sausage");
		topping.setToppingPrice(4);
		pizzaShopDAO.insertTopping(topping);
		
		topping.setToppingName("Bacon");
		topping.setToppingPrice(3);
		pizzaShopDAO.insertTopping(topping);
		
		topping.setToppingName("Extra cheese");
		topping.setToppingPrice(1);
		pizzaShopDAO.insertTopping(topping);
		
		topping.setToppingName("Black olives");
		topping.setToppingPrice(1);
		pizzaShopDAO.insertTopping(topping);
		
		topping.setToppingName("Green Peppers");
		topping.setToppingPrice(1);
		pizzaShopDAO.insertTopping(topping);
		
		topping.setToppingName("Pineapple");
		topping.setToppingPrice(2);
		pizzaShopDAO.insertTopping(topping);
		
		topping.setToppingName("Spinach");
		topping.setToppingPrice(3);
		pizzaShopDAO.insertTopping(topping);
		
		
	}
	
	
	
	
	
	public void createCustomer(){
		Scanner in = new Scanner(System.in);
		System.out.println("enter username");
		String username = in.nextLine();
		System.out.println("enter password");
		String password = in.nextLine();
		System.out.println("enter street name");
		String streetName = in.nextLine();
		System.out.println("enter city");
		String city = in.nextLine();
		System.out.println("enter state");
		String state = in.nextLine();
		System.out.println("enter zipcode");
		String zipcode = in.nextLine();
		Customer customer = new Customer();
		Address address = new Address();
		
		address.setStreetName(streetName);
		address.setCity(city);
		address.setState(state);
		address.setZipCode(zipcode);
		
		customer.setUserName(username);
		customer.setPassword(password);
		customer.setAddress(address);
		
		PizzaShopDAO pizzaShopDAO = new ConcretePizzaShopDAO();
		pizzaShopDAO.insertCustomer(customer);
	}
	
	
	public void makeOrder(Customer customer){
		Order order = new Order();
		Scanner in = new Scanner(System.in);
		double pizzaPrice=0;
//		order.setOrderPrice(34);
//		order.setPaymentMethod(PaymentMethod.MASTER);
//		order.setPizzaSize(PizzaSize.LARGE);
		System.out.println("Enter Payment Type");
		String type = in.nextLine();
		if(type.equalsIgnoreCase("VISA")){
			order.setPaymentMethod(PaymentMethod.VISA);
		}
		else if(type.equals("MASTER")){
			order.setPaymentMethod(PaymentMethod.MASTER);
		}
		else{
			order.setPaymentMethod(PaymentMethod.CASH);
		}
		
		System.out.println("Enter Pizza Size");
		String size = in.nextLine();
		if(size.equalsIgnoreCase("LARGE")){
			order.setPizzaSize(PizzaSize.LARGE);
			pizzaPrice+= PizzaSize.LARGE.getPizzaPrice();
		}
		else if(size.equalsIgnoreCase("MEDIUM")){
			order.setPizzaSize(PizzaSize.MEDIUM);
			pizzaPrice+= PizzaSize.MEDIUM.getPizzaPrice();
		} 
		else{
			order.setPizzaSize(PizzaSize.SMALL);
			pizzaPrice+= PizzaSize.SMALL.getPizzaPrice();
		}
		
		System.out.println("Would you like to add toppings (YES/NO)");
		String toppingResponse = in.nextLine();
		if(toppingResponse.equalsIgnoreCase("YES")){
			int maxTopping = 3;
			boolean addAnother = true;
			int toppingCounter = 0;
			while(maxTopping >0 && addAnother==true){
				System.out.println("Enter Topping (Pepperoni, Mushrooms, Onions, Sausage, Bacon, Extra cheese, Black olives, \nGreen peppers, Pineapple, Spinach)");
				String topping = in.nextLine();
				Topping newTopping = getTopping(topping);
				pizzaPrice+=newTopping.getToppingPrice();
				order.setToppings(newTopping);
				if(toppingCounter<=3)
				{
					System.out.println("Add another topping? (YES/NO)");
					String anotherToppingResponse = in.nextLine();
					if(anotherToppingResponse.equalsIgnoreCase("NO"))
						{
							addAnother=false;
						}
				}
				maxTopping--;
				toppingCounter++;
			}
		}
		else{
			System.out.println("No toppings will be added");
		}		
		
		order.setOrderPrice(pizzaPrice);
		//one to many part  -- might need to change
		customer.setOrder(order);
		order.setCustomer(customer);
		
		Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		order.setDeliveryTime(currentTimestamp);
		
		PizzaShopDAO pizzaShopDAO = new ConcretePizzaShopDAO();
		pizzaShopDAO.insertOrder(order);
		
	}
	
	
	public void makeDiscountedOrder(Customer customer){
		DiscountedOrder discountedOrder = new DiscountedOrder();
		Scanner in = new Scanner(System.in);
		double pizzaPrice=0;
//		order.setOrderPrice(34);
//		order.setPaymentMethod(PaymentMethod.MASTER);
//		order.setPizzaSize(PizzaSize.LARGE);
		System.out.println("Enter Payment Type");
		String type = in.nextLine();
		if(type.equalsIgnoreCase("VISA")){
			discountedOrder.setPaymentMethod(PaymentMethod.VISA);
		}
		else if(type.equals("MASTER")){
			discountedOrder.setPaymentMethod(PaymentMethod.MASTER);
		}
		else{
			discountedOrder.setPaymentMethod(PaymentMethod.CASH);
		}
		
		System.out.println("Enter Pizza Size");
		String size = in.nextLine();
		if(size.equalsIgnoreCase("LARGE")){
			discountedOrder.setPizzaSize(PizzaSize.LARGE);
			pizzaPrice+= PizzaSize.LARGE.getPizzaPrice();
		}
		else if(size.equalsIgnoreCase("MEDIUM")){
			discountedOrder.setPizzaSize(PizzaSize.MEDIUM);
			pizzaPrice+= PizzaSize.MEDIUM.getPizzaPrice();
		} 
		else{
			discountedOrder.setPizzaSize(PizzaSize.SMALL);
			pizzaPrice+= PizzaSize.SMALL.getPizzaPrice();
		}
		
		System.out.println("Would you like to add toppings (YES/NO)");
		String toppingResponse = in.nextLine();
		if(toppingResponse.equalsIgnoreCase("YES")){
			int maxTopping = 3;
			boolean addAnother = true;
			int toppingCounter = 0;
			while(maxTopping >0 && addAnother==true){
				System.out.println("Enter Topping (Pepperoni, Mushrooms, Onions, Sausage, Bacon, Extra cheese, Black olives, \nGreen peppers, Pineapple, Spinach)");
				String topping = in.nextLine();
				Topping newTopping = getTopping(topping);
				pizzaPrice+=newTopping.getToppingPrice();
				discountedOrder.setToppings(newTopping);
				if(toppingCounter<=3)
				{
					System.out.println("Add another topping? (YES/NO)");
					String anotherToppingResponse = in.nextLine();
					if(anotherToppingResponse.equalsIgnoreCase("NO"))
						{
							addAnother=false;
						}
				}
				maxTopping--;
				toppingCounter++;
			}
		}
		else{
			System.out.println("No toppings will be added");
		}		
		 
		discountedOrder.setDiscountedRate(pizzaPrice);
		pizzaPrice = discountedOrder.getDiscountedRate(); 
		
		discountedOrder.setOrderPrice(pizzaPrice);
		//one to many part  -- might need to change
		customer.setOrder(discountedOrder);
		discountedOrder.setCustomer(customer);
		Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		discountedOrder.setDeliveryTime(currentTimestamp);
		
		PizzaShopDAO pizzaShopDAO = new ConcretePizzaShopDAO();
		pizzaShopDAO.insertOrder(discountedOrder);
		
	}
	
	
	public Customer getAuthenticatedCustomer(String username, String password){
		
		String queryString = "from Customer where username = ? AND password = ?";
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		
		Query query = session.createQuery(queryString);
		query.setString(0, username);
		query.setString(1, password);
		
		Customer acquiredCustomer = (Customer) query.uniqueResult();
		session.close();
		return acquiredCustomer; 
		
		
		
	}
	
	
	public Topping getTopping(String toppingName){
		String queryString = "from Topping where topping = ?";
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery(queryString);
		query.setString(0,toppingName);
		Topping acquiredTopping = (Topping) query.uniqueResult();
		session.close();
		
		return acquiredTopping;
	}
	
	
	public Order getOrder(int order_id){
		String queryString = "from Order as o where o.orderId = ?";
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery(queryString);
		query.setInteger(0, order_id);
		Order acquiredOrder = (Order) query.uniqueResult();
		session.close();		
		return acquiredOrder;
	}
	
	
	public void getAllOrders(Customer customer){
			
		String queryString = "from Order as o where o.customer.userId=?";
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.flush();
		session.clear();
		//session.update(customer);
		Query query = session.createQuery(queryString);
		query.setInteger(0, customer.getUserId());
		List<Order> orders = query.list();
		for(Order o: orders){
			System.out.println(o.getCustomer().getUserName()+"--> orderId: "+o.getOrderId()+
					", orderPrice: "+o.getOrderPrice()+ ", Payment method: "+o.getPaymentMethod()+
					", Toppings -> " + Arrays.toString(o.getToppings().toArray())+" deliverytime: "+
					o.getDeliveryTime());
		}
		session.getTransaction().commit();
		
		
	}
	
	public void changeAnOrder(Customer customer){
		Scanner in = new Scanner(System.in);
		String queryString = "from Order as o where o.customer.userId=?";
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		//session.update(customer);
		Query query = session.createQuery(queryString);
		query.setInteger(0, customer.getUserId());
		List<Order> orders = query.list();
		for(Order o: orders){
			System.out.println(o.getCustomer().getUserName()+"--> orderId: "+o.getOrderId()+
					", orderPrice: "+o.getOrderPrice()+ ", Payment method: "+o.getPaymentMethod()+
					", Toppings -> " + Arrays.toString(o.getToppings().toArray())+" deliverytime: "+
					o.getDeliveryTime());
		}
		
		int orderId;
		 do {
			 System.out.println("Enter ID (from the above list) of the order to update a specifice order");
		        while (!in.hasNextInt()) {
		            System.out.println("That's not a number!");
		            in.next(); // this is important!
		        }
		        orderId = in.nextInt();
		    } while (orderId <= 0);
		 
		 Order retrievedOrder = getOrder(orderId);
		 in.nextLine();
		 session.close();
		 ///////////
		 double pizzaPrice=0;
//			order.setOrderPrice(34);
//			order.setPaymentMethod(PaymentMethod.MASTER);
//			order.setPizzaSize(PizzaSize.LARGE);
			System.out.println("Enter Payment Type");
			String type = in.nextLine();
			if(type.equalsIgnoreCase("VISA")){
				retrievedOrder.setPaymentMethod(PaymentMethod.VISA);
			}
			else if(type.equals("MASTER")){
				retrievedOrder.setPaymentMethod(PaymentMethod.MASTER);
			}
			else{
				retrievedOrder.setPaymentMethod(PaymentMethod.CASH);
			}
			
			System.out.println("Enter Pizza Size");
			String size = in.nextLine();
			if(size.equalsIgnoreCase("LARGE")){
				retrievedOrder.setPizzaSize(PizzaSize.LARGE);
				pizzaPrice+= PizzaSize.LARGE.getPizzaPrice();
			}
			else if(size.equalsIgnoreCase("MEDIUM")){
				retrievedOrder.setPizzaSize(PizzaSize.MEDIUM);
				pizzaPrice+= PizzaSize.MEDIUM.getPizzaPrice();
			} 
			else{
				retrievedOrder.setPizzaSize(PizzaSize.SMALL);
				pizzaPrice+= PizzaSize.SMALL.getPizzaPrice();
			}
			
			System.out.println("Would you like to add toppings (YES/NO)");
			String toppingResponse = in.nextLine();
			if(toppingResponse.equalsIgnoreCase("YES")){
				int maxTopping = 3;
				boolean addAnother = true;
				int toppingCounter = 0;
				while(maxTopping >0 && addAnother==true){
					System.out.println("Enter Topping (Pepperoni, Mushrooms, Onions, Sausage, Bacon, Extra cheese, Black olives, \nGreen peppers, Pineapple, Spinach)");
					String topping = in.nextLine();
					Topping newTopping = getTopping(topping);
					pizzaPrice+=newTopping.getToppingPrice();
					retrievedOrder.setToppings(newTopping);
					if(toppingCounter<=3)
					{
						System.out.println("Add another topping? (YES/NO)");
						String anotherToppingResponse = in.nextLine();
						if(anotherToppingResponse.equalsIgnoreCase("NO"))
							{
								addAnother=false;
							}
					}
					maxTopping--;
					toppingCounter++;
				}
			}
			else{
				System.out.println("No toppings will be added");
			}		
			
			retrievedOrder.setOrderPrice(pizzaPrice);
			//one to many part  -- might need to change
			customer.setOrder(retrievedOrder);
			retrievedOrder.setCustomer(customer);
			
			
			PizzaShopDAO pizzaShopDAO = new ConcretePizzaShopDAO();
			pizzaShopDAO.updateOrder(retrievedOrder);
		
		///////////
		
	}
	
	
	
	public void cancelOrder(Customer customer){
		Scanner in = new Scanner(System.in);
		String queryString = "from Order as o where o.customer.userId=?";
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.flush();
		session.clear();
		Query query = session.createQuery(queryString);
		query.setInteger(0, customer.getUserId());
		List<Order> orders = query.list();
		for(Order o: orders){
			System.out.println(o.getCustomer().getUserName()+"--> orderId: "+o.getOrderId()+
					", orderPrice: "+o.getOrderPrice()+ ", Payment method: "+o.getPaymentMethod()+
					", Toppings -> " + Arrays.toString(o.getToppings().toArray())+" deliverytime: "+
					o.getDeliveryTime());
		}
		
		int orderId;
		 do {
			 System.out.println("Enter ID (from the above list) of the order to update a specifice order");
		        while (!in.hasNextInt()) {
		            System.out.println("That's not a number!");
		            in.next(); // this is important!
		        }
		        orderId = in.nextInt();
		    } while (orderId <= 0);
		 
		 Order retrievedOrder = getOrder(orderId);
		 PizzaShopDAO pizzaShopDAO = new ConcretePizzaShopDAO();
		 pizzaShopDAO.deleteOrder(retrievedOrder);
	}
	

	
	
	
	
	
	
	
}
