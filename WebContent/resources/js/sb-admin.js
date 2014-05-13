$(function() {

    $('#side-menu').metisMenu();
   
    $("#e1").select2(); 
    $("#e2").select2();
  
    

});

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
$(function() {
    $(window).bind("load resize", function() {
        console.log($(this).width())
        if ($(this).width() < 768) {
            $('div.sidebar-collapse').addClass('collapse')
        } else {
            $('div.sidebar-collapse').removeClass('collapse')
        }
    })
});

$(function afficheDate()// Notre fonction pour afficher la date et l'heure
	{

	
	date=new Date();

	
	heure=date.getHours();
	minute=date.getMinutes();
	seconde=date.getSeconds();
	

	if (heure<10) heure="0"+heure;
	if (minute<10) minute="0"+minute;
	if (seconde<10) seconde="0"+seconde;
	document.getElementById("texteDate").innerHTML= heure+":"+minute+":"+seconde;
	setTimeout("afficheDate()", 1000);

		});


