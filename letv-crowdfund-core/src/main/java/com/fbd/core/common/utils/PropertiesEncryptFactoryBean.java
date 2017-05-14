package com.fbd.core.common.utils;

import java.util.Properties;
import org.springframework.beans.factory.FactoryBean;

public class PropertiesEncryptFactoryBean implements FactoryBean {

    private Properties properties;
    
    public Properties getProperties() {
        return properties;
    }

    public Object getObject() throws Exception {
        return getProperties();
    }

    public Class getObjectType() {
        return Properties.class;
    }

    public boolean isSingleton() {
        return true;
    }
    
    public void setProperties(Properties inProperties) {
        this.properties = inProperties;  
        String originalUsername = properties.getProperty("user");  
        String originalPassword = properties.getProperty("password");  
        if (originalUsername != null){  
            String newUsername = deEncryptUsername(originalUsername);  
            properties.put("user", newUsername);  
        }  
        if (originalPassword != null){  
            String newPassword = deEncryptPassword(originalPassword);  
            properties.put("password", newPassword);  
        }  
    }
    
    private String deEncryptUsername(String originalUsername){  
        return deEncryptString(originalUsername);  
    }  
      
    private String deEncryptPassword(String originalPassword){  
        return deEncryptString(originalPassword);  
    }  
      
    private String deEncryptString(String originalString){  
        DesUtils des;
        try {
            des = new DesUtils();
            return des.decrypt(originalString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }  

}
