package com.fbd.core.base;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import com.fbd.core.app.redis.IRedisDao;
import com.fbd.core.app.user.model.UserLoginBean;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.web.MyRequestContextHolder;

@SuppressWarnings("all")
public abstract class BaseAction implements Serializable {
    
    
    private static final Logger logger = LoggerFactory
            .getLogger(BaseAction.class);

    
    
    @Resource
    private IRedisDao redisDao;

    // 成功
    protected static final String SUCCESS = "success";
    // 失败
    protected static final String ERROR = "error";

    // 消息
    protected static final String MSG = "msg";
    
    //错误码
    protected static final String ERRORCODE = "errorCode";
    //错误信息
    protected static final String ERRORMSG = "errorMsg";
    
//    public String getUserId(HttpServletRequest request){
//        Object userId = request.getSession().getAttribute(FbdCoreConstants.SESSION_USERID);
//        if(userId !=null){
//           return userId.toString();
//        }else{
//            throw new ApplicationException("您还未登录，请先登录");
//        }
//    }
    
    
    public String getUserId(HttpServletRequest request){
        
        String mobileTerminalFlag = request.getHeader("mobileTerminalFlag");
        if(!StringUtils.isEmpty(mobileTerminalFlag)){
            String token = request.getHeader("token");
            if(StringUtils.isEmpty(token)){
                throw new ApplicationException("您还未登录，请先登录");
            }
            if(this.redisDao.exists(token)){
                String userLoginStr = this.redisDao.get(token);
                UserLoginBean user = (UserLoginBean) JsonHelper.getObjectFromJsonString(userLoginStr, UserLoginBean.class);
                String userId = user.getUserId();
                if(userId!=null){
                    //更新tocken
                    this.redisDao.set(token, userLoginStr,60*30);  //session保存30分钟
                    return userId;
                }else{
                    throw new ApplicationException("您还未登录，请先登录");
                }
            }else{
                throw new ApplicationException("您还未登录，请先登录");
            }
        }else{
            Object userId = request.getSession().getAttribute(FbdCoreConstants.SESSION_USERID);
            if(userId !=null){
               return userId.toString();
            }else{
                throw new ApplicationException("您还未登录，请先登录");
            }
        }
    }  
    
    
    
    
    public void userLoginOut(HttpServletRequest request){
        String mobileTerminalFlag = request.getHeader("mobileTerminalFlag");
        if(!StringUtils.isEmpty(mobileTerminalFlag)){
            String token = request.getHeader("token");
            if(!StringUtils.isEmpty(token)){
                 this.redisDao.del(token);
            }
        } 
    }  
    
//    
//    public String getThirdNo(HttpServletRequest request){
//        Object thirdNo = request.getSession()
//                .getAttribute(FbdCoreConstants.SESSION_THIRDNO);
//        if(thirdNo !=null){
//           return thirdNo.toString();
//        }else{
//            throw new ApplicationException("您还未实名认证，请先实名认证");
//        }
//    }
    
    
    public String getThirdNo(HttpServletRequest request){
        
        String mobileTerminalFlag = request.getHeader("mobileTerminalFlag");
        if(!StringUtils.isEmpty(mobileTerminalFlag)){
            String token = request.getParameter("token");
            if(StringUtils.isEmpty(token)){
                throw new ApplicationException("您还未实名认证，请先实名认证");
            }
            if(this.redisDao.exists(token)){
                String userLoginStr = this.redisDao.get(token);
                UserLoginBean user = (UserLoginBean) JsonHelper.getObjectFromJsonString(userLoginStr, UserLoginBean.class);
                String thirdNo = user.getThirdNo();
                if(thirdNo!=null){
                    //更新tocken
                    this.redisDao.set(token, userLoginStr,60*30);  //session保存30分钟
                    return thirdNo;
                }else{
                    throw new ApplicationException("您还未实名认证，请先实名认证");
                }
            }else{
                throw new ApplicationException("您还未实名认证，请先实名认证");
            }
        }else{
            Object thirdNo = request.getSession()
                    .getAttribute(FbdCoreConstants.SESSION_THIRDNO);
            if(thirdNo !=null){
               return thirdNo.toString();
            }else{
                throw new ApplicationException("您还未实名认证，请先实名认证");
            }
        }
    }
    
    /**
     * Description: 获得分页和排序的参数Map
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-21 上午10:14:30
     */
    public Map<String, Object> getPageParam(){
        HttpServletRequest request = MyRequestContextHolder.getCurrentRequest();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String pageStr = request.getParameter("page");
        pageStr=StringUtils.isEmpty(pageStr)?"1":pageStr;
        int page = Integer.parseInt(pageStr);
        page=page==0?1:page;
        String rowsStr = request.getParameter("rows");
        rowsStr = StringUtils.isEmpty(rowsStr)?"0":rowsStr;
        int rows=Integer.parseInt(rowsStr);
        paramMap.put("startPage", (page - 1) * rows);
        paramMap.put("endPage", rows);
        paramMap.put("sort", request.getParameter("sort"));
        paramMap.put("order", request.getParameter("order"));
        return paramMap;
    }
    
    /**
     * Description: 获取所有的传递参数
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-9 下午3:12:24
     */
    public Map<String, String> getRequestParam() {
        HttpServletRequest request = MyRequestContextHolder.getCurrentRequest();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String, String> result = new HashMap<String, String>();
        Iterator<?> iter = request.getParameterMap().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String value = request.getParameter(key);
            try {
                value = new String(value.getBytes("UTF-8"),request.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
            }
            result.put(key, value);
        }
        return result;
    }
    /**
     * Description: 获取所有的传递参数
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-9 下午3:12:24
     */
    public Map<String, Object> getRequestParams() {
        HttpServletRequest request = MyRequestContextHolder.getCurrentRequest();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        Iterator<?> iter = request.getParameterMap().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String value = request.getParameter(key);
            try {
                value = new String(value.getBytes("UTF-8"),request.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
            }
            result.put(key, value);
        }
        return result;
    }
    
    /**
     * Description: 获取所有的传递参数
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-9 下午3:12:24
     */
    public Map<String, Object> getBlockChainParams() {
        HttpServletRequest request = MyRequestContextHolder.getCurrentRequest();
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int len = request.getContentLength();
        byte[] buffer = new byte[len];
        ServletInputStream inputStream;
        try {
            inputStream = request.getInputStream();
            inputStream.read(buffer, 0, len);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String content = "";
        try {
            content = new String(buffer,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info("CallBack内容为=====>" + content);
        System.out.println("CallBack内容为=====>" + content);
        
        Map<String,Object> resultMap = JsonHelper.getMapFromJson(content);
        return resultMap;
    }
    public Map<String, String> getBlockChainParamsStr() {
        HttpServletRequest request = MyRequestContextHolder.getCurrentRequest();
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int len = request.getContentLength();
        byte[] buffer = new byte[len];
        ServletInputStream inputStream;
        try {
            inputStream = request.getInputStream();
            inputStream.read(buffer, 0, len);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String content = "";
        try {
            content = new String(buffer,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info("CallBack内容为=====>" + content);
        System.out.println("CallBack内容为=====>" + content);
        
        Map<String,String> resultMap = JsonHelper.getMapFromJson(content);
        return resultMap;
 
    }
    
  
    
    @ExceptionHandler(Exception.class)
    public @ResponseBody Map<String, Object> handleException(Exception ex,HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        logger.info("========>文件上传前验证===========");
        if (ex instanceof org.springframework.web.multipart.MaxUploadSizeExceededException){
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "文件大小不能大于"+((MaxUploadSizeExceededException)ex).getMaxUploadSize()/1024/1024+"MB");
         } else{
             resultMap.put(SUCCESS, false);
             resultMap.put(MSG, "参数异常");
        }
        return resultMap;
    }
    
}