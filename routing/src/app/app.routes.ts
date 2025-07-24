import { Routes } from '@angular/router';
import { NotFoundComponent } from './notFound_component/not-found.component/not-found.component.component';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  {
    path: 'login',
    loadChildren: () => import('./login/login.module').then((m) => m.LoginModule),
  },
  { path: '**', component: NotFoundComponent },
];
