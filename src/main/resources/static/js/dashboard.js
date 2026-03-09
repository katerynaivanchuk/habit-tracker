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


//EMOJI PICKER FOR ADD BUTTON MODAL WINDOW
document.addEventListener("DOMContentLoaded", () => {
    const picker = new EmojiMart.Picker({
        onEmojiSelect: emoji => {
            document.getElementById("emojiInput").value = emoji.native;
            document.getElementById("emojiPicker").classList.add("hidden");
        },
    });

    document.getElementById("emojiPicker").appendChild(picker);
    const input = document.getElementById("emojiInput");

    input.addEventListener("click", () => {
        document.getElementById("emojiPicker").classList.toggle("hidden");
    });
});