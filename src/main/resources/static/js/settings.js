document.addEventListener("DOMContentLoaded", () => {

    // LIGHT / DARK THEME BUTTONS
    const lightBtn = document.getElementById('lightBtn');
    const darkBtn = document.getElementById('darkBtn');

    // restore theme button state
    const savedTheme = localStorage.getItem("theme") || "light";

    if (savedTheme === "dark") {
        darkBtn.classList.add("bg-violet-400", "text-white");
        lightBtn.classList.add("hover:text-gray-700");
        lightBtn.classList.remove("bg-violet-400", "text-white");
    } else {
        lightBtn.classList.add("bg-violet-400", "text-white");
        lightBtn.classList.remove("hover:text-gray-700");
        darkBtn.classList.add("hover:text-gray-700");
        darkBtn.classList.remove("bg-violet-400", "text-white");
    }

    // handle clicks
    lightBtn.addEventListener("click", () => {
        setTheme("light");
        lightBtn.classList.add("bg-violet-400", "text-white");
        lightBtn.classList.remove("hover:text-gray-700");
        darkBtn.classList.add("hover:text-gray-700");
        darkBtn.classList.remove("bg-violet-400", "text-white");
    });

    darkBtn.addEventListener("click", () => {
        setTheme("dark");
        darkBtn.classList.add("bg-violet-400", "text-white");
        darkBtn.classList.remove("hover:text-gray-700");
        lightBtn.classList.add("hover:text-gray-700");
        lightBtn.classList.remove("bg-violet-400", "text-white");
    });



    // LANGUAGE BUTTONS
    const lightENBtn = document.getElementById('lightENBtn');
    const darkUABtn = document.getElementById('darkUABtn');

    function setLanguage(lang) {
        if (lang === 'ua') {
            darkUABtn.classList.add('bg-violet-400', 'text-white');
            darkUABtn.classList.remove("hover:text-gray-700")
            lightENBtn.classList.add("hover:text-gray-700");
            lightENBtn.classList.remove('bg-violet-400', 'text-white');
            lightENBtn.classList.add('text-gray-500');
        } else {
            lightENBtn.classList.add('bg-violet-400', 'text-white');
            lightENBtn.classList.remove("hover:text-gray-700");
            darkUABtn.classList.remove('bg-violet-400', 'text-white');
            darkUABtn.classList.add('text-gray-500');
            darkUABtn.classList.add("hover:text-gray-700");
        }
        localStorage.setItem('language', lang);
    }

    lightENBtn.addEventListener('click', () => setLanguage('en'));
    darkUABtn.addEventListener('click', () => setLanguage('ua'));

    const savedLang = localStorage.getItem('language') || 'en';
    setLanguage(savedLang);



    // LOG OUT MODAL
    const logOutModal = document.getElementById("logOutModal");
    const closeLogOutModal = document.getElementById("closeLogOutModal");
    const cancelLogOutModal = document.getElementById("cancelLogOutModal");

    document.querySelectorAll(".logOutBtn").forEach(btn => {
        btn.addEventListener("click", () => {
            logOutModal.classList.remove("hidden");
            logOutModal.classList.add("flex");
        });
    });

    [closeLogOutModal, cancelLogOutModal].forEach(btn =>
        btn.addEventListener("click", () => {
            logOutModal.classList.add("hidden");
            logOutModal.classList.remove("flex");
        })
    );

    logOutModal.addEventListener("click", e => {
        if (e.target === logOutModal) logOutModal.classList.add("hidden");
    });

    document.querySelector("#logOutConfirmBtn").addEventListener("click", async () => {
        await fetch("/logout", { method: "POST" });
        window.location.href = "/login?logout";
    });


    // PROFILE MENU
    const profileButton = document.getElementById("profileButton");
    const profileMenu = document.getElementById("profileMenu");

    profileButton.addEventListener("click", () => {
        profileMenu.classList.toggle("hidden");
    });

    window.addEventListener("click", (e) => {
        if (!profileButton.contains(e.target) && !profileMenu.contains(e.target)) {
            profileMenu.classList.add("hidden");
        }
    });
});
