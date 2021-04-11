import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {RegisterComponent} from './register/register.component';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {ProfileComponent} from './profile/profile.component';
import {BoardUserComponent} from './board-user/board-user.component';
import {BoardManagerComponent} from './board-manager/board-manager.component';
import {BoardAdminComponent} from './board-admin/board-admin.component';
import {BoardTestComponent} from "./board-test/board-test.component";
import {TestComponent} from "./test/test.component";

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'tests', component: BoardUserComponent},
  {path: 'test/:id/user', component: TestComponent},
  {path: 'manager', component: BoardManagerComponent},
  {path: 'test/new', component: BoardTestComponent},
  {path: 'test/:id', component: BoardTestComponent},
  {path: 'admin', component: BoardAdminComponent},
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: '**', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]


})
export class AppRoutingModule {
}
