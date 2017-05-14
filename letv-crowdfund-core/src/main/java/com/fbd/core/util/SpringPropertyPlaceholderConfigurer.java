package com.fbd.core.util;
/**
 * 
 */


import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author Zhao Dong Lu
 * 
 */
public class SpringPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

  /**
   * 
   */
  public SpringPropertyPlaceholderConfigurer() {
    super();
  }

  public Properties getProperties() throws IOException {
    return this.mergeProperties();
  }

}
