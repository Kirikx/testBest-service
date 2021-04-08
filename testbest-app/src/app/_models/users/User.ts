import {Role} from "./Role";

export class User {
  id: string = null;
  firstName:	string = null;
  lastName:	string = null;
  email: string = null;
  username: string = null;
  password:	string = null;
  repeatPassword:	string = null;
  phone: string = null;
  roles: Array<Role> = new Array<Role>();
  isDeleted: boolean = false;
}
