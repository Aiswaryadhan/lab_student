$(document).ready(function(){
//console.log("ready");
// alert("Welcome");
            $("#user").blur(function(){
                var user = $('#user').val();
                if(user=='')
                {
                      $('#error_user').slideDown();
                      $('#error_user').html('Please provide username');
                      status = 1;
                      return false;
                }
                else
                {
                      $('#error_user').slideUp();
                      status = 0;


                }
            });
           $("#pass").blur(function(){
//           alert("password");
                var pass = $('#pass').val();
                if(pass=='')
                {
                       $('#error_pass').slideDown();
                       $('#error_pass').html('Please provide password');
                       status = 1;
                       return false;
                }
                else
                {
                       $('#error_pass').slideUp();
                       status = 0;

                }
           });
//           $("sub").click(function(){
//           alert("HHHH");


//           });
            $('#submit').click(function(){
                var user=$('#user').val();
                var pwd=$('#pass').val();

                var logCred = {
                            'id': user,
                            'password': pwd
                        };
                         var aJson = JSON.stringify(logCred);
                $.ajax({
                            type : "POST",
                            url : 'http://localhost:8090/student/login',
                            headers : {
                                "Content-Type" : "application/json"
                            },
                             data:aJson,
                            success : function(data) {
                                if(data==="success"){
                                                           $('#error_cred').slideUp();
                                                           alert("success");

                                }
                                else{
                                      $('#error_cred').slideDown();
                                      $('#error_cred').html('Incorrect username or password');
                                }

                            }
                        });
            });//close of click
});//close of document ready