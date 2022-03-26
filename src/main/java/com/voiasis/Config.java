package com.voiasis;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    
    private static final Dotenv dotenv = Dotenv.load();

    static String get(String key) {
        return dotenv.get(key);
    }
}
