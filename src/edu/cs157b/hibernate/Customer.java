//ONE SIDED CUSTOMER  -----> MANY SIDED ORDER (ONE TO MANY)
package edu.cs157b.hibernate;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "customer_info")

public class Customer {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="customer_id")
	private int userId; 
	
	@Column(name = "username",unique=true)
	private String username; 
	
	@Column(name = "password")
	private String password; 
	
	
	@Column(name = "address")
	private Address address;
	
	@OneToMany(mappedBy="customer", targetEntity= Order.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Order> orders;
	
	
	
	public int getUserId() {
		return userId;
	}
	
	public Customer(){
		orders = new ArrayList<Order>();
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	
	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String toString() {
		return userId+ " "+ username + " " + password + " " + address; 
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrder(Order order) {
		orders.add(order);
	}
	
	
	
}
