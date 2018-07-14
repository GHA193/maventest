package com.sdut.utils;

import java.util.Set;

import com.sdut.model.Products;

public class ProductUtils {
	public static Products findProduct(Set<Products> set, String id) {
		System.out.println(set);
		for (Products products : set) {
			if (products.getId().equals(id)) {
				return products;
			}
		}
		
		return null;
	}
}
