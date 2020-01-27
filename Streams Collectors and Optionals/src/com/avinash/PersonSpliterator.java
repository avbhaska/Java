package com.avinash;

import com.avinash.model.Person;

import java.util.Spliterator;
import java.util.function.Consumer;

public class PersonSpliterator implements Spliterator<Person> {

    private final Spliterator<String> spliterator;
    private String name;
    private int age;
    private String city;

    public PersonSpliterator(Spliterator<String> spliterator) {
        this.spliterator = spliterator;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Person> action) {
      if  (this.spliterator.tryAdvance(line -> this.name = line) &&
           this.spliterator.tryAdvance(line -> this.age = Integer.parseInt(line)) &&
           this.spliterator.tryAdvance(line -> this.city = line)){

            Person p = new Person(name,age,city);
            action.accept(p);
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<Person> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return spliterator.estimateSize() / 3;
    }

    @Override
    public int characteristics() {
        return spliterator.characteristics();
    }
}
