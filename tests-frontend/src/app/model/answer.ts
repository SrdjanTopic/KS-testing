export interface IAnswer {
  id: number | null;
  answer: string;
  isCorrect: boolean;
}

export const initAnswer: IAnswer = {
  id: null,
  answer: '',
  isCorrect: false,
};
