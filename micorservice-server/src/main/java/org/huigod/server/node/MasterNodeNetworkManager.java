package org.huigod.server.node;

/**
 * 集群内部节点网络通信组件
 */
public class MasterNodeNetworkManager {

  /**
   * 连接节点序号之前的 master 节点
   * @return
   */
  public boolean connectBeforeNodeIdMasterNodes() {

    return false;
  }

  /**
   * 等待节点序号之后的 master 节点向当前节点发起的连接
   */
  public void waitAfterNodeIdConnected() {

  }

}
