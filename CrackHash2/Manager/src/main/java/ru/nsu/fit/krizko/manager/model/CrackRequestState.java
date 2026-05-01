package ru.nsu.fit.krizko.manager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.nsu.fit.krizko.manager.data.WorkerRequest;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "requests")
public class CrackRequestState {
    @Id
    private String id;

    @Indexed(unique = true)
    private String requestId;

    private String hash;
    private int maxLength;

    private RequestStatus status = RequestStatus.IN_PROGRESS;
    private List<String> data = new ArrayList<>();
    private int completedParts = 0;
    private int totalParts;

    public CrackRequestState(String requestId,  String hash, int maxLength, int totalParts) {
        this.requestId = requestId;
        this.hash = hash;
        this.maxLength = maxLength;
        this.totalParts = totalParts;
    }

    public void incrementCompletedParts(){
        completedParts+=1;
    }
}