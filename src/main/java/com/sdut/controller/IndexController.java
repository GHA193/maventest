package com.sdut.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sdut.model.PageBean;
import com.sdut.model.Products;
import com.sdut.service.ProductService;

@Controller
public class IndexController {

	@Autowired
	private ProductService productService;

	@RequestMapping("showIndex")
	public String showIndex(Model model, Integer page) {
		
		return showIndex1(model,page);
	}

	// 设置默认访问问题
	@RequestMapping("/")
	public String showIndex1(Model model, Integer page) {
		// 查询商品
		// List<Products> productList = productService.findProductList();

		// 获取商品的总记录数
		Integer count = productService.findCount();
		System.out.println(count);

		// 创建pageBean对象，设置页面内容
		PageBean pageBean = new PageBean(8, page, count);
		System.out.println(pageBean);

		// 调用具有分页功能的查询方法
		List<Products> productList = productService.findProductListPage(pageBean);
		// 将查询到的商品在首页面中进行展示
		model.addAttribute("productList", productList);
		model.addAttribute("pageBean", pageBean);

		return "index";
	}

	@RequestMapping("showAdminIndex")
	public String showAdminIndex(String id) {
		Products product = productService.findProductById(id);

		return "admin/index";
	}

	@RequestMapping("showInfoIndex")
	public String showInfoIndex(String id, Model model) {
		// 查询商品
		Products product = productService.findProductById(id);

		// 将查询到的商品在首页面中进行展示
		model.addAttribute("product", product);

		return "productinfo";
	}

}
