//import { browser, element, by } from 'protractor/globals';  
import { AppPage } from './app.po';

describe('workspace-project App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });
  
  it("should show current path when we first load app", () => {  
    browser.get("/");
    let pwds = element.all(by.css(".pwd"));
    expect(pwds.count()).toEqual(1);
})
  
  it("should show list files when we first load app", () => {  
    browser.get("/");
    let tables = element.all(by.css(".mat-table"));
    expect(tables.count()).toEqual(1);
})
  
  it("should be able to rename a file", () => {  
    browser.get("/");
    let tables = element.all(by.css(".mat-table"));
    
    let files = element.all(by.css(".mat-row"));
    if (files.count() > 0) {
      let fileName = element.all(by.css(".filename")).first();
      let fileNameText = fileName.getText();
      
      let renameIcon = element(by.css(".irename")).first();
      renameIcon.click();
      
      let inputElement = element(by.css(".mat-input-element")).first();
      inputElement.setAttribute("value","testFileName");
      
      let buttonOk = element(by.css(".mat-raise-button")).first();
      buttonOk.click();
      
      let fileName = element.all(by.css(".filename")).first();
      let fileNameText = fileName.getText();
      
      
       expect(fileNameText).toEqual("testFileName");
    } 
})
  
  it("should file name in remove dialog is equal file name in table", () => {  
    browser.get("/");
    let tables = element.all(by.css(".mat-table"));
//    expect(tables.count()).toEqual(1);
    
    let files = element.all(by.css(".mat-row"));
    if (files.count() > 0) {
      let fileName = element.all(by.css(".filename")).first();
      let fileNameText = fileName.getText();
      
      let renameIcon = element(by.css(".iremove"));
      renameIcon.click();
      
      let inputElement = element(by.css(".mat-input-element")).first();
      let inputElementText = inputElement.getAttribute("value");
      
       expect(inputFieldText).toEqual(firstTodoText);
    } 
})
  

});
