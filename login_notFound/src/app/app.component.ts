import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { IfAdminDirective } from './directives/if-admin.directive';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, IfAdminDirective],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  public isAdmin = true; // Just for testing
}
