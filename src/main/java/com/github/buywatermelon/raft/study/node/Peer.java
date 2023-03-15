package com.github.buywatermelon.raft.study.node;

public class Peer {

    private int peerId;

    /**
     * 发送给每个节点的下一个日志条目的索引（初始化为领导者最后一条日志索引+1）
     * 领导者，无需持久化，选举后初始化
     */
    private long[] nextIndex;

    /**
     * 已复制到每个节点的最新日志条目的索引（初始化为0，单调递增）
     * 领导者，无需持久化，选举后初始化
     */
    private long[] matchIndex;
}
