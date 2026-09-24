package in.main.entities;

public class Dashboard {
	  private double totalRevenue;
	  private double totalOrders;
	  private double totalProducts;
	  private double totalUsers;
	  public double getTotalRevenue() {
		  return totalRevenue;
	  }
	  public void setTotalRevenue(double totalRevenue) {
		  this.totalRevenue = totalRevenue;
	  }
	  public double getTotalOrders() {
		  return totalOrders;
	  }
	  public void setTotalOrders(double totalOrders) {
		  this.totalOrders = totalOrders;
	  }
	  public double getTotalProducts() {
		  return totalProducts;
	  }
	  public void setTotalProducts(double totalProducts) {
		  this.totalProducts = totalProducts;
	  }
	  public double getTotalUsers() {
		  return totalUsers;
	  }
	  public void setTotalUsers(double totalUsers) {
		  this.totalUsers = totalUsers;
	  }
	  public Dashboard(double totalRevenue, double totalOrders, double totalProducts, double totalUsers) {
		super();
		this.totalRevenue = totalRevenue;
		this.totalOrders = totalOrders;
		this.totalProducts = totalProducts;
		this.totalUsers = totalUsers;
	  }
	  public Dashboard() {
		  
	  }
}
