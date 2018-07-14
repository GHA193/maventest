package com.sdut.dao;

import java.util.List;

import com.sdut.model.Orders;

public interface OrdersMapper {
	
	public int saveOrders(Orders order);
	
	public List<Orders> findOrdersByUserId(int id);
	
}
