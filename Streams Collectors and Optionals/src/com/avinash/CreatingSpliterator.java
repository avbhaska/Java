package com.avinash;

import com.avinash.model.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CreatingSpliterator {

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("files/people.txt");

        Stream<String> lines = Files.lines(path);

        Spliterator<String> lineSpliterator = lines.spliterator();
        Spliterator<Person> peopleSpliterator = new PersonSpliterator(lineSpliterator);
        Stream<Person> person =   StreamSupport.stream(peopleSpliterator,false); //false for no parallel stream
        person.forEach(System.out::println);
    }

}
