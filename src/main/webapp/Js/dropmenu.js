const userProfile = document.querySelector('.user-profile');
const dropdownMenu = document.querySelector('.dropdown-menu');
const closeButton = document.querySelector('.close-button');

userProfile.addEventListener('click', (event) => {
    event.stopPropagation();
    dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
});

document.addEventListener('click', (event) => {
    const targetElement = event.target;
    if (!dropdownMenu.contains(targetElement) && targetElement !== userProfile.querySelector('img')) {
        dropdownMenu.style.display = 'none';
    }
});

closeButton.addEventListener('click', () => {
    dropdownMenu.style.display = 'none';
});