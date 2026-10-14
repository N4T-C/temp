# HTML, CSS & JavaScript Lab Programs Directory

This folder contains all exercises cleanly organized so you can open any folder and run `index.html` directly in the browser with double-click.

---

### Folder Map:

| Folder Name | Description | Key Tech Used |
| :--- | :--- | :--- |
| **`ex1_symposium_html/`** | Exercise 1: College Symposium Website (TechNova 2026) | Pure HTML5 (7 pages, forms, tables, semantics) |
| **`ex2_symposium_css/`** | Exercise 2: College Symposium with Full CSS Styling | CSS Grid, Flexbox, Box Model, Sticky Nav, Media Queries |
| **`ex3_bingo_game/`** | Exercise 3: Two-Player 5x5 Bingo Game | JavaScript Array shuffle, DOM manipulation, 5-in-a-row win logic |
| **`shape_rotate_color/`** | Shape 90° Rotation & Color Changer on Click | CSS `transform: rotate()`, `onclick` event listener |
| **`form_validation/`** | Student Registration Form with Validation | JS Regex, Email, 10-digit Phone check, Error spans |
| **`todo_calculator/`** | Simple Arithmetic Calculator & Dynamic Todo List | Basic JS Arithmetic & DOM `createElement` / `remove` |

---

### 1. Shape Rotation & Color Changer (`shape_rotate_color/index.html`)
- **What it does:** Displays a colorful square. Every time you click it, it spins **90 degrees** smoothly (`transform: rotate(90deg)`) and cycles to a new color (crimson -> dodgerblue -> green -> orange -> purple).
- **Core Code to remember:**
  ```javascript
  let angle = 0;
  box.onclick = function() {
    angle += 90;
    box.style.transform = 'rotate(' + angle + 'deg)';
    box.style.backgroundColor = colors[index++ % colors.length];
  }
  ```

---

### 2. Two-Player Bingo Game (`ex3_bingo_game/index.html`)
- **What it does:** Two 5x5 grids randomly filled with numbers 1 to 25. Players enter a number and click **Mark**. It highlights in green on both boards and checks rows, columns, and diagonals for a BINGO win.
- **Core Logic:**
  - `shuffle()`: Shuffles array of 1..25 numbers.
  - `checkWin()`: Loops 5 rows, 5 cols, and 2 diagonals:
    ```javascript
    for (let i = 0; i < 5; i++) {
      if ([0,1,2,3,4].every(c => marked[i * 5 + c])) return true; // row
      if ([0,1,2,3,4].every(r => marked[r * 5 + i])) return true; // col
    }
    ```

---

### 3. Client-Side Form Validation (`form_validation/index.html`)
- **What it does:** Validates Name (min 3 chars), Email (has `@` and `.`), Mobile number (exactly 10 digits using `/^\d{10}$/`), and dropdown selection. Shows red inline errors without refreshing the page.

---

### 4. Calculator & Dynamic List (`todo_calculator/index.html`)
- **What it does:** Performs `+`, `-`, `*`, `/` operations and lets users add and delete items dynamically.
