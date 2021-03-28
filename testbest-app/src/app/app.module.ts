import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {HomeComponent} from './home/home.component';
import {ProfileComponent} from './profile/profile.component';
import {BoardAdminComponent} from './board-admin/board-admin.component';
import {BoardManagerComponent} from './board-manager/board-manager.component';
import {BoardUserComponent} from './board-user/board-user.component';

import {authInterceptorProviders} from './_helpers/auth.interceptor';
import {ModalOutletComponent} from './_modals/modal-outlet/modal-outlet.component';
import {PortalToDirective} from './_modals/portal/portal.directive';
import {ModalComponent} from './_modals/modal/modal.component';
import {CommonModule} from "@angular/common";
import {PortalModule} from "./_modals/portal/portal.module";
import {BoardTestComponent} from './board-test/board-test.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardManagerComponent,
    BoardUserComponent,
    ModalOutletComponent,
    PortalToDirective,
    ModalComponent,
    BoardTestComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    CommonModule,
    PortalModule,
    ReactiveFormsModule
  ],
  exports: [
    ModalOutletComponent,
    ModalComponent
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
