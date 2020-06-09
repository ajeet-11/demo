function showData(){
var department = $("#department").val();
$.ajax({
         type: 'GET',
         url:'/bin/fetchNodeValue',
         data:'department='+department,
         success: function(data){
          $("#tab_data").show();
           $("#tab_data > tbody").empty();
           var row = "" ;
           var obj = JSON.parse(data);
            for(i=0; i<obj.length; i++){
             row += "<tr><td> "+ obj[i]["firstName:"] + "</td><td> "+ obj[i]["lastName:"] + "</td><td> "+ obj[i]["department:"] + "</td><td> "+ obj[i]["Experience:"] + "</td></tr>";
            }
         $("#tab_data > tbody").append(row);
         },
         error: function() {
           window.alert("error!");
         }
     });
}
