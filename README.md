# Java Examples Collection

> Designed for the AUEB CS BSc Java Course
>> https://eclass.aueb.gr/courses/INF176/

This repository contains a collection of Java programming examples used for teaching core object-oriented programming (OOP) concepts in an engaging and applied way. Each example is organized as a separate package/module, and includes working code, documentation, and console interaction.

## 📁 Project Structure

Each top-level package contains a complete, runnable Java example:

---

## 📦 Examples Included

### 🎲 `montyhall/`

A full implementation of the Monty Hall game:

- OOP structure (`Door`, `MontyHallGame`, `Strategy`)
- Interactive version (console-based)
- Simulator to run thousands of trials
- Visual comparison of strategies (switch vs stay)

➡️ Teaches: classes, interfaces, composition, simulations, user interaction

---

### 🚶 `walkroutes/`

A pedestrian routing system on a city grid:

- Location graph representation
- Obstacle-aware pathfinding
- Modular packages: `model`, `routing`, `map`, `simulation`

➡️ Teaches: graphs, modular design, file input, BFS-style logic

---

### 🎯 `randomdemo/`

Demonstrates the difference between:

- `Math.random()` (static, simple, no seed)
- `Random` class (object-based, supports seeding)
- Repeatable pseudo-randomness

➡️ Teaches: randomness, testing, debugging with seeded values

---

### 🏨 `hotelbooking/` *(optional extension)*

Simulates a basic hotel booking system with multiple room types.

➡️ Teaches: inheritance, encapsulation, polymorphism

---

### 🎬 `movies/` *(optional extension)*

Loads movie reviews from a file and filters recommendations by genre, rating, or sentiment.

➡️ Teaches: file I/O, `ArrayList`, filtering, basic NLP

---

## 🧪 How to Run

Use the terminal or your favorite IDE (e.g., IntelliJ, Eclipse):

```bash
# Compile from src root
javac montyhall/app/Main.java
java montyhall.app.Main

# Or run another example
javac randomdemo/RandomComparison.java
java randomdemo.RandomComparison
```

> To contribute to this repo, [this cheatsheet](/git_cheatsheet.md) may help with the first steps. 

## 📄 License

This project is licensed under the [MIT License](LICENSE).
