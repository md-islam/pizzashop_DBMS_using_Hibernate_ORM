package edu.cs157b.hibernate;

import java.util.Scanner;

public class PresentationLayer {

	public static void main (String[] args){
		
		System.out.println("=========== WELCOME TO PIZZA SHOP ============");
		//persistToppings();
		startPizzaShopApplicationWelcomePage();
		
		
	}
	
	public static void persistToppings(){
		ServiceLayer serviceLayer = new ServiceLayer();
		serviceLayer.createToppings();
				
	}
	
	public static void startPizzaShopApplicationWelcomePage(){
		
		Scanner in = new Scanner(System.in);
		ServiceLayer servicelayer = new ServiceLayer();
		int choice; 
		
//		System.out.println("1. To sign in \n"+
//			   "2. To log in \n"+
//			   "3. To make a regular order \n" +
//			   "4. To make a discounted order \n"+
//			   "5. To view all orders \n"+
//			   "6. To change an order \n"+
//			   "7. To cancel an order \n");
		
		System.out.println("1. Sign up \n"
								+ "2. Log in "); 
		
		do{
			choice = in.nextInt();
			switch(choice){
			
			case 1: 
				System.out.println("You will love being a member here");
				servicelayer.createCustomer();
				startPizzaShopApplicationWelcomePage();  
			case 2:
				in.nextLine();
				Customer authenticatedCustomer = null; 
				boolean isAuthenticated = false;
				while(!isAuthenticated){
					System.out.println("Please enter your profile credentials");
					System.out.println("enter username"); 
					String username = in.nextLine();
					System.out.println("enter password");
					String password = in.nextLine();
					 
					authenticatedCustomer = servicelayer.getAuthenticatedCustomer(username, password);
					
					
					
					if(authenticatedCustomer != null)
					{
						isAuthenticated = true;
					}
					if(isAuthenticated == true){
						System.out.println("You are logged in");
						showLoggedInMenu(authenticatedCustomer);}
					else{
						System.out.println("You're not logged in or you're not a member");
					}
				}
				break; 
			default: 
				System.out.println("Invalid choice");
			}
		}while(choice<1 || choice >2);
	}
	
	
	public static void showLoggedInMenu(Customer authenticatedCustomer){
		
		ServiceLayer servicelayer = new ServiceLayer();
		Scanner in = new Scanner(System.in);
		 
		
		System.out.println("1. Make a regular order \n"+
		   "2. Make a discounted order \n"+
		   "3. View All orders \n" +
		   "4. Change an order \n"+
		   "5. Cancel an order \n"+
		   "6. Log out! \n"+
		   "7. EXIT!");
		int choice;
		do{
			choice=in.nextInt();
			switch(choice){
			case 1:
				servicelayer.makeOrder(authenticatedCustomer);
				showLoggedInMenu(authenticatedCustomer);
				
				
			case 2: 
				servicelayer.makeDiscountedOrder(authenticatedCustomer);
				showLoggedInMenu(authenticatedCustomer);
				
			case 3:
				// view all orders with toppings   
				System.out.println("Viewing all orders");
				servicelayer.getAllOrders(authenticatedCustomer);
				showLoggedInMenu(authenticatedCustomer);
				
				
				
			case 4: 
				System.out.println("Change an order");
				servicelayer.changeAnOrder(authenticatedCustomer);
				showLoggedInMenu(authenticatedCustomer);
				
				
			case 5:
				System.out.println("Cancel an order");
				servicelayer.cancelOrder(authenticatedCustomer);
				showLoggedInMenu(authenticatedCustomer);
				
				
			case 6: 
				System.out.println("Logging out");
				startPizzaShopApplicationWelcomePage();
				
			case 7: 
				System.exit(0);
			default:
				System.out.println("Invalid choice! Enter a valid choice");
				break;
			}
			
			in.close();
			
		}while(choice<1 || choice>7);
		
		
		
	}
	
}
