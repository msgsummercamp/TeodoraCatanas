import { Component, signal } from '@angular/core';
import { ImageService } from './services/image.service';
import { MatButtonModule } from '@angular/material/button';

type DogApiResponse = { message: string; status: string };
type CatApiResponse = { url: string }[];

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  imports: [MatButtonModule],
})
export class AppComponent {
  public imgUrl = signal<string>('');
  public status = signal<string>('');

  constructor(private imageService: ImageService) {}

  fetchAnimal<T>(apiUrl: string, extract: (data: T) => string) {
    this.status.set('Loading...');
    this.imageService.fetchImg<T>(apiUrl, extract).subscribe({
      next: (url) => {
        this.imgUrl.set(url);
        this.status.set('');
      },
      error: () => this.status.set('Failed to load image.'),
    });
  }

  getDog() {
    this.fetchAnimal<DogApiResponse>(
      'https://dog.ceo/api/breeds/image/random',
      (res) => res.message,
    );
  }

  getCat() {
    this.fetchAnimal<CatApiResponse>(
      'https://api.thecatapi.com/v1/images/search',
      (res) => res[0].url,
    );
  }
}
