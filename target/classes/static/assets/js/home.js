$(document).ready(function(){
     if ($.cookie("id") != null) {
                var studId =$.cookie("id");
//                $("#studName").text(studId);
//                $.ajax({
//                           type: "GET",
//                           url: 'http://localhost:8090/student/getName/'+studId,
//                           success: function (data) {
//
//                                        $("#studName").text(data);
//                           }
//                });

            }

});//close of document ready