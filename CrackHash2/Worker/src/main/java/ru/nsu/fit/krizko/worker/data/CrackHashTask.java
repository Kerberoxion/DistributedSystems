package ru.nsu.fit.krizko.worker.data;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CrackHashTask {
    private UUID requestId;
    private List<Character> alphabet;
    private String hash;
    private int maxLength;
    private int partNumber;
    private int partCount;
}
