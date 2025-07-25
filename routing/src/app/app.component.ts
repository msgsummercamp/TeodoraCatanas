import { Component, inject, signal } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink, RouterOutlet } from '@angular/router';
import { ImageService } from './services/image.service';
import { IfAdminDirective } from './directives/if-admin.directive';

type DogApiResponse = { message: string; status: string };
type CatApiResponse = { url: string }[];

@Component({
  selector: 'app-root',
  imports: [
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    RouterLink,
    RouterOutlet,
    IfAdminDirective,
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  public readonly imgUrl = signal<string>('');
  public readonly status = signal<string>('');

  private readonly imageService = inject(ImageService);

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
      (res) => res.message
    );
  }

  getCat() {
    this.fetchAnimal<CatApiResponse>(
      'https://api.thecatapi.com/v1/images/search',
      (res) => res[0].url
    );
  }
}
