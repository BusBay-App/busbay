//https://script.google.com/u/1/home/projects/1L2HatJhLx3paPS8GJYTDgJTUbfSb-szXa2BycETsShxXERaHq10QHXHt/edit

//https://docs.google.com/spreadsheets/d/1nQidztlT00-aOGoPwm1NhrqWxvMaq7AMKoQ1no4W6OU/edit#gid=0



var ss = SpreadsheetApp.openByUrl("https://docs.google.com/spreadsheets/d/1nQidztlT00-aOGoPwm1NhrqWxvMaq7AMKoQ1no4W6OU/edit?usp=sharing")


var  sheet=ss.getSheetByName('Poll');
function doPost(e){

  if(true){
    return updateItem(e);
  }
  else{
 // var date =new Date_Time();
  var date= e.parameter.Date;
  var itememail_id=e.parameter.Email_Id;
  var itembranchyear=e.parameter.Branch_Year;
  var itemquestion=e.parameter.Question;
  var itemoptn1=e.parameter.Option1;
  var itemoptn2 =e.parameter.Option2;
  var itemoptn3=e.parameter.Option3;
  var itemoptn4=e.parameter.Option4;
  var itemoptn5=e.parameter.Option5;
  var itemoptn6=e.parameter.Option6;
  var itemoptn7=e.parameter.Option7;
  
  var itemc1=e.parameter.c1;
  var itemc2=e.parameter.c2;
  var itemc3=e.parameter.c3;
  var itemc4=e.parameter.c4;
  var itemc5=e.parameter.c5;
  var itemc6=e.parameter.c6;
  var itemc7=e.parameter.c7;
  


  sheet.appendRow([date,itememail_id,itembranchyear ,itemquestion ,itemoptn1,itemoptn2,itemoptn3,itemoptn4,itemoptn5,itemoptn6,itemoptn7  , itemc1,itemc2,itemc3,itemc4,itemc5,itemc6,itemc7]);
  return ContentService.createTextOutput("Success").setMimeType(ContentService.MimeType.TEXT);

  
  }


 
  
}

function doGet(e){
  var records={};

  var temprow=sheet.getRange(2,1,sheet.getLastRow()-1,sheet.getLastColumn());

  temprow.sort({column:1,ascending:false});
  var rows=temprow.getValues();


  data=[];

  var l=rows.length;
  for (var r=0; r<l ;r++){
    var row =rows[r],record ={};
    record['date']=row[0];
    record['itememail_id']=row[1];
    record['itembranchyear']=row[2];
    record['itemquestion']=row[3];
    record['itemoptn1']=row[4];
    record['itemoptn2']=row[5];
    record['itemoptn3']=row[6];
    record['itemoptn4']=row[7];
    record['itemoptn5']=row[8];
    record['itemoptn6']=row[9];
    record['itemoptn7']=row[10];
    record['itemc1']=row[11];
    record['itemc2']=row[12];
    record['itemc3']=row[13];
    record['itemc4']=row[14];
    record['itemc5']=row[15];
    record['itemc6']=row[16];
    record['itemc7']=row[17];


  

    data.push(record);
  }

  records.items=data;
  var result=JSON.stringify(records);
  return ContentService.createTextOutput(result).setMimeType(ContentService.MimeType.JSON);



}





function updateItem(e){
  
// var itemId = e.parameter.itemId;
// var itemName = e.parameter.itemName;
// var brand = e.parameter.brand;
// var price = e.parameter.price;
var date= e.parameter.Date;
var optionn=parseInt(e.parameter.option_no_selected);
  // var itememail_id=e.parameter.Email_Id;
  // var itembranchyear=e.parameter.Branch_Year;
  // var itemquestion=e.parameter.Question;
  // var itemoptn1=e.parameter.Option1;
  // var itemoptn2 =e.parameter.Option2;
  // var itemoptn3=e.parameter.Option3;
  // var itemoptn4=e.parameter.Option4;
  // var itemoptn5=e.parameter.Option5;
  // var itemoptn6=e.parameter.Option6;
  // var itemoptn7=e.parameter.Option7;

  // var itemc1=e.parameter.c1;
  // var itemc2=e.parameter.c2;
  // var itemc3=e.parameter.c3;
  // var itemc4=e.parameter.c4;
  // var itemc5=e.parameter.c5;
  // var itemc6=e.parameter.c6;
  // var itemc7=e.parameter.c7;
  
 var rows = sheet.getRange(2, 1, sheet.getLastRow() - 1,sheet.getLastColumn()).getValues();

  for (var r = 0, l = rows.length; r < l; r++) {
    var row     = rows[r]; 
    
    if(row[0] == date){

        r= r+2;
      // sheet.getRange(r,2).setValue(itememail_id);
      // sheet.getRange(r,3).setValue(itembranchyear);
      // sheet.getRange(r,4).setValue(itemquestion);
      // sheet.getRange(r,5).setValue(itemoptn1);
      // sheet.getRange(r,6).setValue(itemoptn2);
      // sheet.getRange(r,7).setValue(itemoptn3);
      // sheet.getRange(r,8).setValue(itemoptn4);
      // sheet.getRange(r,9).setValue(itemoptn5);
      // sheet.getRange(r,10).setValue(itemoptn6);
      // sheet.getRange(r,11).setValue(itemoptn7);
      var temp=sheet.getRange(r,12+optionn-1).getValue()+1;

      // option_no_selected
      sheet.getRange(r,12+optionn-1).setValue(temp);
      // sheet.getRange(r,13).setValue(itemc2);
      // sheet.getRange(r,14).setValue(itemc3);
      // sheet.getRange(r,15).setValue(itemc4);
      // sheet.getRange(r,16).setValue(itemc5);
      // sheet.getRange(r,17).setValue(itemc6);
      // sheet.getRange(r,18).setValue(itemc7);

      
      return ContentService.createTextOutput("Value updated Successfully").setMimeType(ContentService.MimeType.JSON);
    
   }    
   
   
}

return ContentService.createTextOutput("Id Did not found").setMimeType(ContentService.MimeType.JSON);
}



