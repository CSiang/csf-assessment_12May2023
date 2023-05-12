import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpService } from 'src/app/Services/HttpService';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit{

file !: any
form !: FormGroup

constructor(private fb: FormBuilder, private httpSvc: HttpService){}

ngOnInit(): void {
  this.form = this.createForm()
}

createForm(){
  return this.fb.group({
    name: this.fb.control<string>('',Validators.required),
    title: this.fb.control<string>('',Validators.required),
      comment: this.fb.control<string>(''),
    })
  }

  getFile(event:any){
    console.info(event.target.files[0]) 
    this.file = event.target.files[0]
  }

  disable(){
    return this.form.invalid || this.file == undefined
  }

  submit(){
    console.log('>>>submit is activated')
    console.info(this.file)

    console.log("name: ", this.form.value['name'])
    console.log("title: ", this.form.value['title'])
    console.log("comment: ", this.form.value['comment'])
    console.log("file: ", this.file)

    // this.uploadSvc.upload(this.form, this.fileUpload.nativeElement.files[0])
    // .then( res => this.postIds.push(res)
    // )  
    this.httpSvc.upload(this.form,this.file)
    this.form.reset()
  }
  
}
