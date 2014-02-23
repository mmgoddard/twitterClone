/**
 * 
 */ 


$(document).ready(function() {
	checkCount();
	
	var parameter = getUrlParameters("userline", "", true);

	if(parameter == "false") {
		$("#dashboard").fadeIn();
		$("#dashboard").css("display", "block");
		
		$("#userline").fadeIn();
		$("#userline").css("display", "block");   
	}
	
	$("#dashboard").click(function(e) {
		$("#dashboard").fadeOut();
		$("#dashboard").css("display", "none");
		$("#userline").fadeOut("slow");
	});
	
	$(".textArea").focus(function() {
		$(".textArea").css("height", "150px");
	});
	$(".blab").click(function(e) {
		$(".textArea").css("height", "150px");
		alert("shithouse");
	});
	
	
});

function checkCount()
{
	$(".textArea").keyup(function() {
		var maxLength = 140;
		var length = $(this).val().length;
		if($(this).val().length < 3) {
			//e.preventdefault();
			$(".blab").css("background-color", "white");
		}
		else
		{
			$(".blab").css("background-color", "#aacfe4");
		}
		$(".countOutput").text(maxLength - length);
	});
}
	
	function getUrlParameters(parameter, staticURL, decode){

		   var currLocation = (staticURL.length)? staticURL : window.location.search,
		       parArr = currLocation.split("?")[1].split("&"),
		       returnBool = true;
		   
		   for(var i = 0; i < parArr.length; i++){
		        parr = parArr[i].split("=");
		        if(parr[0] == parameter){
		            return (decode) ? decodeURIComponent(parr[1]) : parr[1];
		            returnBool = true;
		        }else{
		            returnBool = false;            
		        }
		   }
		   
		   if(!returnBool) return false;  
		}