package com.github.buywatermelon.raft.study.node;

/**
 * Raft节点状态
 */
public enum NodeState {

    /**
     * 领导者
     */
    LEADER,

    /**
     * 追随者
     */
    FOLLOWER,

    /**
     * 候选者
     */
    CANDIDATE,
}
