package com.gonagutor.invitemanager.Files;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

public class CodeGenerator {
    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String digits = "0123456789";
    public static final String alphanum = upper + digits;
    private final Random random;
    private final char[] symbols;
    private final char[] buf;

    public CodeGenerator(int length, Random random) {
        this(length, random, alphanum);
    }

    public CodeGenerator(int length) {
        this(length, new SecureRandom());
    }

    public CodeGenerator() {
        this(21);
    }

    public CodeGenerator(int length, Random random, String symbols) {
        if (length < 1)
            throw new IllegalArgumentException();
        if (symbols.length() < 2)
            throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

}