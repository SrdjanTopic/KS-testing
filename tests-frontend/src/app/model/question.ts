import { IAnswer } from './answer';
import { IConcept, initConcept } from './concept';

export interface IQuestion {
  question: string;
  points: number;
  questionNumber: number;
  answers: IAnswer[];
  concept?: IConcept;
}

export const initQuestion: IQuestion = {
  question: '',
  points: 0,
  questionNumber: 0,
  answers: [],
  concept: initConcept,
};
