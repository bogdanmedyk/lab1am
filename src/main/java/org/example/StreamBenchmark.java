package org.example;
import org.openjdk.jmh.annotations.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@State(Scope.Benchmark)
public class StreamBenchmark {

    private static final int SIZE = 10000000;
    private List<Integer> numbers;

    // Ініціалізація колекції випадкових чисел
    @Setup
    public void setup() {
        Random random = new Random();
        numbers = IntStream.generate(() -> random.nextInt(100) + 1)
                .limit(SIZE)
                .boxed()
                .collect(Collectors.toList());
    }

    // Знаходження суми елементів за допомогою Stream
    @Benchmark
    public long sumWithStream() {
        return numbers.stream().mapToLong(Integer::longValue).sum();
    }

    // Знаходження суми елементів за допомогою Parallel Stream
    @Benchmark
    public long sumWithParallelStream() {
        return numbers.parallelStream().mapToLong(Integer::longValue).sum();
    }

    // Обчислення середнього значення за допомогою Stream
    @Benchmark
    public double averageWithStream() {
        return numbers.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
    }

    // Обчислення середнього значення за допомогою Parallel Stream
    @Benchmark
    public double averageWithParallelStream() {
        return numbers.parallelStream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
    }

    // Обчислення стандартного відхилення за допомогою Stream
    @Benchmark
    public double stdDeviationWithStream() {
        double mean = numbers.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
        double variance = numbers.stream()
                .mapToDouble(i -> Math.pow(i - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    // Обчислення стандартного відхилення за допомогою Parallel Stream
    @Benchmark
    public double stdDeviationWithParallelStream() {
        double mean = numbers.parallelStream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
        double variance = numbers.parallelStream()
                .mapToDouble(i -> Math.pow(i - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    // Множення кожного елемента на 2 за допомогою Stream
    @Benchmark
    public List<Integer> multiplyByTwoWithStream() {
        return numbers.stream().map(i -> i * 2).collect(Collectors.toList());
    }

    // Множення кожного елемента на 2 за допомогою Parallel Stream
    @Benchmark
    public List<Integer> multiplyByTwoWithParallelStream() {
        return numbers.parallelStream().map(i -> i * 2).collect(Collectors.toList());
    }

    // Фільтрація (лише парні, що діляться на 3) за допомогою Stream
    @Benchmark
    public List<Integer> filterEvenAndDivisibleByThreeWithStream() {
        return numbers.stream()
                .filter(i -> i % 2 == 0 && i % 3 == 0)
                .collect(Collectors.toList());
    }

    // Фільтрація (лише парні, що діляться на 3) за допомогою Parallel Stream
    @Benchmark
    public List<Integer> filterEvenAndDivisibleByThreeWithParallelStream() {
        return numbers.parallelStream()
                .filter(i -> i % 2 == 0 && i % 3 == 0)
                .collect(Collectors.toList());
    }

}
