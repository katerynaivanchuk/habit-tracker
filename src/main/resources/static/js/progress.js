//PROFILE ACTION 
const profileButton = document.getElementById("profileButton");
        const profileMenu = document.getElementById("profileMenu");

        profileButton.addEventListener("click", () => {
            profileMenu.classList.toggle("hidden");
        });

        // закривання при кліку поза меню
        window.addEventListener("click", (e) => {
            if (!profileButton.contains(e.target) && !profileMenu.contains(e.target)) {
            profileMenu.classList.add("hidden");
            }
        });

//LOG OUT BUTTON 
const logOutModal = document.getElementById("logOutModal");
const closeLogOutModal = document.getElementById("closeLogOutModal");
const cancelLogOutModal = document.getElementById("cancelLogOutModal");

document.querySelectorAll(".logOutBtn").forEach(btn => {
    btn.addEventListener("click", () => {
        logOutModal.classList.remove("hidden");
        logOutModal.classList.add("flex");
    });
});

[closeLogOutModal,cancelLogOutModal].forEach(btn =>
    btn.addEventListener("click", () => {
        logOutModal.classList.add("hidden");
        logOutModal.classList.remove("flex");
    })
);

logOutModal.addEventListener("click", e => {
    if(e.target === logOutModal) logOutModal.classList.add("hidden");
});

//
const container = document.getElementById("category-progress");
const categoriesData = JSON.parse(container.dataset.categories);

const colorMap = {
    "Health": "#22C55E",
    "Creativity": "#DB20BC",
    "Self-Care":"#FCD445",
    "Finances": "#2132F0",
    "Home": "#64E3FF",
    "Learning": "#3B82F6",
    "Nutrition": "#8CFADE",
    "Productivity": "#F59E0B",
    "Sleep": "#6366F1"
};

const categories = Object.entries(categoriesData).map(([name, progress]) => ({
    name,
    progress,
    color: colorMap[name] || "#8B5CF6" // фіолетовий за замовчуванням
}));

container.innerHTML = "";

categories.forEach(c => {
    const card = `
  <div class="flex items-center bg-lightViolet dark:bg-gray-600 border-b-gray-700 rounded-full px-6 py-3 w-[270px]">
    <svg viewBox="0 0 36 36" class="w-10 h-10">
      <path class="text-gray-200" stroke="currentColor" stroke-width="3.8" fill="none"
        d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831" />
      <path stroke="${c.color}" stroke-width="3.8" fill="none"
        stroke-dasharray="${c.progress}, 100"
        stroke-linecap="round"
        d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831" />
    </svg>
    <div class="ml-3">
      <span class="block font-semibold text-gray-800 dark:text-white">${c.name}</span>
      <span class="text-gray-400 text-sm dark:text-gray-100">${c.progress}% complete</span>
    </div>
  </div>`;
    container.innerHTML += card;
});

// Анімація заповнення кола
setTimeout(() => {
    document.querySelectorAll(".progress-path").forEach((path, i) => {
        const progress = categories[i].progress;
        path.style.transition = "stroke-dasharray 1.5s ease-in-out";
        path.setAttribute("stroke-dasharray", `${progress}, 100`);
    });
}, 200);