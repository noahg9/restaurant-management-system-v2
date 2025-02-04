import {header, token} from './util/csrf.js'

const menuItemId = document.getElementById('menuItemId')
const toggleChefsButton = document.getElementById('toggleChefInformation')
const associatedChefs = document.getElementById('associatedChefs')
const name = document.getElementById('nameField')
const price = document.getElementById('priceField')
const vegetarian = document.getElementById('vegetarianField')
const spiceLevel = document.getElementById('spiceLevelField')
const saveButton = document.getElementById('saveButton')

toggleChefsButton?.addEventListener('click', toggleChefs)
saveButton?.addEventListener('click', saveMenuItem)
name?.addEventListener('input', () => saveButton.disabled = false)

async function toggleChefs() {
    if (associatedChefs.innerHTML !== '') {
        associatedChefs.innerHTML = ''
    } else {
        const response = await fetch(`/api/menu-items/${menuItemId.value}/chefs`, {
            headers: {
                'Accept': 'application/json', [header]: token
            }
        })
        if (response.status === 200) {
            const chefs = await response.json()
            chefs.forEach(chef => {
                const chefLink = document.createElement('a')
                chefLink.href = `/chef?id=${chef.id}`
                chefLink.classList.add('list-group-item', 'list-group-item-action')
                chefLink.textContent = chef.firstName + ' ' + chef.lastName
                associatedChefs.appendChild(chefLink)
            })
        }
    }
}

async function saveMenuItem() {
    const response = await fetch(`/api/menu-items/${menuItemId.value}`, {
        method: 'PATCH', headers: {
            'Content-Type': 'application/json', [header]: token
        }, body: JSON.stringify({
            name: name.value, price: price.value, vegetarian: vegetarian.checked, spiceLevel: spiceLevel.value
        }), redirect: 'manual'
    })
    if (response.status === 204) {
        saveButton.disabled = true
    }
}
