package ru.nsu.fit.krizko.manager;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.krizko.manager.data.CrackHashRequest;
import ru.nsu.fit.krizko.manager.data.CrackHashResponse;
import ru.nsu.fit.krizko.manager.data.CrackStatusResponse;
import ru.nsu.fit.krizko.manager.data.WorkerResult;
import ru.nsu.fit.krizko.manager.model.CrackRequestState;
import ru.nsu.fit.krizko.manager.service.ManagerService;

import java.util.UUID;

@RestController
@RequestMapping("/api/hash")
public class ManagerController {

    private final ManagerService managerService;
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }


    @PostMapping("/crack")
    public CrackHashResponse crack(@RequestBody CrackHashRequest request) {

        UUID requestId = managerService.createRequest(
                request.getHash(),
                request.getMaxLength()
        );

        return new CrackHashResponse(requestId);
    }

    @GetMapping("/status")
    public ResponseEntity<CrackStatusResponse> status(
            @RequestParam UUID requestId
    ) {

        CrackRequestState state = managerService.getStatus(requestId);

        if (state == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                new CrackStatusResponse(
                        state.getStatus().name(),
                        state.getData()
                )
        );
    }

    @PostMapping("/result")
    public ResponseEntity<Void> acceptResult(@RequestBody WorkerResult result) {
        managerService.acceptResult(result);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ping-worker")
    public String pingWorker(int workerIndex) {
        return managerService.ping(workerIndex);
    }
}
