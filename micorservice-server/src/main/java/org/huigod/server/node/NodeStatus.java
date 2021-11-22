package org.huigod.server.node;

import lombok.Getter;
import lombok.Setter;

/**
 * 节点状态管理
 */
public class NodeStatus {

  /**
   * 正在初始化
   */
  public static final int INITIALIZING = 0;
  /**
   * 正在运行中
   */
  public static final int RUNNING = 1;
  /**
   * 已经关闭
   */
  public static final int SHUTDOWN = 2;
  /**
   * 节点状态
   */
  @Getter
  @Setter
  private volatile int status;

  private NodeStatus() {

  }

  public static NodeStatus getInstance() {
    return Singleton.instance;
  }

  private static class Singleton {

    static NodeStatus instance = new NodeStatus();
  }
}
