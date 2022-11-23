import { IUser, initUser } from './user';
import { IQuestion } from './question';
export interface ITest {
  name: string;
  questions: IQuestion[];
  student: any;
  teacher: IUser;
}

export const initTest: ITest = {
  name: '',
  questions: [],
  student: null,
  teacher: initUser
};
