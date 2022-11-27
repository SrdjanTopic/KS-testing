import { IUser, initUser } from './user';
import { IQuestion } from './question';
export interface ITest {
  name: string;
  questions: IQuestion[];
  teacher: IUser;
}

export const initTest: ITest = {
  name: '',
  questions: [],
  teacher: initUser
};
