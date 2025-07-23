async function getDogImage() {
    await fetchAndShowImage('https://dog.ceo/api/breeds/image/random', res => res.message);
}

async function getCatImage() {
    await fetchAndShowImage('https://api.thecatapi.com/v1/images/search', res => res[0].url);
}

async function fetchAndShowImage(apiUrl, extractImageUrl) {
    const img = document.getElementById('animalImage');
    const status = document.getElementById('statusMessage');
    status.textContent = 'Loading...';
    img.style.display = 'none';
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        img.src = extractImageUrl(data);
        img.style.display = 'block';
        status.textContent = '';
    } catch (error) {
        console.error('Failed to fetch image:', error);
        status.textContent = 'Failed to load image. Try again!';
    }
}

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('showDogBtn').addEventListener('click', getDogImage);
    document.getElementById('showCatBtn').addEventListener('click', getCatImage);
});