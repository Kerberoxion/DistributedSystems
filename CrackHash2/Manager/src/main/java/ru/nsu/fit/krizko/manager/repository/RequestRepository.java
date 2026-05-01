package ru.nsu.fit.krizko.manager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.nsu.fit.krizko.manager.model.CrackRequestState;

import java.util.Optional;

public interface RequestRepository extends MongoRepository<CrackRequestState, String> {
    Optional<CrackRequestState> findByRequestId(String requestId);
}
