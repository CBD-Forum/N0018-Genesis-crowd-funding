//package com.fbd.core.app.reconciliation.service.impl;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import javax.annotation.Resource;
//import org.apache.commons.lang3.time.DateUtils;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.stereotype.Service;
//import com.fbd.core.app.recharge.dao.IRechargeDao;
//import com.fbd.core.app.recharge.model.RechargeModel;
//import com.fbd.core.app.reconciliation.dao.IReconciliationRechargeDao;
//import com.fbd.core.app.reconciliation.dao.IReconciliationRecordDao;
//import com.fbd.core.app.reconciliation.dao.IReconciliationRepayDao;
//import com.fbd.core.app.reconciliation.model.ReconciliationRechargeModel;
//import com.fbd.core.app.reconciliation.model.ReconciliationRecordModel;
//import com.fbd.core.app.reconciliation.service.IReconciliationService;
//import com.fbd.core.app.withdraw.dao.IWithDrawDao;
//import com.fbd.core.common.model.SearchResult;
//import com.fbd.core.helper.PKGenarator;
//
//@Service
//public class ReconciliationServiceImpl implements IReconciliationService {
//    
//    /**
//     * 每页记录数 
//     */
//    private static final int pageSize = 50;
//    private static final Date yestoday = DateUtils.addDays(new Date(), -30);
//    
//    //对账记录
//    @Resource
//    private IReconciliationRecordDao reconciliationRecordDao;
//    
//    //充值对账
//    @Resource
//    private IReconciliationRechargeDao reconciliationRechargeDao;
//    
//    //平台充值记录
//    @Resource
//    private IRechargeDao rechargeDao;
//    
//    //平台提现记录
//    @Resource
//    private IWithDrawDao withDrawDao;
//    //还款对账
//    @Resource
//    private IReconciliationRepayDao reconciliationRepayDao;
//    
//    
//
//      
//    
//    
//    /**
//     * Description: 充值对账
//     *
//     * @param 
//     * @return int
//     * @throws 
//     * @Author dongzhongwei
//     * Create Date: 2015-3-12 上午11:15:24
//     */
//    public int saveRecharge() {
//        int pageNum = 1;
//        int saveNum = 0;
//        //汇付返回的记录总数
//        int totalItems = 0;
//        //任务查询
//        ReconciliationRecordModel reconciliationRecord = new ReconciliationRecordModel(PKGenarator.getId(), AutoReconciliationStatus.ING, yestoday, Search.SaveReconciliation);
//        while(true){
//            Map<String, String> params =  getParam(Search.SaveReconciliation, pageNum);
//            String result = TransSubmit.doPost(params);
//            System.out.println("------------充值对账:" + result);
//            JSONObject json = new JSONObject(result);
//            if(verify(json)){
//                String respCode = json.getString(Param.RespCode);
//                //总条数
//                totalItems = json.getInt("TotalItems");
//                if("000".equals(respCode)){
//                    JSONArray rechargeList = json.getJSONArray("SaveReconciliationDtoList");
//                    int total = rechargeList.length();
//                    if (total == 0) {
//                        break;
//                    }
//                    for(int i = 0; i < rechargeList.length(); i++){
//                        ReconciliationRechargeModel reconciliationRechargeModel = new ReconciliationRechargeModel();
//                        try {
//                            JSONObject current = rechargeList.getJSONObject(i);
//                            String orderId = current.getString(Param.OrdId);
//                            reconciliationRechargeModel.setId(orderId);
//                            //查询平台充值记录
//                            RechargeModel  recharge = rechargeDao.selectByOrderId(orderId);
//                            if (recharge!=null) {
////                                reconciliationRechargeModel.setaMoney(recharge.getActualAmt());
////                                reconciliationRechargeModel.setaFee(recharge.getRechargeFee());
////                                reconciliationRechargeModel.setaStatus(recharge.getState());
////                                reconciliationRechargeModel.setOrderTime(recharge.getCompleteTime());
//                            }
////                            reconciliationRechargeModel.setrMoney(current.getDouble(Param.TransAmt));
////                            reconciliationRechargeModel.setrFee(current.getDouble(Param.FeeAmt));
////                            reconciliationRechargeModel.setrStatus(current.getString(Param.TransStat));
////                            reconciliationRechargeModel.setTime(new Date());
//                        } catch (Exception e) {
//                            reconciliationRecord.setStatus(AutoReconciliationStatus.FAIL);
//                        } finally{
//                            int num = reconciliationRechargeDao.save(reconciliationRechargeModel);
//                            if (num == 1) {
//                                saveNum++;
//                            }
//                        }
//                    }
//                    if(total < pageSize){
//                        break;
//                    }
//                    pageNum++;
//                } else{
//                    continue;
//                }
//            } else{
//                break;
//            }
//        }
//        if (saveNum != 0 && saveNum == totalItems) {
//            reconciliationRecord.setStatus(AutoReconciliationStatus.SUCCESS);
//        }
//        reconciliationRecordDao.save(reconciliationRecord);
//        return 0;
//    }
// 
// 
//    /**
//     * Description: 分页查询充值对账的结果
//     *
//     * @param 
//     * @return SearchResult<Map<String,Object>>
//     * @throws 
//     * @Author dongzhongwei
//     * Create Date: 2015-3-13 上午11:27:14
//     */
//    public SearchResult<Map<String, Object>> getRechargePage(Map<String, Object> param) {
//        return this.reconciliationRechargeDao.getPage("getCount", "select", param);
//    }
// 
//    /**
//     * Description: 分页查询还款对账的结果
//     *
//     * @param 
//     * @return SearchResult<Map<String,Object>>
//     * @throws 
//     * @Author dongzhongwei
//     * Create Date: 2015-3-13 下午12:44:19
//     */
//    public SearchResult<Map<String, Object>> getRepayPage(Map<String, Object> param) {
//        return this.reconciliationRepayDao.getPage("getCount", "select", param);
//    }
//
//    /**
//     * Description: 分页查询自动对账的调度记录
//     *
//     * @param 
//     * @return SearchResult<Map<String,Object>>
//     * @throws 
//     * @Author dongzhongwei
//     * Create Date: 2015-3-13 下午5:24:23
//     */
//    public SearchResult<Map<String, Object>> getRecordPage(Map<String, Object> param) {
//        return this.reconciliationRecordDao.getPage("getCount", "select", param);
//    }
// 
//}
