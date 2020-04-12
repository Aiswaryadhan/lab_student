$(document).ready(function(){
    var studId;
    var receiver;
    if ($.cookie("id") != null) {
                var studId =$.cookie("id");
                $.ajax({
                           type: "POST",
                           url: 'http://localhost:8090/student/getName/'+studId,
                           success: function (data) {

                                        $("#studName").text(data);
                                        username = $("#teacher_name").text();
                                        connect();
                           }
                });

    }


});