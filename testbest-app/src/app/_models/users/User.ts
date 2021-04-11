import {Role} from "./Role";

export class User {
  id: string = null;
  firstName:	string = null;
  lastName:	string = null;
  email: string = null;
  username: string = null;
  password:	string = null;
  repeatPassword:	string = null;
  roles: Array<Role> = new Array<Role>();
  isDeleted: boolean = false;

  cloneUser(user: User) {
    this.id = user.id;
    this.firstName = user.firstName;
    this.lastName = user.lastName;
    this.email = user.email;
    this.username = user.username;
    this.password = user.password;
    this.repeatPassword = user.repeatPassword;
    this.roles = user.roles;
    this.isDeleted = user.isDeleted;
  }
}
