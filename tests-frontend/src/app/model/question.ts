import { IAnswer } from './answer';

export interface IQuestion {
  id: number;
  question: string;
  points: number;
  questionNumber: number;
  answers: IAnswer[];
}

export const initQuestion: IQuestion = {
  id: 0,
  question: '',
  points: 0,
  questionNumber: 0,
  answers: [],
};
