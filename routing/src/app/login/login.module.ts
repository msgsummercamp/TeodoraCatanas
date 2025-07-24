import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './login.component';

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild([{ path: '', component: LoginComponent }]), LoginComponent],
})
export class LoginModule {}
