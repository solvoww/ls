import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { LsPath } from '../model/lspath';
import { HttpHeaders } from '@angular/common/http';
import { ResponseContentType } from '@angular/http';
import {RequestOptions, Request, RequestMethod, Headers} from '@angular/http';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private http: HttpClient) {}

  backEndUrl = '//localhost:2222/';

  getCurrentPath(): Observable<any> {
    return this.http.get(this.backEndUrl + 'pwd');
  }
  
  putPath(path) {
//    this.http.put('//localhost:2222/edit?path=' + path);
  }
  
  
    makePutRequest(path) {
      
    console.log('makePutRequest ' + path);

      

    let headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    
    let lspath = new LsPath();
      
      lspath.path = path;
      
      let bodyObj = {
      userId: 1,
      id: 1,
      title: "new title",
      body: "new body"
    };

    this.http
      .put('//localhost:2222/edit', JSON.stringify(bodyObj), {headers: headers});
      
    
//     this.http.put('//localhost:2222/edit', JSON.stringify(bodyObj), {headers: headers});
//     this.http.put('//localhost:2222/edit', lspath, {headers: headers});
  }
  
private extractData(res: Response) {
    let body = res.json();
    return body;
  }
  private handleError (error: any) {
    // In a real world app, we might use a remote logging infrastructure
    // We'd also dig deeper into the error to get a better message
    let errMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg); // log to console instead
    return Observable.throw(errMsg);
  }

  getAll(): Observable<any> {
    return this.http.get(this.backEndUrl);
  }

  setPath(path): Observable<any> {
    return this.http.get(this.backEndUrl + path);
  }

  getByPath(): Observable<any> {
    return this.http.get(this.backEndUrl + 'ls');
  }

  viewFile(fileName): Observable<any> {
    return this.http.get(this.backEndUrl + 'cat/' + fileName,  {responseType: 'blob'});
  }

  rename(oldName, newName): Observable<any> {
    return this.http.get(this.backEndUrl + oldName + '\\' + newName);
  }

  remove(name): Observable<any> {
    return this.http.get(this.backEndUrl + 'rm/' + name, {observe: 'response'});
  }

}
