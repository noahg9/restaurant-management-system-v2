import anime from 'animejs'
import {header, token} from './util/csrf.js'

const chefBody = document.getElementById('chefBody')
const deleteButtons = document.querySelectorAll('button.btn-danger')

for (const deleteButton of deleteButtons) {
    deleteButton?.addEventListener('click', handleDeleteChef)
}

fillChefsTable().catch(error => {
    console.error('Error fetching chefs:', error)
})

async function fillChefsTable() {
    try {
        const response = await fetch('/api/chefs', {
            headers: {
                'Accept': 'application/json',
                [header]: token
            }
        })
        if (response.status === 200) {
            const chefs = await response.json()
            chefs.forEach(chef => {
                addChefToTable(chef)
            })
            anime({
                targets: '.card',
                opacity: [0, 1],
                easing: 'linear',
                duration: 600,
                delay: anime.stagger(100)
            })
        } else {
            console.error('Failed to fetch chefs:', response.statusText)
        }
    } catch (error) {
        console.error('Error fetching chefs:', error)
    }
}

async function handleDeleteChef(event) {
    const card = event.target.closest('.card')
    const chefId = card.dataset.chefId
    const response = await fetch(`/api/chefs/${chefId}`, {
        method: 'DELETE', headers: {
            [header]: token
        }
    })
    if (response.status === 204) {
        anime({
            targets: card, opacity: 0.0, easing: 'linear', duration: 600, complete: function () {
                card.remove()
            }
        })
    }
}

/**
 * @param {{id: string, firstName: string, lastName: string, dateOfBirth: string, username: string, password: string, role, string}} chef
 */
function addChefToTable(chef) {
    const roleNames = {
        'HEAD_CHEF': 'Head Chef',
        'SOUS_CHEF': 'Sous Chef',
        'LINE_COOK': 'Line Cook'
    }

    const roleName = roleNames[chef.role]

    const roleGroup = document.getElementById(chef.role + '-group') // Check if group exists
    let cardGroup
    if (!roleGroup) {
        // Create a new group if it doesn't exist
        cardGroup = document.createElement('div')
        cardGroup.classList.add('role-group', 'mb-4', 'row', 'text-secondary') // Add Bootstrap classes for rows
        cardGroup.id = chef.role + '-group'

        const groupName = document.createElement('h2')
        groupName.textContent = roleName // Use role name as group header
        cardGroup.appendChild(groupName)

        chefBody.appendChild(cardGroup)
    } else {
        cardGroup = roleGroup
    }

    const cardColumn = document.createElement('div')
    cardColumn.classList.add('col-md-6')
    const card = document.createElement('div')
    card.classList.add('card', 'mb-3', 'card-hidden') // Add 'card-hidden' class
    const age = Math.floor((new Date() - new Date(chef.dateOfBirth)) / (1000 * 60 * 60 * 24 * 365))
    card.innerHTML = `
        <div class="card-body" onclick="location.href='/chef?id=${chef.id}';" style="cursor: pointer;">
            <h5 class="card-title">${chef.firstName + ' ' + chef.lastName}</h5>
            <p class="card-text">${age} years old</p>
            <button type="button" class="btn btn-danger btn-sm delete-button"><i class="bi bi-trash3"></i></button>
        </div>
    `
    card.dataset.chefId = chef.id
    cardColumn.appendChild(card)
    cardGroup.appendChild(cardColumn)

    const newDeleteButton = card.querySelector('button')
    newDeleteButton?.addEventListener('click', (event) => {
        event.stopPropagation()
        handleDeleteChef(event).catch(error => {
            console.error('Error deleting chef:', error)
        })
    })
}
