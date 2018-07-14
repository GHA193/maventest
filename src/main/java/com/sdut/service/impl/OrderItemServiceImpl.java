package com.sdut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdut.dao.OrderItemMapper;
import com.sdut.model.OrderItem;
import com.sdut.service.OrderItemService;
@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemMapper mapper;
	
	@Override
	public int saveOrderItem(OrderItem item) {
		// TODO Auto-generated method stub
		return mapper.saveOrderItem(item);
	}

}
