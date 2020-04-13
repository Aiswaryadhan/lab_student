$(document).ready(function(){
     if(($.cookie("id") != null) && ($.cookie("name") != null) && ($.cookie("sem") !=null)){
                var studId =$.cookie("id");
                var studName=$.cookie("name");
                var studSem=$.cookie("sem");
                $("#studName").text(studName);


            }

});//close of document ready