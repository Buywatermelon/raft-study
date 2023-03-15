package com.github.buywatermelon.raft.study.node;

import com.github.buywatermelon.raft.study.storage.Log;
import com.github.buywatermelon.raft.study.task.ElectionTask;
import lombok.Data;
import org.apache.dubbo.common.timer.HashedWheelTimer;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.github.buywatermelon.raft.study.constants.Constants.DEFAULT_ELECTION_PERIOD;

@Data
@Component
public class RaftNode {

    /**
     * 节点id
     */
    private int nodeId;

    /**
     * 节点已知的最新任期（第一次启动时初始化为0，单调递增）
     * 所有节点，需要持久化
     */
    private int currentTerm;

    /**
     * 当前任期内收到投票的候选者ID（如果没有，则为null）
     * 所有节点上，需要持久化
     */
    private int voteFor;

    /**
     * 日志条目；每个条目包含状态机的命令，以及接收领导者条目时的任期（第一个索引为1）
     * 在所有节点上需要持久化
     */
    private List<Log> logEntries;

    /**
     * 已知提交的最新日志条目的索引（初始化为0，单调递增）
     * 所有节点，无需持久化
     */
    private long commitIndex;

    /**
     * 应用于状态机的最新日志条目的索引（初始化为0，单调递增）
     * 所有节点，无需持久化
     */
    private long lastApplied;

    /**
     * 领导者需要维护集群内的其他节点信息
     * key:节点id  value:节点
     */
    private List<Peer> peerList;

    /**
     * 节点状态
     */
    private NodeState state = NodeState.FOLLOWER;

    /**
     * 选举超时时间，从 150-300ms 中随机选择
     */
    private Integer electionTimeouts;

    /**
     * 选举开始定时器
     */
    private HashedWheelTimer electionTimer = new HashedWheelTimer(
            new NamedThreadFactory("RaftElectionsTimeOutTimer", true),
            DEFAULT_ELECTION_PERIOD, TimeUnit.MILLISECONDS, 512);


    public RaftNode() {
        this.electionTimeouts = randomElectionTimeouts();
        electionTimer.newTimeout(new ElectionTask(this), this.electionTimeouts, TimeUnit.MILLISECONDS);

    }

    private Integer randomElectionTimeouts() {
        return ThreadLocalRandom.current().nextInt(150, 300);
    }
}
