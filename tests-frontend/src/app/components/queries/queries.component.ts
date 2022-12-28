import { ConditionalExpr } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { initUser, IUser } from 'src/app/model/user';
import { ConceptService } from 'src/app/services/concept.service';
import { SparqlService } from 'src/app/services/sparql.service';
import { StudentService } from 'src/app/services/student.service';
import { UserService } from 'src/app/services/user.service';

interface Query {
  name: string;
  id: number;
}

interface ProfessionCriteria{
  conceptNames:any;
  profession:string
}

@Component({
  selector: 'app-queries',
  templateUrl: './queries.component.html',
  styleUrls: ['./queries.component.css']
})

export class QueriesComponent implements OnInit {
queries:Query[]=[
  {name:'Q1: Find all required concepts for learning specific concept',id:1},
  {name:'Q2: Find all concepts for learning after knowing a specific concept',id:2},
  {name:'Q3: Find all tests to solve after learning a specific concept',id:3},
  {name:'Q4: Find all students that are competent for team based on concepts',id:4},
  {name:'Q5: Find all concepts that need to be known to meet  requirements for a specific profession',id:5},
]
  user:any;
  selectedQueryId:number=0;
  allConcepts:any=[];
  allConceptsByProfession:any=[];
  allProfessions:any=[];
  selectedConcept:any='';
  selectedConcepts:any[]=[];
  selectedProfession:any;
  results:any=[];
  showResult:boolean=false;
  professionCriteria:ProfessionCriteria={
    conceptNames:[],
    profession:''

  }

  constructor(
    private sparqlService:SparqlService,
    private conceptService:ConceptService,
    private userService:UserService,
    private studentService:StudentService
    ) { }

  ngOnInit(): void {
    this.getAllProfessions();
    this.getAllConcepts();
  }

  getAllProfessions(){
    this.conceptService.getProfessions().subscribe(data=>{
      this.allProfessions=data;
      console.log(this.allProfessions)
    })
  }

  getAllConcepts(){
    this.conceptService.getConcepts().subscribe(data=>{
      this.allConcepts=data;
      console.log(this.allProfessions)
    })
  }

  getAllConceptsByProfession(professionId:Number){
    this.conceptService.getConceptByProfession(professionId).subscribe(data=>{
      this.allConceptsByProfession=data;
      console.log(this.allConceptsByProfession)
    })
  }

  selectConcept(conceptName:String){
    this.selectedConcept=conceptName;

  }

  showQuery(queryId:number){
    this.results=[];
    this.showResult=false;
    this.selectedQueryId=queryId;
   
  }

  runQ1(){
    this.sparqlService.getPreviousConcepts(this.selectedConcept).subscribe(data=>{
      console.log(data);
      this.results=data;
      this.showResult=true;
    },error=>{
      alert('Error')
    })
  }

  runQ2(){
    this.sparqlService.getDirectNextConcepts(this.selectedConcept).subscribe(data=>{
      console.log(data);
      this.results=data;
      this.showResult=true;
    },error=>{
      alert('Error')
    })
  }

  runQ3(){
    this.sparqlService.getSolvableTests(this.selectedConcept).subscribe(data=>{
      console.log(data);
      this.results=data;
      this.showResult=true;
    },error=>{
      alert('Error')
    })
  }

  runQ4(){
    this.sparqlService.getStudentByConcepts(this.selectedConcepts).subscribe(data=>{
      this.results=data;
      console.log(this.results);
      this.showResult=true;
    },error=>{
      alert('Error')
    })
  }

  runQ5(){
    this.userService.getCurrentUser().subscribe(data=>{
      this.user=data;
      this.professionCriteria.profession=this.selectedProfession;
      this.studentService.getLearnedConceptsForStudent(this.user.id).subscribe(data=>{
        this.professionCriteria.conceptNames=data;
        this.sparqlService.getConceptsByProfessionAndSkills(this.professionCriteria).subscribe(data=>{
          this.results=data;
          console.log(this.results);
          this.showResult=true;
        },error=>{
          alert('Error')
        })
      })
      console.log(this.professionCriteria);
     
    
    }
    );
   
  }
}
