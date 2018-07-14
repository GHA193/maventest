package com.sdut.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.sdut.model.PageBean;
import com.sdut.model.Products;
import com.sdut.service.ProductService;
import com.sdut.utils.UUIDUtils;
import com.sdut.utils.UploadUtils;

@Controller
public class ProductController {
	@Autowired
	private ProductService proService;
	
	//打开商品页面
	@RequestMapping("showProductList")
	public String showProductList(Model model) {
		//查询所有的商品
		List<Products> productList = proService.findProductList();
		
		//将查询到的商品传递到页面中展示
		//相当于request.setAttribute
		model.addAttribute("productList",productList);
		
		return "admin/product";
	}
	//打开添加商品的页面
	@RequestMapping("showAddProduct")
	public String showAddProduct() {
		return "admin/addProduct";
	}
	
	//添加商品
	@RequestMapping("saveProduct")
	public String saveProduct(Products pro, MultipartFile imgpic) throws Exception {
		System.out.println(pro);
		System.out.println(imgpic.getOriginalFilename());
		//将上传到的文件上传到服务器
		//获取上传文件的路径
		String upload = UploadUtils.upload(imgpic);
		
		//将路径保存到数据库中
		pro.setId(UUIDUtils.getUUID());
		pro.setImgurl(upload);
		//pro.setState(1);
		
		proService.saveProduct(pro);
		
		return "redirect:showProductList";
	}
	//删除商品
	@RequestMapping("delProduct")
	public String delProduct(String id) {
		
		proService.delProduct(id);
		
		
		
		return "redirect:showProductList";
	}
	
	//打开修改商品页面
	@RequestMapping("showEditProduct")
	public String showEditProduct(String id, Model model) {
		
		//查询要修改的商品，
		Products product = proService.findProductById(id);
		
		//将商品传到页面
		model.addAttribute("product", product);
		
		
		return "admin/editproduct";
	}
	@RequestMapping("updateProduct")
	public String updateProduct(Products pro, MultipartFile imgpic) throws Exception {
		System.out.println("***************");
		System.out.println(pro);
		System.out.println("***************");
		
		if(!imgpic.getOriginalFilename().equals("")) {
			System.out.println(imgpic.getOriginalFilename());
			//将上传到的文件上传到服务器
			//获取上传文件的路径
			String upload = UploadUtils.upload(imgpic);
			pro.setImgurl(upload);
		}
		
		proService.updateProduct(pro);
		
		
		return "redirect:showProductList";
	}
	
	//打开商品展示页面
	@RequestMapping("showProductkinds")
	public String showProductkinds(String type, Model model, Integer page) {
		
		int count = proService.findCountByType(type);
		System.out.println(count+"---------------");
		
		PageBean pageBean = new PageBean(8, page, count);
		//System.out.println(type);
		//查询要显示的类型的商品
		List<Products> productList = proService.findProductListByType(type,pageBean);
		//将商品传递到页面进行展示
		model.addAttribute("productList", productList);
		model.addAttribute("category", type);
		model.addAttribute("pageBean", pageBean);
		
		return "productkinds";
	}
	
	
	
}
