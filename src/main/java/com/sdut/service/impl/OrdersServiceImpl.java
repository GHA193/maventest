package com.sdut.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdut.dao.OrderItemMapper;
import com.sdut.dao.OrdersMapper;
import com.sdut.model.OrderItem;
import com.sdut.model.Orders;
import com.sdut.service.OrderItemService;
import com.sdut.service.OrdersService;
@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersMapper mapper;
	
	@Autowired
	private OrderItemService service;
	
	
	@Override
	public int saveOrders(Orders order) {
		
		int num = mapper.saveOrders(order);
		// TODO Auto-generated method stub
		//保存订单项
		List<OrderItem> orderItems = order.getOrderItems();
		
		for (OrderItem orderItem : orderItems) {
			service.saveOrderItem(orderItem);
			
		}
		
		return num;
	}


	@Override
	public List<Orders> findOrdersByUserId(int id) {
		// TODO Auto-generated method stub
		return mapper.findOrdersByUserId(id);
	}

}
