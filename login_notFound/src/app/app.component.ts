import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { IfAdminDirective } from './directives/if-admin.directive';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, IfAdminDirective],
  templateUrl: './app.component.html',
})
export class AppComponent {
  public readonly isAdmin = true; // Just for testing
}
