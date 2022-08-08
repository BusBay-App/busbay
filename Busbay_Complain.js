//https://script.google.com/u/1/home/projects/1x5hRd05UPCWTIh075twClaqtuFGIxle5SuxLknZHy3zDvJBTzC1BWBhy/edit

//https://docs.google.com/spreadsheets/d/1oZPAqjsYeZS-e6p6RMAKUeAbSIKga1zFzim8Cq6NFT4/edit?pli=1#gid=0


var ss =SpreadsheetApp.openByUrl("https://docs.google.com/spreadsheets/d/1oZPAqjsYeZS-e6p6RMAKUeAbSIKga1zFzim8Cq6NFT4/edit?usp=sharing"); 

var  sheet=ss.getSheetByName('Sheet1');
function doPost(e){
  // var date =new Date_Time();
  var date_time= e.parameter.Date_Time;
  var itemroll_No=e.parameter.Roll_No;
  var itememail_id=e.parameter.Email_Id;
  var itemType=e.parameter.Type;
  var itemroom_details=e.parameter.Room_Details;
  var itemph_no=e.parameter.Phone_No;
  var itemsubject =e.parameter.Subject;
  var itemissue=e.parameter.Issue;

  sheet.appendRow([date_time,itemroll_No,itememail_id,itemType,itemroom_details,itemph_no,itemsubject,itemissue]);
  return ContentService.createTextOutput("Success").setMimeType(ContentService.MimeType.TEXT);

  
}