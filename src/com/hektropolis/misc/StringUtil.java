package com.hektropolis.misc;

import java.util.ArrayList;
import java.util.List;

public class StringUtil
{
    public static List<String> wrapWords(final String text, int lineLength) {
        final String[] intendedLines = text.split("\\n");
        final ArrayList<String> lines = new ArrayList<String>();
        String[] array;
        for (int length = (array = intendedLines).length, i = 0; i < length; ++i) {
            final String intendedLine = array[i];
            final String[] words = intendedLine.split(" ");
            StringBuilder buffer = new StringBuilder();
            String[] array2;
            for (int length2 = (array2 = words).length, j = 0; j < length2; ++j) {
                final String word = array2[j];
                if (word.length() >= lineLength) {
                    if (buffer.length() != 0) {
                        lines.add(buffer.toString());
                    }
                    lines.add(word);
                    buffer = new StringBuilder();
                }
                else {
                    if (buffer.length() + word.length() >= lineLength) {
                        lines.add(buffer.toString());
                        buffer = new StringBuilder();
                    }
                    if (buffer.length() != 0) {
                        buffer.append(' ');
                    }
                    buffer.append(word);
                }
            }
            lines.add(buffer.toString());
        }
        return lines;
    }
}