package ru.nsu.fit.krizko.worker.service;

import lombok.AllArgsConstructor;
import org.paukov.combinatorics3.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import ru.nsu.fit.krizko.worker.ResultProducer;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class WorkerService{

    private final ResultProducer resultProducer;


    static final Logger log =
            LoggerFactory.getLogger(WorkerService.class);

    private static String toString(List<Character> word) {
        StringBuilder sb = new StringBuilder(word.size());
        for (char c : word) sb.append(c);
        return sb.toString();
    }

    private static String md5(String word) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(word.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void crackHash(UUID requestId, List<Character> alphabet, String hash, int maxLength, int partNumber, int partCount){
        Iterator<List<Character>> it = Generator
                .permutation(alphabet)
                .withRepetitions(maxLength)
                .iterator();

        long size = alphabet.size();
        long total = 0;
        for (int i = 1; i <= maxLength; i++) {
            total+=Math.powExact(size,i);
        }

        long from = total * partNumber / partCount;
        long to   = total * (partNumber + 1) / partCount;

        StopWatch stopWatch = new StopWatch();

        log.info("Worker started sorting through the hashes");
        List<String> words = new ArrayList<>();
        stopWatch.start();

        for (long i = 0; i < from; i++) {
            if (!it.hasNext()) break;
            it.next();
        }

        long count = to - from;

        for (long i = 0; i < count; i++) {
            if (!it.hasNext()) break;

            List<Character> chars = it.next();
            String word = toString(chars);
            String wordHash = md5(word);

            if(wordHash.equals(hash)) {
                words.add(word);
                log.info("Worker added word to the list of matches");
            }

        }
        stopWatch.stop();
        log.info("Worker ended with time: {} sec", stopWatch.getTotalTimeSeconds());

        resultProducer.sendResult(requestId, words);
    }

}


