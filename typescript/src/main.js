"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
const img = document.getElementById("animalImage");
const statusMsg = document.getElementById("statusMessage");
const showDogButton = document.getElementById("showDogBtn");
const showCatButton = document.getElementById("showCatBtn");
function getDogImage() {
    return __awaiter(this, void 0, void 0, function* () {
        yield fetchAndShowImage('https://dog.ceo/api/breeds/image/random', (res) => res.message);
    });
}
function getCatImage() {
    return __awaiter(this, void 0, void 0, function* () {
        yield fetchAndShowImage('https://api.thecatapi.com/v1/images/search', (res) => res[0].url);
    });
}
function fetchAndShowImage(apiUrl, extractImageUrl) {
    return __awaiter(this, void 0, void 0, function* () {
        statusMsg.textContent = 'Loading...';
        img.style.display = 'none';
        try {
            const response = yield fetch(apiUrl);
            if (!response.ok)
                throw new Error('Network error');
            const data = yield response.json();
            img.src = extractImageUrl(data);
            img.style.display = 'block';
            statusMsg.textContent = '';
        }
        catch (err) {
            console.error('Failed to fetch image:', err);
            statusMsg.textContent = '🐾 Failed to load image. Try again!';
        }
    });
}
document.addEventListener('DOMContentLoaded', () => {
    showDogButton === null || showDogButton === void 0 ? void 0 : showDogButton.addEventListener('click', getDogImage);
    showCatButton === null || showCatButton === void 0 ? void 0 : showCatButton.addEventListener('click', getCatImage);
});
