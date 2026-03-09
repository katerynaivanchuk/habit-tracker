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


//ADD HABIT BUTTON
const addHabitBtn = document.getElementById("addHabitBtn"); 
const modal = document.getElementById("addHabitModal");
const closeModal = document.getElementById("closeModal");
const cancelModal = document.getElementById("cancelModal");

addHabitBtn.addEventListener("click", () => {
    modal.classList.remove("hidden");
    modal.classList.add("flex");
});

[closeModal, cancelModal].forEach(btn =>
    btn.addEventListener("click", () => {
        modal.classList.add("hidden");
        modal.classList.remove("flex");
    })
    );

// Закрити при кліку поза вікном
modal.addEventListener("click", e => {
if (e.target === modal) modal.classList.add("hidden");
});



//EDIT HABIT MODAL
const editModal = document.getElementById("editHabitModal");
const closeEditModal = document.getElementById("closeEditModal");
const cancelEditModal = document.getElementById("cancelEditModal");

document.querySelectorAll(".editHabitBtn").forEach(btn => {
    btn.addEventListener("click", () => {
        const habitId = btn.getAttribute("data-id");
        const habitName = btn.getAttribute("data-name");
        const habitDescription = btn.getAttribute("data-description");
        const habitCategory = btn.getAttribute("data-category");
        const habitEmoji = btn.getAttribute("data-emoji");

        document.getElementById("habitId").value = habitId;
        // Заповнення
        document.getElementById("habitName").value = habitName || "";
        document.getElementById("emojiInput").value = habitEmoji || "";
        editModal.querySelector("textarea").value = habitDescription || "";

        // вибір категорії
        const categorySelect = document.getElementById("habitCategory");
        Array.from(categorySelect.options).forEach(opt => {
            opt.selected = opt.text.trim().toLowerCase() === habitCategory?.trim().toLowerCase();
        });

        editModal.classList.remove("hidden");
        editModal.classList.add("flex");
    });
});


[closeEditModal, cancelEditModal].forEach(btn =>
    btn.addEventListener("click", () => {
        editModal.classList.add("hidden");
        editModal.classList.remove("flex");
    })
    );

// Закрити при кліку поза вікном
editModal.addEventListener("click", e => {
if (e.target === editModal) editModal.classList.add("hidden");
});


//PAUSE HABIT BUTTON
const pauseModal = document.getElementById("pauseHabitModal");
const closePauseModal = document.getElementById("closePauseModal");
const keepActivePauseModal = document.getElementById("keepActivePauseModal");

document.querySelectorAll(".pauseHabitBtn").forEach(btn => {
    btn.addEventListener("click", () => {

        const pauseHabitId = btn.getAttribute("data-id");
        const pauseHabitName = btn.getAttribute("data-name");
        const pauseHabitCategory = btn.getAttribute("data-category");
        const pauseEmoji = btn.getAttribute("data-emoji")

        document.getElementById("pauseHabitId").value= pauseHabitId;
        document.getElementById("pauseHabitName").textContent = pauseHabitName;
        document.getElementById("pauseHabitCategory").textContent = pauseHabitCategory;
        document.getElementById("pauseEmoji").textContent = pauseEmoji;


        pauseModal.classList.remove("hidden");
        pauseModal.classList.add("flex");
    });
});

[closePauseModal, keepActivePauseModal].forEach(btn =>
    btn.addEventListener("click", () => {
        pauseModal.classList.add("hidden");
        pauseModal.classList.remove("flex");
    })
);

pauseModal.addEventListener("click", e => {
    if (e.target === pauseModal) pauseModal.classList.add("hidden");
});

//DELETE HABIT BUTTON
const deleteModal = document.getElementById("deleteHabitModal");
const closeDeleteModal = document.getElementById("closeDeleteModal");
const cancelDeleteModal = document.getElementById("cancelDeleteModal");

document.querySelectorAll(".deleteHabitBtn").forEach(btn => {
    btn.addEventListener("click", () => {

        const deleteHabitId = btn.getAttribute("data-id");
        const deleteHabitName = btn.getAttribute("data-name");
        const deleteHabitCategory = btn.getAttribute("data-category");
        const deleteEmoji = btn.getAttribute("data-emoji");
        const isPaused = btn.getAttribute("data-paused") === 'true';

        document.getElementById("deleteHabitId").value = deleteHabitId;
        document.getElementById("deleteHabitName").textContent = deleteHabitName;
        document.getElementById("deleteHabitCategory").textContent = deleteHabitCategory;
        document.getElementById("deleteEmoji").textContent = deleteEmoji;

        const statusSpan = document.getElementById("deleteHabitStatus");
        statusSpan.textContent = isPaused ? 'Paused' : 'Active';
        statusSpan.className = isPaused
        ? "bg-gray-300 text-gray-900 border border-gray-200 rounded-full px-3 py-1 font-light text-lg"
            : "bg-green-200 text-gray-900 rounded-full px-3 py-1 font-light text-lg";

        deleteModal.classList.remove("hidden");
        deleteModal.classList.add("flex");
    });
});

[closeDeleteModal,cancelDeleteModal].forEach(btn =>
    btn.addEventListener("click", () => {
        deleteModal.classList.add("hidden");
        deleteModal.classList.remove("flex");
    })
);

deleteModal.addEventListener("click", e => {
    if(e.target == deleteModal) deleteModal.classList.add("hidden");
});

//RESUME HABIT BUTTON
const resumeModal = document.getElementById("resumeHabitModal");
const closeResumeModal = document.getElementById("closeResumeModal");
const cancelResumeModal = document.getElementById("cancelResumeModal");

document.querySelectorAll(".resumeHabitBtn").forEach(btn => {
    btn.addEventListener("click", () => {
        const resumeHabitId = btn.getAttribute("data-id");
        const resumeHabitName = btn.getAttribute("data-name");
        const resumeHabitCategory = btn.getAttribute("data-category");
        const resumeEmoji = btn.getAttribute("data-emoji")

        document.getElementById("resumeHabitId").value= resumeHabitId;
        document.getElementById("resumeHabitName").textContent = resumeHabitName;
        document.getElementById("resumeHabitCategory").textContent = resumeHabitCategory;
        document.getElementById("resumeEmoji").textContent = resumeEmoji;

        resumeModal.classList.remove("hidden");
        resumeModal.classList.add("flex");
    });
});

[closeResumeModal,cancelResumeModal].forEach(btn =>
    btn.addEventListener("click", () => {
        resumeModal.classList.add("hidden");
        resumeModal.classList.remove("flex");
    })
);

resumeModal.addEventListener("click", e => {
    if(e.target === resumeModal) resumeModal.classList.add("hidden");
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

document.querySelector("#logOutConfirmBtn").addEventListener("click", async () => {
    await fetch("/logout", { method: "POST" });
    window.location.href = "/login?logout";
});



//EMOJI PICKER FOR ADD AND EDIT BUTTONS
document.addEventListener("DOMContentLoaded", () => {

    // ADD HABIT EMOJI PICKER
    const addPicker = new EmojiMart.Picker({
        onEmojiSelect: emoji => {
            document.getElementById("addEmojiInput").value = emoji.native;
            document.getElementById("addEmojiPicker").classList.add("hidden");
        },
    });

    document.getElementById("addEmojiPicker").appendChild(addPicker);

    const addInput = document.getElementById("addEmojiInput");
    addInput.addEventListener("click", () => {
        document.getElementById("addEmojiPicker").classList.toggle("hidden");
    });


    // EDIT HABIT EMOJI PICKER
    const editPicker = new EmojiMart.Picker({
        onEmojiSelect: emoji => {
            document.getElementById("emojiInput").value = emoji.native;
            document.getElementById("emojiPicker").classList.add("hidden");
        },
    });

    document.getElementById("emojiPicker").appendChild(editPicker);

    const editInput = document.getElementById("emojiInput");
    editInput.addEventListener("click", () => {
        document.getElementById("emojiPicker").classList.toggle("hidden");
    });

});

// Highlight active filter tab
document.addEventListener("DOMContentLoaded", () => {
    const currentFilter = new URLSearchParams(window.location.search).get("filter") || "active";
    const tabs = document.querySelectorAll(".tab-btn");

    tabs.forEach(tab => {
        tab.classList.remove("bg-violet-500", "text-white");
        tab.classList.add("text-cadetGray", "hover:text-amethyst");

        if (tab.id.startsWith(currentFilter)) {
            tab.classList.remove( "text-cadetGray","hover:text-amethyst");
            tab.classList.add("bg-violet-500", "text-white");
        }

        tab.addEventListener("click", () => {
            const filter = tab.id.replace("Tab", "");
            window.location.href = `/dashboard/my-habits?filter=${filter}`;
        });
    });
});

