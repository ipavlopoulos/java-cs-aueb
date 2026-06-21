# Java Examples Collection

Designed for the AUEB CS BSc Java Course:
https://eclass.aueb.gr/courses/INF176/

This repository contains small, runnable Java examples and course projects used
to teach core programming and object-oriented programming concepts. Topic-based
examples live under `gr/aueb/cs/examples`, while larger mini-projects live under
`gr/aueb/cs/projects`.

## Project Structure

### `gr/aueb/cs/examples/introduction`

Introductory Java examples covering variables, control flow, simple classes, and
basic simulation.

### `gr/aueb/cs/examples/strings`

Examples for working with Java strings and common string operations.

### `gr/aueb/cs/examples/inheritance`

Examples for inheritance, `final`, dynamic binding, and polymorphism.

### `gr/aueb/cs/examples/abstraction`

Examples for abstract classes and salary-based employee models.

### `gr/aueb/cs/examples/interfaces`

Small demonstrations of interfaces and interface-based design.

### `gr/aueb/cs/examples/collections`

Examples using Java collections such as lists, queues, iterators, and traversal
patterns.

### `gr/aueb/cs/examples/generics`

Examples for generic classes, generic methods, bounded type parameters,
comparators, sorting, and type erasure.

### `gr/aueb/cs/examples/exceptions`

Examples for exception handling, throwing exceptions, and safer error handling.

### `gr/aueb/cs/examples/files`

Examples for reading and writing files, UTF-8 text, URLs, and CSV-style input.

### `gr/aueb/cs/examples/unit`

A small calculator example with a matching unit test.

### `gr/aueb/cs/examples/profiling`

Simple performance and profiling examples comparing data structures, primitive
types, wrapper types, and buffer capacity choices.

### `gr/aueb/cs/examples/graphics`

Graphical Java examples, including a tag cloud visualization.

### `gr/aueb/cs/examples/sound`

Audio-related examples, including a simple alarm demo.

### `gr/aueb/cs/examples/review`

Review examples for dynamic dispatch, constructors, exceptions, anonymous
classes, and randomness.

### `gr/aueb/cs/projects`

Larger examples and student-friendly mini-projects:

- `montyhall`: Monty Hall game logic, strategies, console UI, and simulation.
- `walkroute`: Walking route demos using linked structures, search, and agents.
- `captcha`: A simple CAPTCHA generator and checker.
- `javagotchi`: A virtual pet project.
- `javagotchiWeb`: A Spring Boot web version of the Javagotchi project.

## How to Run

From the repository root, compile and run a single class by using its package
path. For example:

```bash
javac gr/aueb/cs/examples/introduction/Variables.java
java gr.aueb.cs.examples.introduction.Variables
```

Another example:

```bash
javac gr/aueb/cs/examples/inheritance/Polymorphism.java
java gr.aueb.cs.examples.inheritance.Polymorphism
```

For examples that define multiple classes in the same package, compile the
source file and then run the public class:

```bash
javac gr/aueb/cs/examples/interfaces/Demo.java
java gr.aueb.cs.examples.interfaces.Demo
```

The Spring Boot project has its own README:

```text
gr/aueb/cs/projects/javagotchiWeb/README.md
```

## Running Tests

The `gr/aueb/cs/examples/unit` package contains small JUnit 5 tests for the
calculator example. Run them from the repository root with:

```bash
mvn test
```

## Contributing

To contribute to this repository, the [Git cheatsheet](git_cheatsheet.md) may
help with the first steps.

## License

This project is licensed under the [MIT License](LICENSE).
