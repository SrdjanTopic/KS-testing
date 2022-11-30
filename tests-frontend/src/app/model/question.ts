import { IAnswer } from './answer';
import { IConcept, initConcept } from './concept';

export interface IQuestion {
  id: number | null;
  question: string;
  points: number;
  questionNumber: number;
  answers: IAnswer[];
  concept?: IConcept;
}

export const initQuestion: IQuestion = {
  id: null,
  question: '',
  points: 0,
  questionNumber: 0,
  answers: [],
  concept: initConcept,
};
