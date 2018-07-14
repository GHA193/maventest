package com.sdut.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sdut.model.OrderItem;
import com.sdut.model.Orders;
import com.sdut.model.Products;
import com.sdut.model.Users;
import com.sdut.service.OrdersService;
import com.sdut.utils.DateUtils;
import com.sdut.utils.UUIDUtils;

/**
 * 打开订单生成页面
 * 
 * @author Administrator
 *
 */
@Controller
public class OrderController {

	@Autowired
	private OrdersService service;

	// 打开创建订单页面
	@RequestMapping("showCreateOrder")
	public String showCreateOrder() {

		return "order";
	}

	// 生成订单
	@RequestMapping("createOrder")
	public String createOrder(Orders order, HttpServletRequest request) {
		System.out.println(order);
		// 只获取到了金额和收货地信息，补充订单的其他信息
		String id = UUIDUtils.getUUID();
		order.setId(id);
		order.setOrdertime(DateUtils.formatDate(new Date()));
		order.setPaystate(0);// 0 未支付 1已支付
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		order.setUserId(user.getId());

		// 获取购物车信息
		Map<Products, Integer> cart = (Map<Products, Integer>) session.getAttribute("cart");
		Set<Products> keySet = cart.keySet();

		List<OrderItem> items = new ArrayList<OrderItem>();

		for (Products products : keySet) {
			// 设置订单项信息
			OrderItem item = new OrderItem();
			item.setOrderId(id);
			item.setProductId(products.getId());
			item.setBuynum(cart.get(products));

			items.add(item);
		}
		order.setOrderItems(items);

		service.saveOrders(order);

		session.removeAttribute("cart");

		// 结算完成暂时跳转到首页，订单结算功能到此为止
		return "redirect:showOrders";
		// return "index";
	}

	// 打开创建订单页面
	@RequestMapping("showOrders")
	public String showOrders(HttpServletRequest request) {
		//查询当前用户所有的订单
		Users user = (Users) request.getSession().getAttribute("user");
		
		List<Orders> list = service.findOrdersByUserId(user.getId());
//		for (Orders orders : list) {
//			System.out.println(orders);
//			System.out.println("----------------");
//			List<OrderItem> orderItems = orders.getOrderItems();
//			for (OrderItem orderItem : orderItems) {
//				System.out.println(orderItem.getProduct());
//				System.out.println("-----------");
//			}
//		}
		
		//将查到的信息传到页面
		request.setAttribute("list", list);
		
		
		return "showorder";
	}
}
