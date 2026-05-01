package ru.nsu.fit.krizko.manager.data;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CrackHashResponse {

    private final UUID requestId;

    public CrackHashResponse(UUID requestId) {
        this.requestId = requestId;
    }

}
