package com.github.buywatermelon.raft.study.task;

import com.github.buywatermelon.raft.study.node.NodeState;
import com.github.buywatermelon.raft.study.node.RaftNode;
import com.github.buywatermelon.raft.study.protocol.RequestVote;
import com.github.buywatermelon.raft.study.storage.Log;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.timer.Timeout;
import org.apache.dubbo.common.timer.TimerTask;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ElectionTask implements TimerTask {

    private final RaftNode raftNode;

    @Override
    public void run(Timeout timeout) throws Exception {
        log.info("begin request vote");

        raftNode.setState(NodeState.CANDIDATE);

        Log lastLog = raftNode.getLogEntries().get(raftNode.getLogEntries().size() - 1);
        RequestVote requestVote = RequestVote.builder()
                .candidateId(raftNode.getNodeId())
                .term(raftNode.getCurrentTerm())
                .lastLogTerm(lastLog.getTerm())
                .lastLogIndex(lastLog.getIndex())
                .build();

        raftNode.getPeerList()
                .parallelStream()
                .forEach(peer -> {

                });
    }
}
