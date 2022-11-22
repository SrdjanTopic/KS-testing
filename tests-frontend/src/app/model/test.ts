import { IQuestion } from './question';
export interface ITest {
  name: string;
  questions: IQuestion[];
}

export const initTest: ITest = {
  name: '',
  questions: [],
};
