import { inject, Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ImageService {
  private readonly _http = inject(HttpClient);

  fetchImg<T>(
    apiUrl: string,
    extractUrl: (data: T) => string,
  ): Observable<string> {
    return this._http.get<T>(apiUrl).pipe(
      map((data) => extractUrl(data)),
      catchError((err) => {
        console.error('Fetching image failed', err);
        return throwError(() => new Error('Image fetch error'));
      }),
    );
  }
}
