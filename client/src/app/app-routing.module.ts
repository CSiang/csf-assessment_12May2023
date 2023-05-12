import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UploadComponent } from './components/upload/upload.component';
import { DetailsComponent } from './components/details/details.component';

const routes: Routes = [
  {path:"", component:UploadComponent},
  {path:"file", component:UploadComponent},
  {path:"details/:id", component:DetailsComponent},
  {path:'**', redirectTo:'', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
