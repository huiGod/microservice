package org.huigod.server.node;

/**
 * Master 节点
 */
public class MasterNode {

  /**
   * Controller 候选节点组件
   */
  private ControllerCandidate controllerCandidate;

  /**
   * 集群内部节点通信组件
   */
  private MasterNodeNetworkManager masterNodeNetworkManager;

  public MasterNode() {
    this.masterNodeNetworkManager = new MasterNodeNetworkManager();
    this.controllerCandidate = new ControllerCandidate(masterNodeNetworkManager);
  }

  /**
   * Master 节点启动
   */
  public void start() {

    //连接节点序号之前的 master 节点
    masterNodeNetworkManager.connectBeforeNodeIdMasterNodes();
    //等待节点序号之后的 master 节点向当前节点发起的连接
    masterNodeNetworkManager.waitAfterNodeIdConnected();
  }
}
