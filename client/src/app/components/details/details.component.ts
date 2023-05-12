import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Bundle } from 'src/app/Model';
import { HttpService } from 'src/app/Services/HttpService';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {

  bundleId !: string
  bundle !: Bundle

  constructor(private actRoute: ActivatedRoute, private httpSvc: HttpService){}


  ngOnInit(): void {
    this.bundleId = this.actRoute.snapshot.params["id"]
    this.httpSvc.getBundle(this.bundleId)
                .then(v => v as Bundle).then(b => this.bundle = b)
                .catch(e => {
                  console.info(e['error']['error'])
                })
  }

}
