package ru.nsu.fit.krizko.manager.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class CrackRequestState {

    private RequestStatus status;
    private List<String> data = new ArrayList<>();
    private int completedParts = 0;
    private int totalParts;

    public CrackRequestState(RequestStatus status, int totalParts) {
        this.status = status;
        this.totalParts = totalParts;
    }

    public void incrementCompletedParts(){
        completedParts+=1;
    }
}