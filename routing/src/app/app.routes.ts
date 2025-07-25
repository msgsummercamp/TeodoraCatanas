import { Routes } from '@angular/router';
import { NotFoundComponent } from './notFound_component/not-found.component/not-found.component.component';
import { HomeComponent } from './home/home.component';
import { authGuard } from './guards/auth.guard';
import { confirmExitGuard } from './guards/confirm-exit.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [authGuard] },
  {
    path: 'login',
    loadChildren: () => import('./login/login.module').then((m) => m.LoginModule),
  },
  { path: '**', component: NotFoundComponent, canDeactivate: [confirmExitGuard] },
];
