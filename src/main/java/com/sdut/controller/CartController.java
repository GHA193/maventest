package com.sdut.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdut.model.Products;
import com.sdut.service.ProductService;
import com.sdut.utils.ProductUtils;

@Controller
public class CartController {

	@Autowired
	ProductService service;

	// 显示购物车页面
	@RequestMapping("showCart")
	public String showCart() {
		return "cart";
	}

	// 加入购物车
	@RequestMapping("addCart")
	@ResponseBody
	public String addCart(String id, HttpServletRequest request) {
		System.out.println(id);
		// 获取到要加入购物车的商品
		Products product = null;

		HttpSession session = request.getSession();
		// 先从session中获取购物车对象
		Map<Products, Integer> cart = (Map<Products, Integer>) session.getAttribute("cart");
		if (cart == null) {
			// 定义一个map集合作为购物车
			cart = new HashMap<Products, Integer>();
			product = service.findProductById(id);
			cart.put(product, 1);
		} else {
			// 判断要加入购物车的商品是否存在在购物车中
			Set<Products> keySet = cart.keySet();
			product = ProductUtils.findProduct(keySet, id);
			if (product == null) {
				product = service.findProductById(id);
				cart.put(product, 1);
			} else {
				if(cart.get(product)<product.getPnum()) {
					cart.put(product, cart.get(product) + 1);					
				}else {
					return "{\"msg\":\"full\"}";
				}
			}
		}

		// 将购物车对象放到session中
		session.setAttribute("cart", cart);

		return "{\"msg\":\"true\"}";
	}
	
	// 修改购物车
	@RequestMapping("updateCart")
	public String updateCart(String id, Integer count,HttpServletRequest request) {
		//获取到购物车对象
		HttpSession session = request.getSession();
		Map<Products, Integer> cart = (Map<Products, Integer>) session.getAttribute("cart");
		
		Set<Products> keySet = cart.keySet();
		Products product = ProductUtils.findProduct(keySet, id);
		
		cart.put(product, count);
		
		if(count==0) {
			cart.remove(product);
		}
		
		session.setAttribute("cart", cart);
		
		return "redirect:showCart";
	}

}
