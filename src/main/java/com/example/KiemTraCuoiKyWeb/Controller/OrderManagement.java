package com.example.KiemTraCuoiKyWeb.Controller;


public class OrderManagement {
	private Order order;
	private OrderDetail orderDetail;
	private Customer customer;
	private Product product;
	
	public OrderManagement() {
		order = new Order();
		orderDetail = new OrderDetail();
		customer = new Customer();
		product = new Product();
	}

	public OrderManagement(Order order, OrderDetail orderDetail, Customer customer,Product product) {
		this.order = order;
		this.orderDetail = orderDetail;
		this.customer = customer;
		this.product = product;
		
	}
	
	

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}	
	
	
}
