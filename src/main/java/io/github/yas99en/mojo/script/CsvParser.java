package io.github.yas99en.mojo.script;

import java.util.ArrayList;
import java.util.List;

public final class CsvParser {
    private CsvParser(){}

    public static String[] splitByCommna(String str) {
        StringBuilder builder = new StringBuilder();
        List<String> tokens = new ArrayList<String>();
        boolean escapeEnable = false;
        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch(c) {
            case ',':
                if(escapeEnable) {
                    builder.append(c);
                    escapeEnable = false;
                } else {
                    if(builder.length() != 0) {
                        tokens.add(builder.toString());
                        builder = new StringBuilder();
                    }
                }
                break;
            case '\\':
                if(escapeEnable) {
                    builder.append(c);
                    escapeEnable = false;
                } else {
                    escapeEnable = true;
                }
                break;
            default:
                builder.append(c);
                escapeEnable = false;
            }
        }
        if(builder.length() != 0) {
            tokens.add(builder.toString());
        }
        return tokens.toArray(new String[0]);
    }
}
