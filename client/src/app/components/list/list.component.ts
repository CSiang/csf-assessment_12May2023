import { Component, OnInit } from '@angular/core';
import { Bundle } from 'src/app/Model';
import { HttpService } from 'src/app/Services/HttpService';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  bundles !: Bundle[]

  constructor(private HttpSvc: HttpService){}

  ngOnInit(): void {
     this.HttpSvc.getBundleList().then(v => {this.bundles = v
                                              console.info(this.bundles)}
                                              )
  }




}
