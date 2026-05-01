package ru.nsu.fit.krizko.manager.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class WorkerResult {
    private UUID requestId;
    private List<String> words;
}
