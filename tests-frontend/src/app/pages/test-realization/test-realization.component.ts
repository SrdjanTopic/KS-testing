import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { initTest, ITest } from 'src/app/model/test';
import { TestService } from 'src/app/services/test.service';

@Component({
  selector: 'app-test-realization',
  templateUrl: './test-realization.component.html',
  styleUrls: ['./test-realization.component.css']
})
export class TestRealizationComponent implements OnInit {

  test: ITest = initTest;
  id!: number;

  constructor(private testService: TestService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = +this.route.snapshot.paramMap.get('id')!;
    this.testService.getTest(this.id).subscribe(test => this.test = test);
  }


}
