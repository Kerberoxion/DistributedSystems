package ru.nsu.fit.krizko.worker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.krizko.worker.data.CrackHashTask;
import ru.nsu.fit.krizko.worker.service.WorkerService;

@RestController
//@RequestMapping("/internal/api/worker/hash/crack/")
public class WorkerController {

    /*private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }


    @PostMapping("/task")
    public ResponseEntity<Void> CrackHash(@RequestBody CrackHashTask crackHashTask){
        workerService.crackHash(crackHashTask.getRequestId(), crackHashTask.getAlphabet(), crackHashTask.getHash(),crackHashTask.getMaxLength(), crackHashTask.getPartNumber(), crackHashTask.getPartCount());

        return ResponseEntity.ok().build();
    }*/
}