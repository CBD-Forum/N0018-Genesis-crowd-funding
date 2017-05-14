package com.fbd.web.app.action;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import com.fbd.web.base.JUnitActionBase;

public class LogActionTest extends JUnitActionBase {
    
    
    @Test
    public void testSaveLoan() throws Exception{  
        MockHttpServletRequest request = new MockHttpServletRequest();  
        MockHttpServletResponse response = new MockHttpServletResponse();  
        
        request.setServletPath("/crowdfundingInvest/getLeadSupportList.html");  
        request.addParameter("loanNo", "2015060239042407");  
        request.setMethod("POST");
        
        final ModelAndView mav = this.excuteAction(request, response);  
        // 执行结果
        String result = response.getContentAsString();
        System.out.println("执行结果 : "+result); 
    }
    
    
    @Test  
    public void testGetLogPage() throws Exception{  
        MockHttpServletRequest request = new MockHttpServletRequest();  
        MockHttpServletResponse response = new MockHttpServletResponse();  
        
        request.setServletPath("/log/getlist.html");  
        request.addParameter("logType", "guarantee");  
        request.addParameter("page", "1");  
        request.addParameter("rows", "2");  
        request.setMethod("POST");
        
        final ModelAndView mav = this.excuteAction(request, response);  
        // 执行结果
        String result = response.getContentAsString();
        System.out.println("执行结果 : "+result); 
    }
    
    @Test  
    public void testGetLoanById() throws Exception{  
        MockHttpServletRequest request = new MockHttpServletRequest();  
        MockHttpServletResponse response = new MockHttpServletResponse();  
        
        request.setServletPath("/loan/getById.html");  
        request.addParameter("id", "228a5d6d8bc64d5f9830ad19d335e0a5");  
        request.setMethod("POST");
        
        final ModelAndView mav = this.excuteAction(request, response);  
        // 执行结果
        String result = response.getContentAsString();
        System.out.println("执行结果 : "+result); 
    }
    
    @Test
    public void testModifyLoan() throws Exception{  
        MockHttpServletRequest request = new MockHttpServletRequest();  
        MockHttpServletResponse response = new MockHttpServletResponse();  
        
        request.setServletPath("/loan/modify.html");  
        request.addParameter("id", "228a5d6d8bc64d5f9830ad19d335e0a5");  
        request.addParameter("loanNo", "test11");
        request.setMethod("POST");
        
        final ModelAndView mav = this.excuteAction(request, response);  
        // 执行结果
        String result = response.getContentAsString();
        System.out.println("执行结果 : "+result); 
    }
    
    @Test
    public void testRemoveBusConfig() throws Exception{  
        MockHttpServletRequest request = new MockHttpServletRequest();  
        MockHttpServletResponse response = new MockHttpServletResponse();  
        
        request.setServletPath("/businessconfig/remove.html");  
        request.addParameter("id", "dba662521e9f40109dd01bfa03d653c3,f284831a7f324b02bcd7dbad1f343f96");  
        request.setMethod("POST");
        
        final ModelAndView mav = this.excuteAction(request, response);  
        // 执行结果
        String result = response.getContentAsString();
        System.out.println("执行结果 : "+result); 
    }

}
