type DogApiResponse = {
    message: string;
    status: string;
};
type CatApiResponse = {
    url: string;
};

const img = document.getElementById("animalImage") as HTMLImageElement;
const statusMsg = document.getElementById("statusMessage") as HTMLParagraphElement;
const showDogButton:HTMLButtonElement|null = document.getElementById("showDogBtn") as HTMLButtonElement;
const showCatButton:HTMLButtonElement|null = document.getElementById("showCatBtn") as HTMLButtonElement;

async function getDogImage(): Promise<void> {
    await fetchAndShowImage<DogApiResponse>(
        'https://dog.ceo/api/breeds/image/random',
        (res) => res.message
    );
}

async function getCatImage(): Promise<void> {
    await fetchAndShowImage<CatApiResponse[]>(
        'https://api.thecatapi.com/v1/images/search',
        (res) => res[0].url
    );
}

async function fetchAndShowImage<T>(
    apiUrl: string,
    extractImageUrl: (res: T) => string
): Promise<void> {
    statusMsg.textContent = 'Loading...';
    img.style.display = 'none';

    try {
        const response: Response = await fetch(apiUrl);
        if (!response.ok) throw new Error('Network error');

        const data: T = await response.json();
        img.src = extractImageUrl(data);
        img.style.display = 'block';
        statusMsg.textContent = '';
    } catch (err) {
        console.error('Failed to fetch image:', err);
        statusMsg.textContent = '🐾 Failed to load image. Try again!';
    }
}

document.addEventListener('DOMContentLoaded', () => {
    showDogButton?.addEventListener('click', getDogImage);
    showCatButton?.addEventListener('click', getCatImage);
});