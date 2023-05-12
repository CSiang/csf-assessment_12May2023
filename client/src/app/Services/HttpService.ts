import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { FormGroup } from "@angular/forms";
import {lastValueFrom} from "rxjs"
import { Bundle } from "../Model";

@Injectable()
export class HttpService{

    constructor(private http: HttpClient){}

    upload(form: FormGroup, file: Blob ){

        // const header = new HttpHeaders()
        //             .set('content-type','multipart/form-data')
        //             .set('Accept', 'application/json')

        const formData = new FormData()
        formData.set("name",form.value['name'] )
        formData.set("title",form.value['title'] )
        formData.set("comment",form.value['comment'] )
        formData.append("file", file)

        return lastValueFrom(this.http.post("/upload", formData))
    }

    getBundle(id: string){

        const url = 'bundle/' + id

        return lastValueFrom(this.http.get(url))
    }

    getBundleList() {

        return lastValueFrom(this.http.get('/bundles'))
                .then(l => l as any[])
                .then(i => i.map(j => j as Bundle ) )
    }
    // job.add("bundleId", doc.getString("bundleId"))
    // .add("date", doc.getString("date"))
    // .add("title", doc.getString("title"));



}