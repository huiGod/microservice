package org.huigod.server.common;

/**
 * 配置解析异常类
 */
public class ConfigurationException extends Exception{

  public ConfigurationException(String msg) {
    super(msg);
  }

  public ConfigurationException(String msg,Exception e) {
    super(msg, e);
  }

}
