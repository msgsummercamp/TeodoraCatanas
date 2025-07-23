type DogApiResponse = {
    message: string;
    status: string;
}
type CatApiResponse = {
    url: string
}

async function fetchImg<T>(apiUrl: string, extractUrl: (data: T) => string): Promise<void> {
    const img = document.getElementById('animalImage') as HTMLImageElement;
    const status = document.getElementById('statusMessage') as HTMLSpanElement;
    status.textContent = "Loading...";
    img.style.display = 'none';
    try{
        const response = await fetch(apiUrl);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data: T = await response.json();
        img.src = extractUrl(data);
        img.style.display = 'block';
        status.textContent = '';
    }catch (error) {
        console.error('Failed to fetch image:', error);
        status.textContent = '🐾 Failed to load image. Try again!';
    }
}

async function getDogImage(): Promise<void> {
    await fetchImg<DogApiResponse>('https://dog.ceo/api/breeds/image/random', res => res.message);
}

async function getCatImage(): Promise<void>{
    await fetchImg<CatApiResponse[]>('https://api.thecatapi.com/v1/images/search', res => res[0].url)
}

document.addEventListener('DOMContentLoaded', () => {
    const dogButton = document.getElementById('showDogBtn') as HTMLButtonElement | null;
    const catButton = document.getElementById('showCatBtn') as HTMLButtonElement | null;
    if(dogButton && catButton) {
        dogButton.addEventListener('click', getDogImage);
        catButton.addEventListener('click', getCatImage);
    }
});

