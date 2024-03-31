const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');

const firstName = document.getElementById("firstName");
const lastName = document.getElementById("lastName");
const dateOfBirth = document.getElementById("dateOfBirth");
const username = document.getElementById("username");
const password = document.getElementById("password");
const role = document.getElementById("role");
const registerButton = document.getElementById("registerButton");

async function addNewChef() {
    await fetch(`/api/chefs`, {
        method: "POST", headers: {
            "Accept": "application/json", "Content-Type": "application/json", [header]: token
        }, body: JSON.stringify({
            firstName: firstName.value,
            lastName: lastName.value,
            dateOfBirth: dateOfBirth.value,
            username: username.value,
            password: password.value,
            role: role.value
        })
    });
}

registerButton?.addEventListener("click", addNewChef);