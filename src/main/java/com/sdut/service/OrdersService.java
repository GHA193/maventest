package com.sdut.service;

import java.util.List;

import com.sdut.model.Orders;

public interface OrdersService {
	public int saveOrders(Orders order);
	public List<Orders> findOrdersByUserId(int id);
}
