export interface ISubmitTestAnswers {
  studentId: number | null;
  answers: {
    id: number | null;
    answer: string;
    isCorrect: boolean;
    questionId: number | null;
  }[];
}

export const initSubmitTestAnswers: ISubmitTestAnswers = {
  studentId: null,
  answers: [],
};
