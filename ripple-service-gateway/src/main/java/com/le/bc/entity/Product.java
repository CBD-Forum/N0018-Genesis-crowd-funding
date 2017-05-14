package com.le.bc.entity;

/**
 * Document Start 
 * 产品实体
 * Document End 
 * Author: songwenpeng@le.com
 * Time: 2016年9月16日 下午1:10:45
 */
public class Product {

	private String code;//产品代码
	private String currency;//产品代币符号 
	private String issuer;//产品发行地址

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

}
