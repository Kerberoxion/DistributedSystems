package ru.nsu.fit.krizko.worker.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class WorkerResult {
    private UUID requestId;
    private List<String> words;

    public WorkerResult(UUID requestId, List<String> words){
        this.requestId = requestId;
        this.words = words;
    }
}