package com.github.buywatermelon.raft.study.protocol;

import lombok.Builder;

@Builder
public class RequestVote {

    private int candidateId;

    private long term;

    private long lastLogIndex;

    private long lastLogTerm;
}
