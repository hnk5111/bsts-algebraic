package edu.psu.ist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

public final class ExercisesTests {


    @Test public void testSum01() {
        var tr = BSTree.empty() //
                .insert(10) //
                .insert(5) //
                .insert(20) //
                .insert(15); //
        Assertions.assertEquals(50, Exercises.sumAll(tr));
    }

    @Test public void testSum02() {
        var tr = BSTree.empty() //
                .insert(10);
        Assertions.assertEquals(10, Exercises.sumAll(tr));
    }

    @Test public void testSum03() {
        var tr = BSTree.empty();
        Assertions.assertEquals(0, Exercises.sumAll(tr));
    }

    @Test public void testSumLeafs01() {
        var tr = BSTree.empty()
                .insert(10)
                .insert(5)
                .insert(20)
                .insert(15);
        Assertions.assertEquals(20, Exercises.sumLeafs(tr));
    }

    @Test public void testMaxDepth() {
        var tr = BSTree.empty()
                .insert(10)
                .insert(5)
                .insert(20)
                .insert(25);
        Assertions.assertEquals(3, Exercises.maxDepth(tr));
    }

    @Test public void testAverage(){
        //average
        var tr4 = BSTree.empty()
                .insert(8)
                .insert(2);
        int res4 = Exercises.average(tr4);
        Assertions.assertEquals(5, res4);
    }

    @Test public void testToList(){
        var tr5 = BSTree.empty()
                .insert(10)
                .insert(5)
                .insert(20)
                .insert(15);
        TreeSet<Integer> res5 = Exercises.toList(tr5);
        TreeSet<Integer> resultSet = new TreeSet<>();
            resultSet.add(5);
            resultSet.add(10);
            resultSet.add(15);
            resultSet.add(20);

        Assertions.assertEquals(resultSet, res5);
    }
}
