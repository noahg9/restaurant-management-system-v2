const menuItemIdInput = document.getElementById("menuItemId");
const toggleChefsButton = document.getElementById("toggleChefInformation");
const chefsTable = document.getElementById("chefInformation");
const buttonWrapper = document.getElementById("dropdownButtonWrapper");
const tableBody = document.getElementById("chefInformationBody");

async function toggleChefsTable() {
    if (chefsTable.style.display === "table") {
        hideChefsTable();
    } else {
        const response = await fetch(`/api/menuitems/${menuItemIdInput.value}/chefs`,
            { headers: { Accept: "application/json" } });
        if (response.status === 200) {
            const chefs = await response.json();
            tableBody.innerHTML = '';
            for (const chef of chefs) {
                tableBody.innerHTML += `
                    <tr>
                        <td>${chef.firstName}</td>
                        <td>${chef.lastName}</td>
                        <td>${chef.dateOfBirth}</td>
                    </tr>
                `;
            }
            showChefsTable();
        }
    }
}

function hideChefsTable() {
    chefsTable.style.display = "none";
    buttonWrapper.classList.remove("dropup");
    if (!buttonWrapper.classList.contains("dropdown")) {
        buttonWrapper.classList.add("dropdown");
    }
}

function showChefsTable() {
    chefsTable.style.display = "table";
    buttonWrapper.classList.remove("dropdown");
    if (!buttonWrapper.classList.contains("dropup")) {
        buttonWrapper.classList.add("dropup");
    }
}

toggleChefsButton.addEventListener("click", toggleChefsTable);



const priceInput = document.getElementById("priceInput");
/**
 * @type {HTMLButtonElement}
 */
const updateButton = document.getElementById("updateButton");

async function changeMenuItem() {
    updateButton.disabled = true;
}

updateButton.addEventListener("click", changeMenuItem);
priceInput.addEventListener("input", () => updateButton.disabled = false);
