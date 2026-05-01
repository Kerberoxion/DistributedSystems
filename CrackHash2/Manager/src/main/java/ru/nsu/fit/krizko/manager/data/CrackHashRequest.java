package ru.nsu.fit.krizko.manager.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrackHashRequest {
    private String hash;
    private int maxLength;
}
