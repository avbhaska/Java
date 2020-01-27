package com.avinash.jumble;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class Jumble {
    public static void main(String[] args) throws IOException {
        Set<String> shakeepeareWords = Files.lines(Paths.get("files/words.shakespeare.txt"))
                .map(word->word.toLowerCase())
                .collect(Collectors.toSet());

        Set<String> scrableWords = Files.lines(Paths.get("files/ospd.txt"))
                .map(word ->word.toLowerCase())
                .collect(Collectors.toSet());

        System.out.println("# words of Shakespeare :" + shakeepeareWords.size());
        System.out.println("# words of Scrabble :" + scrableWords.size());

        final int [] scrableENScore = {
           //a ,b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q,  r, s, t, u, v, w, x, y, z
             1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
        Function<String, Integer> score = word -> word.chars().map(letter -> scrableENScore[letter - 'a']).sum();

        ToIntFunction<String> intScore = word -> word.chars().map(letter -> scrableENScore[letter - 'a']).sum();

        System.out.println("Score of hello: " + intScore.applyAsInt("hello"));

        String bestWord = shakeepeareWords.stream()
                .filter(word -> scrableWords.contains(word))
                .max(Comparator.comparing(score))
                .get();
        System.out.println("Best word: " + bestWord);

       IntSummaryStatistics summaryStatistics =  shakeepeareWords
                                                .stream().parallel()
                                                .filter(scrableWords::contains)
                                                .mapToInt(intScore)
                                                .summaryStatistics();

        System.out.println("Stats: " + summaryStatistics);

    }
}
