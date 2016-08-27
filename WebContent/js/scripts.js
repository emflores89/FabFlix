/**
 * Created by egflo on 3/24/2016.
 */
function resetData()
{
    document.getElementById('info').reset();
}

$(document).ready(
	    function () {
	    	$('nav li ul').hide().removeClass('fallback');
	        $(".hoverli").hover(
	          function () {
	             $('account_menu').finish().slideDown('medium');
	          }, 
	          function () {
	             $('account_menu').finish().slideUp('medium');
	          }
	    );
	});

$(document).ready(function() {
	var cartPopup = $('<div id="cart-window"></div>');
	$(document.body).append(cartPopup);
	
	$(".carttrigger").click(function(e) {
		var $target = $(e.target);
		var offset = $target.offset();
		var height = $target.height();

		var movie_id =  $target.attr("id");
		$.ajax({
			url : "/fabflix/CartPopUpWindow",
			data : {
				movieid: movie_id
			},
			success : function(data, textStatus, jqXHR) {
				cartPopup.append('<div id="CartData"></div>');
				$('#CartData').html(data);
				
				cartPopup.show();
				cartPopup.css({
					position : "absolute",
					top : offset.top + $target.height(),
					left : offset.left + $target.width(),
					backgroundColor : "white",
					textAlign: "center",
					border : "2px solid black",
					padding: "10px 30px 10px 30px"
				})
				

					
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert(textStatus);
			}
		});
	});
	
	$(".carttrigger").mouseleave(function(e) {
	    setTimeout(function () {
	    	cartPopup.hide();
	    	cartPopup.empty();		
	    }, 6000);
		
	});

});

$(document).ready(function() {
	var moviePopup = $('<div id="movie-window"></div>');
	$(document.body).append(moviePopup);
	
	$(".trigger").mouseover(function(e) {
		var $target = $(e.target);
		var offset = $target.offset();
		var height = $target.height();

		var movie_id =  $target.attr("id");
		$.ajax({
			url : "/fabflix/MoviePopUpWindow",
			data : {
				movieid: movie_id
			},
			
			success : function(data, textStatus, jqXHR) {
				moviePopup.append('<div id="MovieData"></div>');
				$('#MovieData').html(data);
				
				moviePopup.show();
				moviePopup.css({
					position : "absolute",
					top : offset.top + $target.height(),
					left : offset.left + $target.width(),
					backgroundColor : "white",
					border : "2px solid black"
				})
					
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert(textStatus);
			}
		});
	});

	$(".trigger").mouseleave(function(e) {
	    setTimeout(function () {
	    	moviePopup.hide();
	    	moviePopup.empty();		
	    }, 6000);
		
	});
});


$(document).ready(function() {
    $(function() {
        $("#term").autocomplete({
        	minLength: 1,
            source: function(request, response) {
                $.ajax({
                    url: "/fabflix/MovieAutoComplete",
                    dataType : "json",
                    data: { term: request.term },
                    success: function(data) {
                        response(data);
                    },
                    
                    error: function(xhr, textStatus, errorThrown) {
                        alert(textStatus);
                        },
                    
               });              
            }   
        });
    });
});