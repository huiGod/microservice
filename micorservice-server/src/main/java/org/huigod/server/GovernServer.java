package org.huigod.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.huigod.server.common.Configuration;
import org.huigod.server.common.ConfigurationException;
import org.huigod.server.constant.NodeTypeConstant;
import org.huigod.server.node.MasterNode;
import org.huigod.server.node.NodeStatus;

/**
 * 注册中心服务端启动类
 */
@Slf4j
public class GovernServer {

  private static final long SHUTDOWN_CHECK_INTERVAL = 300L;

  public static void main(String[] args) {

    try {
      //配置文件路径
      String configPath = args[0];

      if (StringUtils.isBlank(configPath)) {
        throw new ConfigurationException("配置文件路径未指定!");
      }

      NodeStatus nodeStatus = NodeStatus.getInstance();
      nodeStatus.setStatus(NodeStatus.INITIALIZING);

      //解析配置文件配置
      Configuration configuration = Configuration.getInstance();
      configuration.parse(configPath);

      //启动节点
      String nodeType = configuration.getNodeType();
      startNode(nodeType);

      nodeStatus.setStatus(NodeStatus.RUNNING);

      //等待系统退出
      waitForShutDown();

    } catch (ConfigurationException e) {
      log.error("服务进程启动异常:", e);
      System.exit(2);
    } catch (InterruptedException e) {
      log.error("系统遇到未知异常！");
      System.exit(1);
    }
  }

  /**
   * 启动节点
   *
   * @param nodeType 节点类型
   */
  private static void startNode(String nodeType) {

    if (NodeTypeConstant.NODE_TYPE_MASTER.equals(nodeType)) {
      MasterNode masterNode = new MasterNode();
      masterNode.start();
    }
  }

  /**
   * 等待系统关闭
   */
  private static void waitForShutDown() throws InterruptedException {
    while (NodeStatus.RUNNING == NodeStatus.getInstance().getStatus()) {
      Thread.sleep(SHUTDOWN_CHECK_INTERVAL);
    }
  }
}
