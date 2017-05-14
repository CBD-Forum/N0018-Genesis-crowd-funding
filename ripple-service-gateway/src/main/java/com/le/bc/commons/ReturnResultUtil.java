package com.le.bc.commons;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.le.bc.component.ProductComponent;
import com.le.bc.http.HttpClientWithPoolUtil;
import com.ripple.client.responses.Response;

/**
 * Document Start 
 * 返回结果处理
 * Document End 
 * Author: songwenpeng@le.com
 * Time: 2016年9月29日 下午12:56:57
 */
public class ReturnResultUtil {
	public static Logger logger = LogManager.getLogger(ReturnResultUtil.class);

	//以下返回码为应用服务器返回
	public static String SERVICE_SUCCESS_CODE = "ServiceSuccess";
	public static String SERVICE_SUCCESS_MESSAGE = "服务器响应：调用成功，不表示业务成功";
	public static String SERVICE_PARAMETER_ERROR_CODE = "ServiceParameterError";
	public static String SERVICE_PARAMETER_ERROR_MESSAGE = "服务器响应：参数错误";
	public static String SERVICE_PRODUCT_ISNOTEXIST_CODE = "ServiceProductIsNotExist";
	public static String SERVICE_PRODUCT_ISNOTEXIST_MESSAGE = "服务器响应：产品不存在";
	public static String SERVICE_EXCEPTION_CODE = "ServiceException";
	public static String SERVICE_EXCEPTION_MESSAGE = "服务器响应：服务异常";

	//以下返回码为提交到全球账本返回
	public static String SUBMIT_GLOBALLEDGER_SUCCESS_CODE = "SubmitGlobalLedgerSuccess";
	public static String SUBMIT_GLOBALLEDGER_SUCCESS_MESSAGE = "分布式账本响应：提交成功";
	public static String SUBMIT_GLOBALLEDGER_FIAL_CODE = "SubmitGlobalLedgerFail";
	public static String SUBMIT_GLOBALLEDGER_FIAL_MESSAGE = "分布式账本响应：提交失败";

	//以下返回码为共识结论返回
	public static String CONSENSUS_SUCCESS_CODE = "ConsensusSuccess";
	public static String CONSENSUS_SUCCESS_MESSAGE = "共识响应：事务成功";
	public static String CONSENSUS_FIAL_CODE = "ConsensusFail";
	public static String CONSENSUS_FIAL_MESSAGE = "共识响应：事务失败";
	public static String CONSENSUS_PROCESS_CODE = "ConsensusProcess";
	public static String CONSENSUS_PROCESS_MESSAGE = "共识响应：事务处理中";

	/**
	 * Document Start 
	 * 交易提交回调结果处理
	//	   {
	//	    "result": {
	//	        "tx_json": {
	//	            "Account": "rraBDW7zYFUUcJoVyC6Dk9jZYQxf6V1Mkj",
	//	            "Destination": "rUhkoFgPEJXEN7yYxYqkTZA3HUCJm422Mj",
	//	            "TransactionType": "Payment",
	//	            "TxnSignature": "3045022100A0DAD123FD7180AEC1E5B726E91B5CDF35A7F686A9347E9696E3A96A5004696C0220485ABA1A81B9F1A1D6D5EC3558D2998ED17CF1CA78B833111C4D7851B2B7B345",
	//	            "SigningPubKey": "0343B79C8821E986D2C0579352AF4DC5F1C8C5917B9ACBC1C9742DF68A5ED46BBB",
	//	            "Amount": "20000000",
	//	            "Fee": "10",
	//	            "Flags": 2147483648,
	//	            "Sequence": 101,
	//	            "hash": "819E42C4B4EF2D94268D9D17EB901CB4407E47B69BE695631155CB7F91D38A30"
	//	        },
	//	        "engine_result_code": 0,
	//	        "tx_blob": "12000022800000002400000065614000000001312D0068400000000000000A73210343B79C8821E986D2C0579352AF4DC5F1C8C5917B9ACBC1C9742DF68A5ED46BBB74473045022100A0DAD123FD7180AEC1E5B726E91B5CDF35A7F686A9347E9696E3A96A5004696C0220485ABA1A81B9F1A1D6D5EC3558D2998ED17CF1CA78B833111C4D7851B2B7B345811400FA9B7F6C173DA71141FEE1A78E36BCE87B78508314795F3331E3703194E40500782F262D8BA7FCA424",
	//	        "engine_result": "tesSUCCESS",
	//	        "engine_result_message": "The transaction was applied. Only final in a validated ledger."
	//	    },
	//	    "id": 0,
	//	    "type": "response",
	//	    "status": "success"
	//	}
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月17日 下午8:39:53
	 * @param response
	 * @param sourceAddress
	 * @param targetAddress
	 * @param productCode
	 * @param value
	 * @param transferNO
	 * @param notifyURL
	 */
	public static void transactionXRPCallBack(Response response, String requestID, String callBackURL) {
		RequestAndResponseLogUtil.rippleAsynResponseLog(logger, response.message.toString(), requestID);
		//格式转换
		String result = response.message.toString();
		JSONObject resultJSON = JSON.parseObject(result);

		//判断是否成功
		String submitStatus = resultJSON.getString("status");
		String engineStatus = resultJSON.getJSONObject("result").getString("engine_result");
		String engineStatusMessages = resultJSON.getJSONObject("result").getString("engine_result_message");

		JSONObject txJson = resultJSON.getJSONObject("result").getJSONObject("tx_json");
		JSONObject object = new JSONObject();

		//判断提交全球账本状态
		//提交成功
		if (submitStatus.equalsIgnoreCase("success") && engineStatus.equalsIgnoreCase("tesSUCCESS")) {
			object.put(GatewayConstants.ADDRESS, txJson.getString("Destination"));
			object.put(GatewayConstants.TRANSACTION_ID, txJson.getString("hash"));
			object.put(GatewayConstants.AMOUNT, txJson.getString("Amount"));
			object.put(GatewayConstants.REQUEST_ID, requestID);
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SUBMIT_GLOBALLEDGER_SUCCESS_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SUBMIT_GLOBALLEDGER_SUCCESS_MESSAGE);
		} else {
			object.put(GatewayConstants.ADDRESS, "");
			object.put(GatewayConstants.TRANSACTION_ID, "");
			object.put(GatewayConstants.AMOUNT, "");
			object.put(GatewayConstants.REQUEST_ID, requestID);
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SUBMIT_GLOBALLEDGER_FIAL_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SUBMIT_GLOBALLEDGER_FIAL_MESSAGE + " {result:" + engineStatus + ",result_message:" + engineStatusMessages + "}");
		}
		//发送异步响应消息
		ReturnResultUtil.callBackRequest(callBackURL, object, requestID);
	}

	/**
	 * Document Start 
	 * 回调方法
	//       {
	//	    "result": {
	//	        "tx_json": {
	//	            "Account": "rraBDW7zYFUUcJoVyC6Dk9jZYQxf6V1Mkj",
	//	            "Destination": "rHa3trZg2MzGTkGA4PLmsTPo9azHUGbhUV",
	//	            "TransactionType": "Payment",
	//	            "TxnSignature": "304402201217A801BDC977FEADD11CEC161D35D7606DA9ABF47E882760AE625609612B53022002E6D1191DF934FA145F60DEB68F9A8C2AE953B34416D970DD841C671C13C540",
	//	            "SigningPubKey": "0343B79C8821E986D2C0579352AF4DC5F1C8C5917B9ACBC1C9742DF68A5ED46BBB",
	//	            "Amount": {
	//	                "currency": "NBI",
	//	                "value": "11",
	//	                "issuer": "rLDykZiJ2Zm6UB937jfZPiRnnh85X7CBCS"
	//	            },
	//	            "Fee": "5400",
	//	            "Flags": 2147483648,
	//	            "Sequence": 99,
	//	            "hash": "87220FE50F0BDFD3FC55BA8D943C8D61AD7FA631644D14FB46E7E1179CCD906F"
	//	        },
	//	        "engine_result_code": 0,
	//	        "tx_blob": "1200002280000000240000006361D4C3E871B540C0000000000000000000000000004E42490000000000D2DEC14151E399313F62FB7B91440B56CC01C57B68400000000000151873210343B79C8821E986D2C0579352AF4DC5F1C8C5917B9ACBC1C9742DF68A5ED46BBB7446304402201217A801BDC977FEADD11CEC161D35D7606DA9ABF47E882760AE625609612B53022002E6D1191DF934FA145F60DEB68F9A8C2AE953B34416D970DD841C671C13C540811400FA9B7F6C173DA71141FEE1A78E36BCE87B78508314B07AAB786C4A4E9AF0D80B068D9226476E7C0D55",
	//	        "engine_result": "tesSUCCESS",
	//	        "engine_result_message": "The transaction was applied. Only final in a validated ledger."
	//	    },
	//	    "id": 0,
	//	    "type": "response",
	//	    "status": "success"
	//	}
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月16日 下午2:52:57
	 * @param response
	 * @param sourceAddress
	 * @param targetAddress
	 * @param productCode
	 * @param value
	 * @param transferNO
	 * @param notifyURL
	 */
	public static void transferCallBack(Response response, String requestID, String callBackURL, String transferNO) {
		RequestAndResponseLogUtil.rippleAsynResponseLog(logger, response.message.toString(), requestID);
		//格式转换
		String result = response.message.toString();
		JSONObject resultJSON = JSON.parseObject(result);

		//判断是否成功
		String submitStatus = resultJSON.getString("status");
		String engineStatus = resultJSON.getJSONObject("result").getString("engine_result");
		String engineStatusMessages = resultJSON.getJSONObject("result").getString("engine_result_message");

		JSONObject txJson = resultJSON.getJSONObject("result").getJSONObject("tx_json");
		JSONObject object = new JSONObject();

		//判断提交全球账本状态
		//提交成功
		if (submitStatus.equalsIgnoreCase("success") && engineStatus.equalsIgnoreCase("tesSUCCESS")) {
			object.put(GatewayConstants.SOURCE_ADDRESS, txJson.getString("Account"));
			object.put(GatewayConstants.TARGET_ADDRESS, txJson.getString("Destination"));
			object.put(GatewayConstants.AMOUNT, txJson.getJSONObject("Amount").getString("value"));
			object.put(GatewayConstants.CURRENCY, txJson.getJSONObject("Amount").getString("currency"));
			object.put(GatewayConstants.TRANSFER_NO, transferNO);
			object.put(GatewayConstants.TRANSACTION_ID, txJson.getString("hash"));
			object.put(GatewayConstants.REQUEST_ID, requestID);
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SUBMIT_GLOBALLEDGER_SUCCESS_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SUBMIT_GLOBALLEDGER_SUCCESS_MESSAGE);
		} else {
			object.put(GatewayConstants.SOURCE_ADDRESS, "");
			object.put(GatewayConstants.TARGET_ADDRESS, "");
			object.put(GatewayConstants.AMOUNT, "");
			object.put(GatewayConstants.CURRENCY, "");
			object.put(GatewayConstants.TRANSFER_NO, transferNO);
			String hashID = txJson.getString("hash");
			if (hashID == null || hashID.equalsIgnoreCase("")) {
				object.put(GatewayConstants.TRANSACTION_ID, "");
			} else {
				object.put(GatewayConstants.TRANSACTION_ID, hashID);
			}
			object.put(GatewayConstants.REQUEST_ID, requestID);
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SUBMIT_GLOBALLEDGER_FIAL_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SUBMIT_GLOBALLEDGER_FIAL_MESSAGE + " {result:" + engineStatus + ",result_message:" + engineStatusMessages + "}");
		}
		//发送异步响应消息
		ReturnResultUtil.callBackRequest(callBackURL, object, requestID);
	}

	/**
	 * Document Start 
	 * 设置信任线异步回调
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月19日 下午10:19:47
	 * @param response
	 * @param address
	 * @param productCode
	 */
	public static void trustSetCallback(Response response, String requestID, String callBackURL) {
		RequestAndResponseLogUtil.rippleAsynResponseLog(logger, response.message.toString(), requestID);
		//		{
		//		    "result": {
		//		        "tx_json": {
		//		            "Account": "rnwDpX4WBWnhYztJDu2599GqAxPpg5htjV",
		//		            "TransactionType": "TrustSet",
		//		            "TxnSignature": "304402203F1C9E48A2DD45A64F74159FC5CA6AF3B6E23866190C90AD7A8D12C942DA568F0220411E881805F9B0D17D22B795094C8C67E5E7E5423C63D6E755F52DB45130D051",
		//		            "LimitAmount": {
		//		                "currency": "RMB",
		//		                "value": "1000000000",
		//		                "issuer": "rLbppnUciWQ2AfLkcdTfBwb1yh97Kc9cfx"
		//		            },
		//		            "SigningPubKey": "020C1757F67F73619B0D8F9145769B8C7FDFD8145DF6EE3A3FCAD73414C147CCCE",
		//		            "Fee": "6008",
		//		            "Flags": 2147483648,
		//		            "Sequence": 2,
		//		            "hash": "1D2AACA3EA4C0E86E77A50AB488E8A75FCF8AE7FF065C6C1D74E57A32CD7259D"
		//		        },
		//		        "engine_result_code": 0,
		//		        "tx_blob": "1200142280000000240000000263D6C38D7EA4C68000000000000000000000000000524D420000000000D6D961FE36914895E7FE6214D470355EF7BABC4A6840000000000017787321020C1757F67F73619B0D8F9145769B8C7FDFD8145DF6EE3A3FCAD73414C147CCCE7446304402203F1C9E48A2DD45A64F74159FC5CA6AF3B6E23866190C90AD7A8D12C942DA568F0220411E881805F9B0D17D22B795094C8C67E5E7E5423C63D6E755F52DB45130D05181142D9E492B33D2F71A4504A109611C187CF3BCC1D5",
		//		        "engine_result": "tesSUCCESS",
		//		        "engine_result_message": "The transaction was applied. Only final in a validated ledger."
		//		    },
		//		    "id": 0,
		//		    "type": "response",
		//		    "status": "success"
		//		}
		//格式转换
		String result = response.message.toString();
		JSONObject resultJSON = JSON.parseObject(result);

		//判断是否成功
		String submitStatus = resultJSON.getString("status");
		String engineStatus = resultJSON.getJSONObject("result").getString("engine_result");

		JSONObject txJson = resultJSON.getJSONObject("result").getJSONObject("tx_json");
		JSONObject object = new JSONObject();

		//判断提交全球账本状态
		//提交成功
		if (submitStatus.equalsIgnoreCase("success") && engineStatus.equalsIgnoreCase("tesSUCCESS")) {
			object.put(GatewayConstants.ADDRESS, txJson.getString("Account"));
			object.put(GatewayConstants.CURRENCY, txJson.getJSONObject("LimitAmount").getString("currency"));
			object.put(GatewayConstants.TRANSACTION_ID, txJson.getString("hash"));
			object.put(GatewayConstants.REQUEST_ID, requestID);
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SUBMIT_GLOBALLEDGER_SUCCESS_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SUBMIT_GLOBALLEDGER_SUCCESS_MESSAGE);
		} else {
			object.put(GatewayConstants.ADDRESS, "");
			object.put(GatewayConstants.CURRENCY, "");
			object.put(GatewayConstants.TRANSACTION_ID, "");
			object.put(GatewayConstants.REQUEST_ID, requestID);
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SUBMIT_GLOBALLEDGER_FIAL_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SUBMIT_GLOBALLEDGER_FIAL_MESSAGE);
		}
		//发送异步响应消息
		ReturnResultUtil.callBackRequest(callBackURL, object, requestID);

	}

	/**
	 * Document Start 
	 * 处理创建账户异步回调
	//		{
	//		    "result": {
	//		        "public_key": "aBQzH7xjcHgh8VNsav1YgfbdEWPQMuc4o8f1DFJDy6kGv2c3L6AZ",
	//		        "master_seed_hex": "B39C3F0FEC62935222D3CF3D9EE8A756",
	//		        "account_id": "rNnNFm1jZUtDHQdpWWsJY7TBVaYkFP1aeT",
	//		        "public_key_hex": "038F23B107A18174E9BB36D8343C558CACEB488FD6486264649E406677A03820A7",
	//		        "key_type": "secp256k1",
	//		        "master_seed": "snUSa3WK1HU3ds76pmEZttUDAMgJx",
	//		        "master_key": "BERT TAN MAW EMIT WONT GURU BARB HOST MILD IRE WELL BLOT"
	//		    },
	//		    "id": 2,
	//		    "type": "response",
	//		    "status": "success"
	//		}
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月4日 下午3:56:58
	 * @param notifyURL
	 * @param object
	 */
	public static void walletProposeCallback(Response response, String requestID, String uid, String callBackURL) {
		RequestAndResponseLogUtil.rippleAsynResponseLog(logger, response.message.toString(), requestID);

		//格式转换
		String result = response.message.toString();
		JSONObject resultJSON = JSON.parseObject(result).getJSONObject("result");

		//判断是否成功
		String submitStatus = JSON.parseObject(result).getString("status");

		JSONObject object = new JSONObject();

		//判断提交全球账本状态
		//提交成功
		if (submitStatus.equalsIgnoreCase("success")) {
			object.put(GatewayConstants.ADDRESS, resultJSON.getString("account_id"));
			object.put(GatewayConstants.KEY, resultJSON.getString("master_seed"));
			object.put(GatewayConstants.UID, uid);
			object.put(GatewayConstants.REQUEST_ID, requestID);
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SUBMIT_GLOBALLEDGER_SUCCESS_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SUBMIT_GLOBALLEDGER_SUCCESS_MESSAGE);
		} else {
			object.put(GatewayConstants.ADDRESS, "");
			object.put(GatewayConstants.KEY, "");
			object.put(GatewayConstants.UID, uid);
			object.put(GatewayConstants.REQUEST_ID, requestID);
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SUBMIT_GLOBALLEDGER_FIAL_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SUBMIT_GLOBALLEDGER_FIAL_MESSAGE);
		}
		//发送异步响应消息
		ReturnResultUtil.callBackRequest(callBackURL, object, requestID);
	}

	/**
	 * Document Start 
	 * accountInfo回调
	//	{
	//	    "result": {
	//	        "ledger_current_index": 24112232,
	//	        "validated": false,
	//	        "account_data": {
	//	            "Account": "rHa3trZg2MzGTkGA4PLmsTPo9azHUGbhUV",
	//	            "OwnerCount": 1,
	//	            "PreviousTxnLgrSeq": 23001638,
	//	            "LedgerEntryType": "AccountRoot",
	//	            "index": "AAB239FD522363730614137FA02DDDD0E9059CEB4FBB104C5149CF513366C359",
	//	            "PreviousTxnID": "66ED8392753A1A3A1BC920C2C98A6902C1C9C6AD606715A36A5347252FBF9D8C",
	//	            "Flags": 0,
	//	            "Sequence": 3,
	//	            "Balance": "19809999976"
	//	        }
	//	    },
	//	    "id": 0,
	//	    "type": "response",
	//	    "status": "success"
	//	}
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月16日 上午2:14:23
	 * @param response
	 * @param address
	 * @param productCode
	 * @param callBackURL
	 */
	public static void accountInfoCallback(Response response, String requestID, String callBackURL) {
		RequestAndResponseLogUtil.rippleAsynResponseLog(logger, response.message.toString(), requestID);
		String result = response.message.toString();
		JSONObject resultJSON = JSON.parseObject(result);
		JSONObject object = new JSONObject();
		//判断是否成功
		//提交状态
		String submitStatus = resultJSON.getString("status");
		//		Boolean validateStatus = resultJSON.getJSONObject("result").getBoolean("validated");

		//成功
		if (submitStatus.equalsIgnoreCase("success")) {
			//			if (validateStatus.booleanValue()) {
			object.put(GatewayConstants.ADDRESS, resultJSON.getJSONObject("result").getJSONObject("account_data").getString("Account"));
			object.put(GatewayConstants.AMOUNT, resultJSON.getJSONObject("result").getJSONObject("account_data").getString("Balance"));
			object.put(GatewayConstants.CURRENCY, "XRP");
			object.put(GatewayConstants.REQUEST_ID, requestID);
			object.put(GatewayConstants.STATUS, ReturnResultUtil.CONSENSUS_SUCCESS_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.CONSENSUS_SUCCESS_MESSAGE);
			//			} else {
			//				object.put(GatewayConstants.ADDRESS, "");
			//				object.put(GatewayConstants.AMOUNT, "");
			//				object.put(GatewayConstants.CURRENCY, "XRP");
			//				object.put(GatewayConstants.STATUS, ReturnResultUtil.CONSENSUS_FIAL_CODE);
			//				object.put(GatewayConstants.MESSAGE, ReturnResultUtil.CONSENSUS_FIAL_MESSAGE);
			//			}
		} else {//失败
			object.put(GatewayConstants.ADDRESS, "");
			object.put(GatewayConstants.AMOUNT, "");
			object.put(GatewayConstants.CURRENCY, "XRP");
			object.put(GatewayConstants.REQUEST_ID, requestID);
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SUBMIT_GLOBALLEDGER_FIAL_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SUBMIT_GLOBALLEDGER_FIAL_MESSAGE);
		}

		//发送异步响应消息
		ReturnResultUtil.callBackRequest(callBackURL, object, requestID);
	}

	/**
	 * Document Start 
	 * account_object CallBack
	 * 
	//	   {
	//		    "result": {
	//		        "ledger_current_index": 24072766,
	//		        "validated": false,
	//		        "account_objects": [
	//		            {
	//		                "HighNode": "0000000000000000",
	//		                "LowNode": "0000000000000000",
	//		                "PreviousTxnLgrSeq": 22790387,
	//		                "LedgerEntryType": "RippleState",
	//		                "LowLimit": {
	//		                    "currency": "NBI",
	//		                    "value": "1000000000",
	//		                    "issuer": "rHa3trZg2MzGTkGA4PLmsTPo9azHUGbhUV"
	//		                },
	//		                "index": "0BFAA390A81C772E8AE6036368C5024E20FBCF860B97D8D92CD4F7AB7B5ECD44",
	//		                "PreviousTxnID": "27DFE7E06D61E6AF67A19EE373B61C1D659FD7135BD50B05B232FAF390AFF25C",
	//		                "Flags": 1114112,
	//		                "Balance": {
	//		                    "currency": "NBI",
	//		                    "value": "13",
	//		                    "issuer": "rrrrrrrrrrrrrrrrrrrrBZbvji"
	//		                },
	//		                "HighLimit": {
	//		                    "currency": "NBI",
	//		                    "value": "0",
	//		                    "issuer": "rLDykZiJ2Zm6UB937jfZPiRnnh85X7CBCS"
	//		                }
	//		            }
	//		        ],
	//		        "account": "rHa3trZg2MzGTkGA4PLmsTPo9azHUGbhUV"
	//		    },
	//		    "id": 0,
	//		    "type": "response",
	//		    "status": "success"
	//		}
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月14日 上午9:52:29
	 * @param response
	 */
	public static void accountObjectCallback(Response response, String requestID, String productCode, String callBackURL) {
		RequestAndResponseLogUtil.rippleAsynResponseLog(logger, response.message.toString(), requestID);
		String result = response.message.toString();
		JSONObject resultJSON = JSON.parseObject(result);
		JSONObject object = new JSONObject();
		//判断是否成功
		//提交状态
		String submitStatus = resultJSON.getString("status");
		//		Boolean validateStatus = resultJSON.getJSONObject("result").getBoolean("validated");

		//成功
		if (submitStatus.equalsIgnoreCase("success")) {
			//			if (validateStatus.booleanValue()) {
			JSONArray objects = resultJSON.getJSONObject("result").getJSONArray("account_objects");
			String issuer = ProductComponent.getProductInfoByProductCode(productCode).getIssuer();
			for (Object o : objects) {
				JSONObject temp = (JSONObject) o;
				String tempIssuer = temp.getJSONObject("HighLimit").getString("issuer");
				if (issuer.equalsIgnoreCase(tempIssuer)) {
					object.put(GatewayConstants.ADDRESS, resultJSON.getJSONObject("result").getString("account"));
					object.put(GatewayConstants.AMOUNT, temp.getJSONObject("Balance").getString("value"));
					object.put(GatewayConstants.CURRENCY, temp.getJSONObject("Balance").getString("currency"));
					object.put(GatewayConstants.REQUEST_ID, requestID);
					object.put(GatewayConstants.STATUS, ReturnResultUtil.CONSENSUS_SUCCESS_CODE);
					object.put(GatewayConstants.MESSAGE, ReturnResultUtil.CONSENSUS_SUCCESS_MESSAGE);
				}
			}
			//			} else {
			//				object.put(GatewayConstants.ADDRESS, "");
			//				object.put(GatewayConstants.AMOUNT, "");
			//				object.put(GatewayConstants.CURRENCY, "");
			//				object.put(GatewayConstants.STATUS, ReturnResultUtil.CONSENSUS_FIAL_CODE);
			//				object.put(GatewayConstants.MESSAGE, ReturnResultUtil.CONSENSUS_FIAL_MESSAGE);
			//				logger.info("异步回调数据为" + object.toJSONString());
			//			}
		} else {//失败
			object.put(GatewayConstants.ADDRESS, "");
			object.put(GatewayConstants.AMOUNT, "");
			object.put(GatewayConstants.CURRENCY, "");
			object.put(GatewayConstants.REQUEST_ID, requestID);
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SUBMIT_GLOBALLEDGER_FIAL_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SUBMIT_GLOBALLEDGER_FIAL_MESSAGE);
			logger.info("异步回调数据为" + object.toJSONString());
		}

		//发送异步响应消息
		ReturnResultUtil.callBackRequest(callBackURL, object, requestID);
	}

	/**
	 * Document Start 
	 * 事务查询回调
	//{
	//    "result": {
	//        "date": 523265521,
	//        "Account": "rpJB2tF2vjPxDiiGAbVuWYRxTwTDEyvAgg",
	//        "Destination": "rHa3trZg2MzGTkGA4PLmsTPo9azHUGbhUV",
	//        "TransactionType": "Payment",
	//        "ledger_index": 23001638,
	//        "SigningPubKey": "03728DCBBF86F1C30CA8D7B429600DFFE550347E3DE74891AABC38DDD5908FD426",
	//        "Amount": "10000000",
	//        "Fee": "12",
	//        "SendMax": {
	//            "currency": "CNY",
	//            "value": "0.4061181571",
	//            "issuer": "rpJB2tF2vjPxDiiGAbVuWYRxTwTDEyvAgg"
	//        },
	//        "Flags": 2147483648,
	//        "Sequence": 58,
	//        "LastLedgerSequence": 23001640,
	//        "TxnSignature": "3045022100932DC486BC344172DBC62031A25AC941C7178EC13E181A35A5C60E4605C418CD02205A82D50F4E35FCA4589F65EE9DD2467F903925E14CAAD0FAD701E6F17FD11E0C",
	//        "validated": true,
	//        "Memos": [
	//            {
	//                "Memo": {
	//                    "MemoType": "636C69656E74",
	//                    "MemoData": "7274312E312E33322D6275676669782D322D67653135323239372D6469727479"
	//                }
	//            }
	//        ],
	//        "meta": {
	//            "AffectedNodes": [
	//                {
	//                    "ModifiedNode": {
	//                        "LedgerIndex": "0B731AF2A9DFAD5C68B3FBD47FD88A25E99C50144B7D49600609EF3F665AD93B",
	//                        "FinalFields": {
	//                            "TakerPays": {
	//                                "currency": "CNY",
	//                                "value": "7659.377090771973",
	//                                "issuer": "razqQKzJRdB4UxFPWf5NEpEG3WMkmwgcXA"
	//                            },
	//                            "Account": "rBxy23n7ZFbUpS699rFVj1V9ZVhAq6EGwC",
	//                            "BookDirectory": "7254404DF6B7FBFFEF34DC38867A7E7DE610B513997B78804D0E490D76F32D3F",
	//                            "OwnerNode": "000000000000021C",
	//                            "TakerGets": "190485717652",
	//                            "Flags": 0,
	//                            "Sequence": 28888150,
	//                            "BookNode": "0000000000000000"
	//                        },
	//                        "PreviousFields": {
	//                            "TakerPays": {
	//                                "currency": "CNY",
	//                                "value": "7659.779187957272",
	//                                "issuer": "razqQKzJRdB4UxFPWf5NEpEG3WMkmwgcXA"
	//                            },
	//                            "TakerGets": "190495717652"
	//                        },
	//                        "PreviousTxnLgrSeq": 23001632,
	//                        "LedgerEntryType": "Offer",
	//                        "PreviousTxnID": "4EC79E78BD10151FFE093641B8CC0892E86EFD1CD6BCCCCCC819AFA2D5658A24"
	//                    }
	//                },
	//                {
	//                    "ModifiedNode": {
	//                        "LedgerIndex": "204C137517CE1A279EE9E93FE73D9EB76E391DA8377DFBB1ECC5644987F8713D",
	//                        "FinalFields": {
	//                            "HighNode": "00000000000001DF",
	//                            "LowNode": "0000000000000000",
	//                            "LowLimit": {
	//                                "currency": "CNY",
	//                                "value": "1000000000",
	//                                "issuer": "rpJB2tF2vjPxDiiGAbVuWYRxTwTDEyvAgg"
	//                            },
	//                            "Flags": 1114112,
	//                            "Balance": {
	//                                "currency": "CNY",
	//                                "value": "29.46200523490861",
	//                                "issuer": "rrrrrrrrrrrrrrrrrrrrBZbvji"
	//                            },
	//                            "HighLimit": {
	//                                "currency": "CNY",
	//                                "value": "0",
	//                                "issuer": "razqQKzJRdB4UxFPWf5NEpEG3WMkmwgcXA"
	//                            }
	//                        },
	//                        "PreviousFields": {
	//                            "Balance": {
	//                                "currency": "CNY",
	//                                "value": "29.8641024202086",
	//                                "issuer": "rrrrrrrrrrrrrrrrrrrrBZbvji"
	//                            }
	//                        },
	//                        "PreviousTxnLgrSeq": 22019153,
	//                        "LedgerEntryType": "RippleState",
	//                        "PreviousTxnID": "B4EC7340E044050CE27351C94990D69EFF15F2A2FF61277CA2849D9314EBCB88"
	//                    }
	//                },
	//                {
	//                    "ModifiedNode": {
	//                        "LedgerIndex": "644E1B59EB070713A3A80082D32FA20D5EB694DDA75F29C66A20D83FBFDB06F6",
	//                        "FinalFields": {
	//                            "Account": "rpJB2tF2vjPxDiiGAbVuWYRxTwTDEyvAgg",
	//                            "OwnerCount": 4,
	//                            "Flags": 0,
	//                            "Sequence": 59,
	//                            "Balance": "18670461885"
	//                        },
	//                        "PreviousFields": {
	//                            "Sequence": 58,
	//                            "Balance": "18670461897"
	//                        },
	//                        "PreviousTxnLgrSeq": 22998663,
	//                        "LedgerEntryType": "AccountRoot",
	//                        "PreviousTxnID": "3256F5F78DB1DAB04C1CCA6284C6AB09CF0D8FA9EA8EA96CE6EEA051FE3257A6"
	//                    }
	//                },
	//                {
	//                    "ModifiedNode": {
	//                        "LedgerIndex": "9FB4BCEBF4E6F3927578EE02434C09AC1C6EC1EC98388BC91B9C25CF6282EE3A",
	//                        "FinalFields": {
	//                            "HighNode": "000000000000011A",
	//                            "LowNode": "000000000000011F",
	//                            "LowLimit": {
	//                                "currency": "CNY",
	//                                "value": "0",
	//                                "issuer": "razqQKzJRdB4UxFPWf5NEpEG3WMkmwgcXA"
	//                            },
	//                            "Flags": 3211264,
	//                            "Balance": {
	//                                "currency": "CNY",
	//                                "value": "-1.206291555899999",
	//                                "issuer": "rrrrrrrrrrrrrrrrrrrrBZbvji"
	//                            },
	//                            "HighLimit": {
	//                                "currency": "CNY",
	//                                "value": "0",
	//                                "issuer": "rBxy23n7ZFbUpS699rFVj1V9ZVhAq6EGwC"
	//                            }
	//                        },
	//                        "PreviousFields": {
	//                            "Balance": {
	//                                "currency": "CNY",
	//                                "value": "-0.8041943705999998",
	//                                "issuer": "rrrrrrrrrrrrrrrrrrrrBZbvji"
	//                            }
	//                        },
	//                        "PreviousTxnLgrSeq": 23001218,
	//                        "LedgerEntryType": "RippleState",
	//                        "PreviousTxnID": "5F57831251CCB2292EA9C82884E2CBD1E37DA99DE246BD81710A22BF78C44287"
	//                    }
	//                },
	//                {
	//                    "ModifiedNode": {
	//                        "LedgerIndex": "AAB239FD522363730614137FA02DDDD0E9059CEB4FBB104C5149CF513366C359",
	//                        "FinalFields": {
	//                            "Account": "rHa3trZg2MzGTkGA4PLmsTPo9azHUGbhUV",
	//                            "OwnerCount": 1,
	//                            "Flags": 0,
	//                            "Sequence": 3,
	//                            "Balance": "19809999976"
	//                        },
	//                        "PreviousFields": {
	//                            "Balance": "19799999976"
	//                        },
	//                        "PreviousTxnLgrSeq": 22585950,
	//                        "LedgerEntryType": "AccountRoot",
	//                        "PreviousTxnID": "635144071ED08E7D5511D09AA9564DA77FCE227FE7EB9BB55877398196BC6AD2"
	//                    }
	//                },
	//                {
	//                    "ModifiedNode": {
	//                        "LedgerIndex": "EAFF4A0B5E891B9BE6A4D484FD0A73356F099FA54F650C9D8FB35D3F29A44176",
	//                        "FinalFields": {
	//                            "Account": "rBxy23n7ZFbUpS699rFVj1V9ZVhAq6EGwC",
	//                            "OwnerCount": 25,
	//                            "Flags": 0,
	//                            "Sequence": 28888174,
	//                            "Balance": "238104155645"
	//                        },
	//                        "PreviousFields": {
	//                            "Balance": "238114155645"
	//                        },
	//                        "PreviousTxnLgrSeq": 23001637,
	//                        "LedgerEntryType": "AccountRoot",
	//                        "PreviousTxnID": "54E9C4AD9D2B3066E01C34E2AE8E3ABBC4899B95D4779BCB1AA8A270292A4A89"
	//                    }
	//                }
	//            ],
	//            "TransactionResult": "tesSUCCESS",
	//            "TransactionIndex": 0,
	//            "delivered_amount": "10000000"
	//        },
	//        "inLedger": 23001638,
	//        "Paths": [
	//            [
	//                {
	//                    "type_hex": "0000000000000001",
	//                    "type": 1,
	//                    "account": "razqQKzJRdB4UxFPWf5NEpEG3WMkmwgcXA"
	//                },
	//                {
	//                    "type_hex": "0000000000000010",
	//                    "currency": "XRP",
	//                    "type": 16
	//                }
	//            ]
	//        ],
	//        "hash": "66ED8392753A1A3A1BC920C2C98A6902C1C9C6AD606715A36A5347252FBF9D8C"
	//    },
	//    "id": 0,
	//    "type": "response",
	//    "status": "success"
	//}
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月16日 上午1:23:04
	 * @param response
	 * @param callBackURL
	 */
	public static void txCallback(Response response, String requestID, String hash, String callBackURL) {
		RequestAndResponseLogUtil.rippleAsynResponseLog(logger, response.message.toString(), requestID);
		String result = response.message.toString();
		JSONObject resultJSON = JSON.parseObject(result);
		JSONObject object = new JSONObject();
		//判断是否成功
		//提交状态
		String submitStatus = resultJSON.getString("status");
		boolean validateStatus = resultJSON.getJSONObject("result").getBooleanValue("validated");

		String transactionResult = resultJSON.getJSONObject("result").getJSONObject("meta").getString("TransactionResult");
		//判断是否成功
		//成功
		if (submitStatus.equalsIgnoreCase("success")) {
			//共识验证成功
			if (validateStatus) {
				object.put(GatewayConstants.TRANSACTION_ID, hash);
				object.put(GatewayConstants.REQUEST_ID, requestID);
				object.put(GatewayConstants.STATUS, ReturnResultUtil.CONSENSUS_SUCCESS_CODE);
				object.put(GatewayConstants.MESSAGE, ReturnResultUtil.CONSENSUS_SUCCESS_MESSAGE + "{" + transactionResult + "}");
			} else {
				//共识验证失败
				if (transactionResult.indexOf("tec") >= 0 || transactionResult.indexOf("tem") >= 0 || transactionResult.equalsIgnoreCase("tefPAST_SEQ") || transactionResult.equalsIgnoreCase("tefMAX_LEDGER")) {
					object.put(GatewayConstants.TRANSACTION_ID, hash);
					object.put(GatewayConstants.REQUEST_ID, requestID);
					object.put(GatewayConstants.STATUS, ReturnResultUtil.CONSENSUS_FIAL_CODE);
					object.put(GatewayConstants.MESSAGE, ReturnResultUtil.CONSENSUS_FIAL_MESSAGE + "{" + transactionResult + "}");
				}

				//共识处理中
				else {
					object.put(GatewayConstants.TRANSACTION_ID, hash);
					object.put(GatewayConstants.REQUEST_ID, requestID);
					object.put(GatewayConstants.STATUS, ReturnResultUtil.CONSENSUS_PROCESS_CODE);
					object.put(GatewayConstants.MESSAGE, ReturnResultUtil.CONSENSUS_PROCESS_MESSAGE + "{" + transactionResult + "}");
				}
			}

		} else {//失败
			object.put(GatewayConstants.TRANSACTION_ID, hash);
			object.put(GatewayConstants.REQUEST_ID, requestID);
			object.put(GatewayConstants.STATUS, ReturnResultUtil.SUBMIT_GLOBALLEDGER_FIAL_CODE);
			object.put(GatewayConstants.MESSAGE, ReturnResultUtil.SUBMIT_GLOBALLEDGER_FIAL_MESSAGE);
		}

		//发送异步响应消息
		ReturnResultUtil.callBackRequest(callBackURL, object, requestID);
	}

	/**
	 * Document Start 
	 * 发送异步回调请求方法
	 * Document End 
	 * Author: songwenpeng@le.com
	 * Time: 2016年9月29日 下午1:10:16
	 * @param callBackURL
	 * @param object
	 */
	public static void callBackRequest(String callBackURL, JSONObject object, String requestID) {
		logger.info("<=====向外部系统发送异步,本次响应的请求ID为【" + requestID + "】,异步响应地址【" + callBackURL + "】,异步回告报文" + object.toJSONString());
		try {
			HttpClientWithPoolUtil.post(callBackURL, object);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("<=====向外部系统发送异步回告失败!!本次响应的请求ID为【" + requestID + "】,异步响应地址【" + callBackURL + "】,异常跟踪" + e.getMessage());
		}
	}

}
