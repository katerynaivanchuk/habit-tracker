// GLOBAL THEME RESTORE (for all pages)
const html = document.documentElement;

function setTheme(mode) {
    if (mode === 'dark') {
        html.classList.add('dark');
        localStorage.setItem('theme', 'dark');
    } else {
        html.classList.remove('dark');
        localStorage.setItem('theme', 'light');
    }
}

// restore theme on page load
const savedTheme = localStorage.getItem('theme') || 'light';
setTheme(savedTheme);
