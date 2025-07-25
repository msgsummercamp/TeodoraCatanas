import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthService {
  isAuthenticated = signal(false);

  login() {
    this.isAuthenticated.set(true);
  }

  logout() {
    this.isAuthenticated.set(false);
  }

  getToken(): string {
    return 'this-is-a-very-good-token-I-promise-:)';
  }
}
