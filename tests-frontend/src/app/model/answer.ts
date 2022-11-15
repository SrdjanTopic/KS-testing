export interface IAnswer{
    id:number;
    answer:string;
    isCorrect:boolean;
}

export const initAnswer : IAnswer = {
    id:0,
    answer:'',
    isCorrect:false
}