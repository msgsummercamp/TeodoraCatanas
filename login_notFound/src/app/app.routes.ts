import { Routes } from '@angular/router';
import { NotFoundComponent } from './notFound_component/notFound.component';

export const routes: Routes = [{ path: '**', component: NotFoundComponent }];
