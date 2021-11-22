package org.huigod.server.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.huigod.server.constant.NodeTypeConstant;

/**
 * 负责从配置文件中解析配置项到内存中
 */
@Slf4j
public class Configuration {

  /**
   * 节点类型
   */
  public static final String NODE_TYPE = "node.type";

  /**
   * master server 列表
   */
  public static final String MASTER_NODE_SERVERS = "master.node.servers";
  public static final Pattern MASTER_SERVER_REGEX_PATTERN = Pattern
      .compile("(\\d+):(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+):(\\d+)");

  /**
   * 节点序号
   */
  public static final String NODE_ID = "node.id";
  public static final Pattern NODE_ID_REGEX_PATTERN = Pattern.compile("(\\d+)");

  /**
   * 节点类型
   */
  @Getter
  private String nodeType;

  /**
   * master 节点列表
   */
  @Getter
  private String masterNodeServers;

  /**
   * 节点序号
   */
  @Getter
  private Integer nodeId;

  /**
   * 单例类
   */
  private Configuration() {

  }

  public static Configuration getInstance() {
    return Singleton.instance;
  }

  /**
   * 解析配置文件
   *
   * @param configPath 配置文件路径
   */
  public void parse(String configPath) throws ConfigurationException {

    log.info("开始从配置文件:{}中解析配置项!", configPath);
    try {
      Properties properties = loadConfigurationFile(configPath);

      String nodeType = properties.getProperty(NODE_TYPE);
      log.debug("解析{}={}", NODE_TYPE, nodeType);
      validateNodeType(nodeType);
      this.nodeType = nodeType;

      String masterNodeServers = properties.getProperty(MASTER_NODE_SERVERS);
      log.debug("解析{}={}", MASTER_NODE_SERVERS, masterNodeServers);
      validateMasterNodeServers(masterNodeServers);
      this.masterNodeServers = masterNodeServers;

      String nodeId = properties.getProperty(NODE_ID);
      log.debug("解析{}={}", NODE_ID, nodeId);
      validateNodeId(nodeId);
      this.nodeId = Integer.valueOf(nodeId);

      log.info("完成所有配置项的解析!");

    } catch (IllegalArgumentException | IOException e) {
      throw new ConfigurationException("解析配置文件:" + configPath + "异常!", e);
    }

  }


  /**
   * 解析配置文件
   */
  private Properties loadConfigurationFile(String configPath) throws IOException {
    File configFile = new File(configPath);

    if (!configFile.exists()) {
      throw new IllegalArgumentException("解析配置文件:" + configPath + "异常,配置文件不存在!");
    }

    Properties properties = new Properties();
    FileInputStream configFileInputStream = new FileInputStream(configFile);
    try {
      properties.load(configFileInputStream);
    } finally {
      configFileInputStream.close();
    }
    return properties;
  }

  /**
   * 校验节点类型配置
   */
  private void validateNodeType(String nodeType) {
    if (StringUtils.isBlank(nodeType)) {
      throw new IllegalArgumentException("节点类型 " + nodeType + " 未配置!");
    }

    if (!NodeTypeConstant.NODE_TYPE_MASTER.equals(nodeType) && !NodeTypeConstant.NODE_TYPE_SLAVE
        .equals(nodeType)) {
      throw new IllegalArgumentException("节点类型 " + nodeType + " 配置只能是 MASTER 或者 SLAVE!");
    }
  }

  /**
   * 校验 master 节点地址
   */
  private void validateMasterNodeServers(String masterNodeServers) {
    if (StringUtils.isBlank(masterNodeServers)) {
      throw new IllegalArgumentException("master 服务地址 " + MASTER_NODE_SERVERS + " 未配置!");
    }

    String[] ipAndPorts = masterNodeServers.split(",");
    for (String ipAndPort : ipAndPorts) {
      if (!MASTER_SERVER_REGEX_PATTERN.matcher(ipAndPort).matches()) {
        throw new IllegalArgumentException("master 服务地址 " + MASTER_NODE_SERVERS + " 配置格式异常!");
      }
    }
  }

  /**
   * 校验节点序号
   */
  private void validateNodeId(String nodeId) {
    if (StringUtils.isBlank(nodeId)) {
      throw new IllegalArgumentException("master 节点序号 " + NODE_ID + " 未配置!");
    }

    if (!NODE_ID_REGEX_PATTERN.matcher(nodeId).matches()) {
      throw new IllegalArgumentException("master 节点序号 " + NODE_ID + " 配置必须是数字类型!");
    }
  }

  private static class Singleton {

    static Configuration instance = new Configuration();
  }


}
