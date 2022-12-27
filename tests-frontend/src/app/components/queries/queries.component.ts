import { ConditionalExpr } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ConceptService } from 'src/app/services/concept.service';
import { SparqlService } from 'src/app/services/sparql.service';
interface Food {
  value: string;
  viewValue: string;
}


@Component({
  selector: 'app-queries',
  templateUrl: './queries.component.html',
  styleUrls: ['./queries.component.css']
})

export class QueriesComponent implements OnInit {
  foods: Food[] = [
    {value: 'steak-0', viewValue: 'Steak'},
    {value: 'pizza-1', viewValue: 'Pizza'},
    {value: 'tacos-2', viewValue: 'Tacos'},
  ];
  allConceptsByProfession:any=[];
  allProfessions:any=[];
  selectedConcept:any='';
  results:any=[];

  constructor(
    private sparqlService:SparqlService,
    private conceptService:ConceptService
    ) { }

  ngOnInit(): void {
    this.getAllProfessions();
  }

  getAllProfessions(){
    this.conceptService.getProfessions().subscribe(data=>{
      this.allProfessions=data;
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

  runQ1(){
    this.sparqlService.getDirectNextConcepts(this.selectedConcept).subscribe(data=>{
      console.log(data);
      this.results=data;
    },error=>{
      alert('Error')
    })
  }

}
