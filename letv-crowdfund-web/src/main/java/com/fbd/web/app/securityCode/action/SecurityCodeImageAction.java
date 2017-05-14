package com.fbd.web.app.securityCode.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Encoder;

import com.fbd.core.app.redis.IRedisDao;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.securityCode.SecurityCode;
import com.fbd.core.util.securityCode.SecurityImage;
import com.fbd.web.app.common.FbdConstants;
@Controller
@RequestMapping("/security")
public class SecurityCodeImageAction implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4209129193446817393L;
    @Resource
    private IRedisDao redisDao;
    // 图片流
    private ByteArrayInputStream imageStream;

    public ByteArrayInputStream getImageStream() {
        return imageStream;
    }

    public void setImageStream(ByteArrayInputStream imageStream) {
        this.imageStream = imageStream;
    }

    @RequestMapping(value = "/securityCodeImage.html", method = RequestMethod.GET)
    public void getCode(HttpServletRequest request,
            HttpServletResponse response, HttpSession session) throws Exception {
        // 获取默认难度和长度的验证码
        String securityCode = SecurityCode.getSecurityCode();
        BufferedImage image = SecurityImage.createImage(securityCode);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(image, "JPEG", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        // 放入session中
        session.setAttribute(FbdConstants.CAPTCHA_SESSION, securityCode);
    }
    
    

    @SuppressWarnings("restriction")
	@RequestMapping(value = "/securityImageCode.html", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> securityImageCode(HttpServletRequest request,
            HttpServletResponse response, HttpSession sessio) throws Exception {
    	Map<String,Object> resultMap = new HashMap<String,Object> ();
    	try{
            // 获取默认难度和长度的验证码
            String securityCode = SecurityCode.getSecurityCode();
            String imageId = "I"+PKGenarator.getId();
            //将验证码写入到redis中
            this.redisDao.set(imageId, securityCode,60*5);  //图片验证码5分钟失效
            BufferedImage image = SecurityImage.createImage(securityCode);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();    
            ImageIO.write(image, "png", baos);    
            byte[] bytes = baos.toByteArray();    
            String base64Image = new BASE64Encoder().encodeBuffer(bytes).trim(); 
            resultMap.put("image", base64Image);
            resultMap.put("imageId", imageId);
            resultMap.put("success",true);
    	}catch(Exception e){
    		e.printStackTrace();
    		resultMap.put("success",false);
    	}
        return resultMap;
    }
}
