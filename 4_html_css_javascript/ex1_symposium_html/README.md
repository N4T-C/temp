# HTML, CSS & JavaScript Practical Guide (Exam / Lab Questions)

> **Folder:** `C:\Users\natha\Downloads\wad_test\4_html_css_javascript\`

---

## 1. Quick Testing
Open [index.html](file:///C:/Users/natha/Downloads/wad_test/4_html_css_javascript/index.html) by double-clicking it. It runs instantly in Chrome/Edge offline.

It contains:
- **Image Slider:** Previous and Next buttons to change pictures dynamically.
- **Rotation Animation:** Clicking the image or the rotate button executes a smooth 360-degree rotation.
- **Dynamic Captions:** Shows the current slide name and count.

---

## 2. Common Practical / Viva Code Snippets

### A. Next and Previous Image Changing Logic
```javascript
// Current index tracker
let currentIndex = 0;
const images = ["img1.png", "img2.png", "img3.png"];

function nextImage() {
  // Using modulo (%) loops back to index 0 after the last image
  currentIndex = (currentIndex + 1) % images.length;
  document.getElementById("myImage").src = images[currentIndex];
}

function prevImage() {
  // Prevents negative numbers when clicking 'Prev' on the first image
  currentIndex = (currentIndex - 1 + images.length) % images.length;
  document.getElementById("myImage").src = images[currentIndex];
}
```

### B. Image Rotation on Click
```css
/* In CSS */
.my-img {
  transition: transform 0.6s ease-in-out; /* Smooth transition */
}

.rotate-360 {
  transform: rotate(360deg); /* Full 360-degree spin */
}
```
```javascript
// In JavaScript
function rotateImage() {
  const img = document.getElementById("myImage");
  img.classList.toggle("rotate-360"); // Adds or removes the rotation class
}
```

---

## 3. Top Viva Questions for HTML / CSS / JS
1. **What is the DOM?**
   - Document Object Model. It represents the HTML page as a tree of objects that JavaScript can modify (`document.getElementById()`).
2. **Difference between `let`, `const`, and `var`?**
   - `const`: Cannot be reassigned.
   - `let`: Block-scoped, can be reassigned.
   - `var`: Old function-scoped declaration (avoided in modern code).
3. **How does CSS `transition` work?**
   - It animates the change between property states (e.g. from 0deg to 360deg rotation) over a specified duration rather than snapping instantaneously.
4. **Why use `addEventListener` instead of inline `onclick`?**
   - `addEventListener` separates HTML structure from JavaScript logic and allows attaching multiple listeners to the same element.
