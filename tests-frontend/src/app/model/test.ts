import { IUser, initUser } from './user';
import { IQuestion } from './question';
export interface ITest {
  id: number | null;
  name: string;
  questions: IQuestion[];
  teacher: IUser;
}

export const initTest: ITest = {
  id: null,
  name: '',
  questions: [],
  teacher: initUser,
};
