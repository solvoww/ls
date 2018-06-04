import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Component } from '@angular/core';

import { AppComponent } from './app.component';
import { RemoveDialogComponent } from './dialogs/remove-dialog.component';
import { RenameDialogComponent } from './dialogs/rename-dialog.component';
import { FileListComponent } from './file-list/file-list.component';
import { FileService } from './file/file.service';

import { MatButtonModule, MatSnackBarModule, MatToolbarModule } from '@angular/material';
import { MatIconModule, MatTableModule, MatSortModule, MatDialogModule } from '@angular/material';
import { MatInputModule } from '@angular/material';
import { FormsModule } from '@angular/forms';

import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    FileListComponent,
    RenameDialogComponent,
    RemoveDialogComponent
  ],
  entryComponents: [
    RenameDialogComponent,
    RemoveDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    MatButtonModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatIconModule,
    MatTableModule,
    MatSortModule,
    MatDialogModule,
    MatInputModule,
    RouterModule.forRoot([{ path: '', component: AppComponent}])
  ],
  providers: [
    FileService
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
