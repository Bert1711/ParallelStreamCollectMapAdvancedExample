package com.zaroyan;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Zaroyan
 */
class Student {
    private String name;
    private Map<String, Integer> grades;

    public Student(String name, Map<String, Integer> grades) {
        this.name = name;
        this.grades = grades;
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }
}

public class ParallelStreamCollectMapAdvancedExample {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("Student1", Map.of("Math", 90, "Physics", 85)),
                new Student("Student2", Map.of("Math", 95, "Physics", 88)),
                new Student("Student3", Map.of("Math", 88, "Chemistry", 92)),
                new Student("Student4", Map.of("Physics", 78, "Chemistry", 85))
        );

        // Используем параллельный поток для обработки студентов
        Map<String, Double> averageGrades = students.parallelStream()
                // Объединяем все оценки всех студентов в один поток Map.Entry
                .flatMap(student -> student.getGrades().entrySet().stream())
                // Группируем оценки по предметам
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        // Вычисляем среднюю оценку для каждого предмета
                        Collectors.averagingInt(Map.Entry::getValue)
                ));

        // Выводим результат
        System.out.println("Средние оценки по предметам:");
        averageGrades.forEach((subject, averageGrade) ->
                System.out.println(subject + ": " + averageGrade));
    }
}