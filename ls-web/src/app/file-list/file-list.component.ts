import { RemoveDialogComponent } from '../dialogs/remove-dialog.component';
import { RenameDialogComponent } from '../dialogs/rename-dialog.component';
import { Component, OnInit } from '@angular/core';
import { LsPath } from '../model/lspath';
import { LsFile } from '../model/lsfile';
import { FileService } from '../file/file.service';
import { Router } from '@angular/router';

import {ViewChild} from '@angular/core';
import {MatSort, MatTableDataSource, Sort} from '@angular/material';
import {MatDialog, MAT_DIALOG_DATA, MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition} from '@angular/material';

import * as FileSaver from 'file-saver';

@Component({
  selector: 'app-file-list',
  templateUrl: './file-list.component.html',
  styleUrls: ['./file-list.component.css']
})

export class FileListComponent implements OnInit {

  currentPath: LsPath;

  files: LsFile[];

  displayedColumns = ['type', 'name', 'modDate', 'size', 'attr', 'actions'];

  constructor(private fileService: FileService, private router: Router, public dialog: MatDialog, public snackBar: MatSnackBar) { }

  ngOnInit() {

    this.fileService.getCurrentPath().subscribe(data => {
      this.currentPath = data;
    });

    this.fileService.getAll().subscribe(data => {
      this.files = data;
    });

  }

 sortData(sort: Sort) {
    const data = this.files.slice();
    if (!sort.active || sort.direction === '') {
      this.files = data;
      return;
    }

    this.files = data.sort((a, b) => {
      let isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'name': return compare(a.name, b.name, isAsc);
        case 'type': return compare(a.type, b.type, isAsc);
        case 'modDate': return compare(a.modDate, b.modDate, isAsc);
        case 'size': return compare(+a.size, +b.size, isAsc);
        case 'attr': return compare(+a.attr, +b.attr, isAsc);
        default: return 0;
      }
    });
  }

  goClick(canonicalPath) {
    console.log('Ala ma kota i psa ' + canonicalPath);
  }

  goToFolder(canonicalPath) {
    console.log('goToFolder ' + canonicalPath);

    this.fileService.setPath(canonicalPath).subscribe(data => {
    });

    this.fileService.getCurrentPath().subscribe(data => {
      this.currentPath = data;
    });

    this.fileService.getByPath().subscribe(data => {
      this.files = data;
    });

  }

  goToFile(fileName) {
    console.log('goToFile ' + fileName);

    this.fileService.viewFile(fileName).subscribe(data => {
//      let blob = new Blob([data], {
//              type: 'application/octet-stream'
//          });

      FileSaver.saveAs(data, fileName);

//      window.URL.createObjectURL(data);
//      var link = document.createElement('a');
//      link.href = data;
//      link.download = fileName;
//      document.body.appendChild(link);
//      link.setAttribute('style', 'display: none');
//      link.click();
//      setTimeout(function(){
//        window.URL.revokeObjectURL(data) , 100}
//      );
//      link.remove();
//


    });
  }

  rename(fileName) {
    console.log('rename ' + fileName);

    let dialogRef = this.dialog.open(RenameDialogComponent, {
      data: {
        nameToModify: fileName
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);

      if ((result) && (result !== fileName)) {

        this.fileService.rename(fileName, result).subscribe(data => {

          this.fileService.getByPath().subscribe(data => {
            this.files = data;
          });

        });
      }

    });

 }

  remove(fileName) {
    console.log('remove ' + fileName);

    let dialogRef = this.dialog.open(RemoveDialogComponent, {
      data: {
        nameToRemove: fileName
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog result: ${result}');

      if ((result) && (result === fileName)) {

        this.fileService.remove(result).subscribe(response => {
          console.log('Remove result: ' + response.status);

          if (response.status === 204) {
            let horizontalPosition: MatSnackBarHorizontalPosition = 'end';
            let verticalPosition: MatSnackBarVerticalPosition = 'top';

            this.snackBar.open('Folder nie jest pusty - usunięcie nie jest możliwe', 'Zamknij', {
              duration: 10000,
              horizontalPosition: horizontalPosition,
              verticalPosition: verticalPosition,
              });
          }

          this.fileService.getByPath().subscribe(data => {
            this.files = data;
          });

        });
      }

    });

  }

}

function compare(a, b, isAsc) {
  if (a === '..') {
    return -1;
  } else if (b === '..') {
    return 1;
  } else {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }
}

