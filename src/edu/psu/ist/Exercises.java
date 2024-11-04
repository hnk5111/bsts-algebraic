package edu.psu.ist;
import java.util.function.Function;
import java.util.TreeSet;

public final class Exercises {

    /**
     * Creates a greeting message based on the contents of a binary search tree (BST).
     *
     * <p>Uses <a href="https://docs.oracle.com/en/java/javase/17/language/switch-expressions-and-statements.html">Java 17 switch expressions</a> and pattern matching:</p>
     * <ul>
     *   <li><strong>Deconstruction:</strong> Breaks down a BST node into its left subtree, data, and right subtree.</li>
     *   <li><strong>Wildcard (_):</strong> Matches any value but ignores it.</li>
     * </ul>
     *
     * <p>Handled cases:</p>
     * <ul>
     *   <li><strong>NonEmpty:</strong> Deconstructs the node to use its data and left subtree.</li>
     *   <li><strong>Empty:</strong> Matches with a wildcard.</li>
     * </ul>
     *
     * @param t the binary search tree to create the greeting from
     * @return a greeting string based on the tree's contents
     *
     * @see <a href="https://blog.jetbrains.com/idea/2020/09/java-15-and-intellij-idea/">Sealed Interfaces and Records Article</a>
     * @see <a href="https://docs.oracle.com/en/java/javase/17/language/switch-expressions-and-statements.html">Java Switch Documentation</a>
     */
    public static String hello(BSTree t) {
        String myReturnStr = switch (t) {
            case NonEmpty(var l, var d, _) ->
                    "Hello, I'm a BST with data: " + d + "\n" +
                    "Here's the size of my left subtree: " + l.size();
            case Empty e -> "Empty :-(";
        };
        return myReturnStr;
    }

    // version 1: newer
    public static int sumAll(BSTree t){
        int theSum = switch(t){
            case Empty e -> 0;
            case NonEmpty e -> {
                BSTree left = e.left();
                BSTree right = e.right();
                int data = e.data();
                yield data + sumAll(left) + sumAll(right);
            }
        };

        return theSum;
    }

    // version 2
    public static int sumAll2(BSTree t){
        int theSum = switch(t){
            case Empty _ -> 0;
            case NonEmpty(BSTree left, Integer d, BSTree right) -> {
                yield d + sumAll2(left) + sumAll2(right);
            }
        };
        return theSum;
    }

    // version 3
    public static int sumAll3(BSTree t){
        return switch(t){
            case Empty _ -> 0;
            case NonEmpty(BSTree left, Integer d, BSTree right) ->
                    d + sumAll3(left) + sumAll3(right);
        };
    }

    public static int sumLeafs(BSTree t){
        return switch (t){
            case Empty _ -> 0;
            //case 2: were looking at a leaf node (left and right subtree are empty)
            case NonEmpty(Empty _, var d, Empty _) -> d;
            //case 3: were looking at a non-leaf node
            case NonEmpty(var left, var d, var right) -> sumLeafs(left) + sumLeafs(right);
        };
    }

    public static int maxDepth(BSTree t){
        return switch(t){
            case Empty _ -> 0;
            //case 2: were looking at a leaf node (ALL leafs have a depth of 1)
            case NonEmpty(Empty _, _, Empty _) -> 1;
            case NonEmpty(BSTree left, Integer d, BSTree right) ->
                    1 + Math.max(maxDepth(left), maxDepth(right));
        };
    }

    public static int average(BSTree t) {
        int count = 0;
        int result = switch (t) {
            case Empty _ -> 0;
            case NonEmpty(BSTree left, Integer d, BSTree right) -> {
                yield d + sumAll(left) + sumAll(right);
            }
        };
        return result / (t.size());
    }

    public static TreeSet<Integer> toList(BSTree t) {
        TreeSet<Integer> resultSet = new TreeSet<>();
        switch (t) {
            case Empty _ -> {}
            case NonEmpty(BSTree left, Integer d, BSTree right) -> {
                resultSet.addAll(toList(left));
                resultSet.add(d);
                resultSet.addAll(toList(right));
            }
        }
        return resultSet;
    }

    public static boolean pathSum(BSTree t, int target) {
        return switch (t) {
            case Empty _ -> false;
            case NonEmpty(BSTree left, Integer d, BSTree right) -> {
                int newTarget = target - d;
                if (left instanceof Empty && right instanceof Empty) {
                    yield newTarget == 0;
                }
                yield pathSum(left, newTarget) || pathSum(right, newTarget);
            }
        };
    }

    public static BSTree mirror(BSTree t) {
        return switch (t) {
            case Empty _ -> t;
            case NonEmpty(BSTree left, Integer d, BSTree right) ->
                    new NonEmpty(mirror(right), d, mirror(left));
        };
    }

    public static boolean allSatisfy(BSTree t, Function<Integer, Boolean> f) {
        return switch (t) {
            case Empty _ -> true;
            case NonEmpty(BSTree left, Integer d, BSTree right) ->
                    f.apply(d) && allSatisfy(left, f) && allSatisfy(right, f);};
    }

    public static void main(String[] args) {
        BSTree tr = BSTree.empty()
                .insert(10)
                .insert(5)
                .insert(20)
                .insert(15);
        int res = Exercises.sumAll(tr);
        System.out.println(res); // sum of all nodes is 50

        // declaring and initializing a sample bst...
        // will be using `var` for decls here on out
        var tr2 = BSTree.empty()
                .insert(10)
                .insert(5)
                .insert(20)
                .insert(15);
        int res2 = Exercises.sumLeafs(tr2);
        System.out.println(res2); // 20 (sum of leaf nodes 5 and 15)

        var tr3 = BSTree.empty()
                .insert(10)
                .insert(5)
                .insert(20)
                .insert(25);
        int res3 = Exercises.maxDepth(tr3);
        System.out.println(res3); // 3

        //average
        var tr4 = BSTree.empty()
                .insert(8)
                .insert(2);
        int res4 = Exercises.average(tr4);
        System.out.println(res4); // 5

        //toList
        var tr5 = BSTree.empty()
                .insert(10)
                .insert(5)
                .insert(20)
                .insert(15);
        TreeSet<Integer> res5 = Exercises.toList(tr5);
        System.out.println(res5); // [5, 10, 15, 20]

        //pathSum
        var tr6 = BSTree.empty()
                .insert(10)
                .insert(5)
                .insert(15)
                .insert(1)
                .insert(7);
        // any leaf nodes with a path down that sums to 22?
        boolean res6 = Exercises.pathSum(tr6, 22); // True (10 -> 5 -> 7)
        System.out.println(res6); // true

        //mirror
        var tr7 = BSTree.empty()
                .insert(10)
                .insert(5)
                .insert(15);
        BSTree mirrored = Exercises.mirror(tr7);
        // should print a mirrored preorder traversal
        System.out.println(mirrored.preOrder());

        //allSatisfy
        var tr8 = BSTree.empty()
                .insert(10)
                .insert(5)
                .insert(20)
                .insert(15);

        // example 1: check if all nodes are greater than 0
        boolean allPositive = Exercises.allSatisfy(tr8, (Integer x) -> x > 0);
        System.out.println(allPositive); // true, since all nodes are positive

        // example 2: check if all nodes are less than 10
        // (can actually omit the `Integer` on the param `x`)
        boolean allLessThan10 = Exercises.allSatisfy(tr8, x -> x < 10);
        System.out.println(allLessThan10); // false, since 10, 20, and 15 are not less than 10

        // example 3: check if all nodes are even
        boolean allEven = Exercises.allSatisfy(tr8, x -> x % 2 == 0);
        System.out.println(allEven); // false, since 5 and 15 are odd

        // example 4: check if all nodes are divisible by 5
        boolean allDivisibleBy5 = Exercises.allSatisfy(tr8, x -> x % 5 == 0);
        System.out.println(allDivisibleBy5); // true, since all nodes are divisible by 5```

    }
}
