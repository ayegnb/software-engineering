package kz.edu.nu.cs.teaching;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class App {
    static final String h = "~~~~~~~~~~~~\n";
    
    public static void main(String[] args) {
        
        System.out.println("\nTask 1 " + h);
        wordcount(getTestLinesStream());
        
        System.out.println("\nTask 2 " + h);
        filterByWordLength(getTestLinesStream());

        System.out.println("\nTask 3 " + h);
        groupWordsByFirstCharacter(getTestLinesStream());
    }
    
    /*
     * Task 1, count words in entire file
     */
    public static void wordcount(Stream<String> stream) {
        long count = stream.flatMap(str -> Stream.of(str.split("[ ,.;:()\n]")))
                .filter(s -> s.length() > 0)
                .count();
        System.out.println(count);
    }
    
    /*
     * Task 2, filter lines by lengths of longest words
     */
    public static boolean containsLongString(String line) {
        String[] strings = line.split("[ ,.;:()\n]");
        for (String string : strings) {
            if (string.length() > 7) return true;
        }
        return false;
    }

    public static void filterByWordLength(Stream<String> stream) {
        stream.filter(line -> containsLongString(line))
                .forEach(line -> System.out.println(line));
    }
    
    /*
     * Task 3, group words by first character
     */
    public static void groupWordsByFirstCharacter(Stream<String> stream) {
        Map<Character, Integer> numOfFirstChars = new HashMap<>();
        for (int i = 97; i <= 122; i++) {
            numOfFirstChars.put((char)i, 0);
        }
        stream.flatMap(str -> Stream.of(str.split("[ ,.;:()\n]")))
                .filter(s -> s.length() > 0)
                .forEach(str -> {
                    str = str.toLowerCase();
                    numOfFirstChars.put(str.charAt(0), numOfFirstChars.get(str.charAt(0)) + 1);
                });
        System.out.println(numOfFirstChars.entrySet());
    }
    
    /*
     * Return Stream of lines from file
     */
    public static Stream<String> getTestLinesStream() {
        File file = new File("lambtest.txt");
        
        try {
            return Files.lines(file.toPath());
        } catch (Exception e) {
            System.out.println("Error reading from file");
            return null;
        }
    }
    
    
}
