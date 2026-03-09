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

//CHANGE PASSWORD BUTTON
const changePasswordModal = document.getElementById("changePasswordModal");
const closeChangePasswordModal = document.getElementById("closeChangePasswordModal");
const cancelChangePasswordModal = document.getElementById("cancelChangePasswordModal");

document.querySelectorAll(".changePasswordBtn").forEach(btn => {
    btn.addEventListener("click", () => {
        changePasswordModal.classList.remove("hidden");
        changePasswordModal.classList.add("flex");
    });
});

[closeChangePasswordModal,cancelChangePasswordModal].forEach(btn =>
    btn.addEventListener("click", () => {
        changePasswordModal.classList.add("hidden");
        changePasswordModal.classList.remove("flex");
    })
);

changePasswordModal.addEventListener("click", e => {
    if(e.target == changePasswordModal) changePasswordModal.classList.add("hidden");
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
    if(e.target == logOutModal) logOutModal.classList.add("hidden");
});

document.querySelector("#logOutConfirmBtn").addEventListener("click", async () => {
    await fetch("/logout", { method: "POST" });
    window.location.href = "/login?logout";
});

//UPLOAD PHOTO
const profilePhoto = document.getElementById("profilePhoto");
const imageInput = document.getElementById("imageInput");
const uploadForm = document.getElementById("uploadForm");

// click → open file chooser
profilePhoto.addEventListener("click", () => imageInput.click());

// file chosen → submit automatically
imageInput.addEventListener("input", () => {
    if (imageInput.files.length > 0) {
        uploadForm.submit();
    }
});



//
const form = document.getElementById("changePasswordForm");
const errorBox = document.getElementById("passwordErrorText");
const successBox = document.getElementById("passwordSuccessText");

form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const formData = new FormData(form);

    const response = await fetch("/profile/change-password", {
        method: "POST",
        body: formData
    });

    const text = await response.text();

    if (response.status === 400) {
        successBox.classList.add("hidden");
        errorBox.textContent = text;
        errorBox.classList.remove("hidden");
    } else if (response.status === 200) {
        errorBox.classList.add("hidden");
        successBox.textContent = "Password updated successfully!";
        successBox.classList.remove("hidden");

        // Автоматичне закриття модалки через 1 секунду
        setTimeout(() => {
            changePasswordModal.classList.add("hidden");
            changePasswordModal.classList.remove("flex");
            form.reset();
        }, 1000);
    }
});
