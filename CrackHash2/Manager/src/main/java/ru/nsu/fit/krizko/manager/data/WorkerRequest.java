package ru.nsu.fit.krizko.manager.data;

import lombok.Getter;
import java.util.List;
import java.util.UUID;

@Getter
public class WorkerRequest {
    private final UUID requestId;
    private final String hash;
    private final int maxLength;
    private final List<Character> alphabet;
    private final int partNumber;
    private final int partCount;


    public WorkerRequest(UUID requestId, String hash, int maxLength, List<Character> alphabet, int partNumber, int partCount){
        this.requestId = requestId;
        this.alphabet = alphabet;
        this.hash = hash;
        this.maxLength = maxLength;
        this.partNumber = partNumber;
        this.partCount = partCount;
    }
}
