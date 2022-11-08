import { IQuestion } from './question';
export interface ITest {
  id: number;
  name: string;
  questions: IQuestion[];
}

export const initTest: ITest = {
  id: 0,
  name: '',
  questions: [],
};
