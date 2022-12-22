import { Routes } from '@angular/router'
import {LoginComponent} from "./login/login.component";
import {AuthGuard} from "./auth.guard";
import {PassDisplayComponent} from "./pass-display/pass-display.component";
import {AdminComponent} from "./admin/admin.component";
import {RegisterComponent} from "./register/register.component";
export const appRoutes:Routes = [

  {path: 'logon', component: LoginComponent},

  {path: 'register', component: RegisterComponent},

  {path: '', component: PassDisplayComponent,
  data: {authorities: ['ROLE_USER', 'ROLE_ADMIN']
  }, canActivate: [AuthGuard]},

  {path: 'admin', component: AdminComponent,
    data: {authorities: ['ROLE_ADMIN']
    }, canActivate: [AuthGuard]}

]
