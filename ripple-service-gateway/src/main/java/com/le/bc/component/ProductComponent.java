package com.le.bc.component;

import com.le.bc.entity.Product;
import com.le.bc.file.PropertyUtil;

/**
 * Document Start 
 * 产品组件
 * Document End 
 * Author: songwenpeng@le.com
 * Time: 2016年9月16日 下午1:08:57
 */
public class ProductComponent {

	/**
	 * Document Start 
	 * 根据产品码获取产品信息
	 * 不存在产品信息则返回Null
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月16日 下午1:12:45
	 * @param productCode
	 * @return
	 */
	public static Product getProductInfoByProductCode(String productCode) {
		String key = "Product_Info_" + productCode;
		String value = PropertyUtil.getPropertyByName(key);
		if (value == null) {
			return null;
		}

		String[] values = value.split("_");

		Product product = new Product();
		product.setCode(productCode);
		product.setCurrency(values[0]);
		product.setIssuer(values[1]);

		return product;
	}

}
