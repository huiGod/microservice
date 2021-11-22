package org.huigod.server.node;

/**
 * Controller 候选节点组件
 */
public class ControllerCandidate {

  /**
   * 集群内部节点通信组件
   */
  private MasterNodeNetworkManager masterNodeNetworkManager;

  public ControllerCandidate(MasterNodeNetworkManager masterNodeNetworkManager) {
    this.masterNodeNetworkManager = masterNodeNetworkManager;
  }

}
