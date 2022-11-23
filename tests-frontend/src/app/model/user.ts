export interface IUser {
  id: number;
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  roles: IRole[];
  deleted: boolean;
}

export interface IDirective {
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  roles: IRole[];
  activeRoles: Array<string>;
}

export interface IUserLogin {
  username: string;
  password: string;
}

export interface IToken {
  accessToken: string;
  expiresIn: number;
}

export interface IRole {
  id: number;
  authority: string;
}

export const initUser : IUser = {
  id: 0,
  username: '',
  password: '',
  firstName: '',
  lastName: '',
  email: '',
  phoneNumber: '',
  roles: [],
  deleted: false
}
