
function xyz(){
var name = $("#name").val();
var email = $("#email").val();
$.ajax({
         type: 'GET',
         url:'/content/test/test-1/jcr:content.text',
         data:'name='+name+'&email='+email,
         success: function(msg){
         //  alert("name ="+name+",,,,email ="+email);
           alert(msg); //display the data returned by the servlet
         },
         error: function() {
       //  alert("name ="+name+",,,,email ="+email);
                   alert("error!");
                 }
     });
}