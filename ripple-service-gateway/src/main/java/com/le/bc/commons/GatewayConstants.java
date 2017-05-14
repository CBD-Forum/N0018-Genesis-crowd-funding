package com.le.bc.commons;

/**
 * Document Start 
 * 网关相关常量
 * Document End 
 * songwenpeng@letv.com 
 * 乐视控股（北京）有限公司
 * 2016年7月3日 下午2:43:46
 */
public interface GatewayConstants {

	///Service Name
	//开户
	public static final String CREATE_ACCOUNT = "createAccount";
	//激活账户
	public static final String ACTIVE_ACCOUNT = "activeAccount";
	//添加信任
	public static final String SET_TRUST = "setTrust";
	//转账交易
	public static final String TRANSFER = "transfer";
	//开户账户查询
	public static final String ACCOUNT_XRP_QUERY = "accountXRPQuery";
	//产品账户余额查询
	public static final String ACCOUNT_QUERY = "accountQuery";
	//事务查询
	public static final String TRANSACTION_QUERY = "transactionQuery";
	
	///Parameters Name
	public static final String SERVICE_ID = "serviceID";
	public static final String PRODUCT_CODE = "productCode";
	public static final String ADDRESS = "address";
	public static final String KEY = "key";
	public static final String STATUS = "status";
	public static final String MESSAGE = "message";
	public static final String NOTIFYURL = "notifyURL";
	public static final String TRANSACTION_ID = "transactionID";
	public static final String AMOUNT = "amount";
	public static final String CURRENCY = "currency";
	public static final String SOURCE_ADDRESS = "sourceAddress";
	public static final String SOUCRCE_KEY = "sourceKey";
	public static final String TARGET_ADDRESS = "targetAddress";
	public static final String TRANSFER_NO = "transferNO";
	public static final String UID = "UID";
	public static final String REQUEST_ID = "requestID";

}
