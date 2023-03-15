package com.github.buywatermelon.raft.study.storage;

import lombok.Data;

@Data
public class Log {

    private long term;

    private long index;
}
