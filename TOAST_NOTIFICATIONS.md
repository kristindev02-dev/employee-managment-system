# Toast Notification Implementation Guide

> This guide is tailored to your existing project stack:  
> **Bootstrap 5 + `js/flash.js` + `style.css`**

---

## 1. How the System Works

Your `js/flash.js` already provides a working toast system. It:

- Dynamically creates a `#toast-container` fixed at the top-right of the screen
- Builds Bootstrap `alert` elements with types like `success`, `danger`, `warning`, `info`
- Plays a slide-in (`toast-in`) and slide-out (`toast-out`) CSS animation
- Auto-dismisses after a configurable delay (default: 5 seconds)
- Is exposed globally as `window.showFlashMessage(type, message, durationMs)`

---

## 2. Required CSS (add to `style.css`)

The animations referenced by `flash.js` (`toast-in`, `toast-out`) need to be defined.  
Add this block to the **bottom** of `style.css`:

```css
/* ─── Toast / Flash Notification ─────────────────────────── */
#toast-container {
    position: fixed;
    top: 1.25rem;
    right: 1.25rem;
    z-index: 9999;
    display: flex;
    flex-direction: column;
    gap: 0.6rem;
    max-width: 360px;
    width: 100%;
    pointer-events: none;   /* container itself does not block clicks */
}

/* Slide-in from the right */
@keyframes toastSlideIn {
    from {
        opacity: 0;
        transform: translateX(110%);
    }
    to {
        opacity: 1;
        transform: translateX(0);
    }
}

/* Slide-out to the right */
@keyframes toastSlideOut {
    from {
        opacity: 1;
        transform: translateX(0);
    }
    to {
        opacity: 0;
        transform: translateX(110%);
    }
}

.toast-in {
    animation: toastSlideIn 0.28s cubic-bezier(0.22, 1, 0.36, 1) forwards;
}

.toast-out {
    animation: toastSlideOut 0.22s ease-in forwards;
}
```

---

## 3. Include `flash.js` in Every HTML Page

Add the script tag **before** the closing `</body>` tag in each HTML file,  
**before** your page-specific scripts:

```html
<!-- flash.js must come before any script that calls showFlashMessage -->
<script src="js/flash.js"></script>
<script src="js/your-page-script.js"></script>
```

---

## 4. API Reference

```js
// Signature
showFlashMessage(type, message, autoDismissMs);
//               ^      ^        ^
//               |      |        └── optional, default = 5000 (5 seconds)
//               |      └────────── HTML string allowed
//               └───────────────── Bootstrap color keyword (see table below)
```

| `type` value | Visual color | When to use |
|---|---|---|
| `"success"` | Green | Record saved, action completed |
| `"danger"` | Red | API error, delete failed, validation error |
| `"warning"` | Yellow | Partial success, action may have side effects |
| `"info"` | Blue | Neutral status message, loading hint |

---

## 5. Usage Examples

### Success — after a save/create

```js
showFlashMessage("success", "Employee added successfully!");
```

### Error — after a failed API call

```js
showFlashMessage("danger", "Failed to save. Please try again.");
```

### Warning

```js
showFlashMessage("warning", "No changes were detected.");
```

### Info

```js
showFlashMessage("info", "Loading data, please wait…");
```

### With a custom duration (10 seconds)

```js
showFlashMessage("success", "Record updated!", 10000);
```

### With HTML content (icon + bold text)

```js
showFlashMessage(
    "success",
    `<i class="bi bi-check-circle-fill me-2"></i><strong>Saved!</strong> Employee record updated.`
);
```

---

## 6. Integration Pattern — Fetch API Calls

Wrap every `fetch` in your page JS using this pattern:

```js
async function saveEmployee(data) {
    try {
        const response = await fetch("/api/employees", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            // Server returned 4xx / 5xx
            const err = await response.json().catch(() => ({}));
            showFlashMessage("danger", err.message || "Something went wrong.");
            return;
        }

        showFlashMessage("success", "Employee added successfully!");

    } catch (networkError) {
        // No internet / server unreachable
        showFlashMessage("danger", "Network error. Check your connection.");
    }
}
```

### Delete pattern

```js
async function deleteEmployee(id) {
    try {
        const response = await fetch(`/api/employees/${id}`, { method: "DELETE" });

        if (!response.ok) {
            showFlashMessage("danger", "Failed to delete employee.");
            return;
        }

        showFlashMessage("success", "Employee deleted.");
        loadEmployees(); // refresh table

    } catch {
        showFlashMessage("danger", "Network error.");
    }
}
```

---

## 7. Full Working Demo (standalone test)

Paste this in a browser console on any page that has loaded `flash.js` to verify everything works:

```js
// Test all four types one after another
const types = ["success", "danger", "warning", "info"];
const messages = [
    "✅ Record saved successfully!",
    "❌ Something went wrong.",
    "⚠️ Proceed with caution.",
    "ℹ️ Data is loading…"
];

types.forEach((type, i) => {
    setTimeout(() => showFlashMessage(type, messages[i]), i * 1200);
});
```

---

## 8. Common Mistakes to Avoid

| Mistake | Fix |
|---|---|
| Calling `showFlashMessage` before `flash.js` loads | Move `<script src="js/flash.js">` higher, before page scripts |
| Passing `"error"` as type | Bootstrap uses `"danger"` not `"error"` |
| No CSS for `toast-in` / `toast-out` | Add the CSS block from **Section 2** above |
| Toast appears but has no animation | Check for typos in the CSS class names |
| Multiple toasts stacking off-screen | Ensure `#toast-container` has `max-width` and is `position: fixed` |

---

## 9. Quick Reference Card

```js
// ✅ Success
showFlashMessage("success", "Done!");

// ❌ Error
showFlashMessage("danger", "Failed!");

// ⚠️ Warning
showFlashMessage("warning", "Be careful!");

// ℹ️ Info
showFlashMessage("info", "FYI…");

// ⏱ Custom duration (ms)
showFlashMessage("success", "Saved!", 8000);
```
