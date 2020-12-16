/**
 * ajax form validator - jQuery plugin
 *
 * @Author bright_zheng
 * @Version 1.0
 * 
 * Usage:
 * 1. by default, we can just bind this to onclick event of the form's submit button
 * 
 * 	something like this:
 *  	<input type="button" onclick="$(this).ajaxFormValidator();">Find Hotels</input>
 *  
 * 2. we can customize the behaviors like:
 * 	1) beforeFormValidate, by defining a new method like this to override the default:
 * 		$.fn.ajaxFormValidator.beforeFormValidate = function(xhr){
 *	    	//the param xhr is the XMLHttpRequest instance so we can customize the header etc.
 *	    }
 *	2) afterFormValidate, by defining a new method like this to override the default:
 *		$.fn.ajaxFormValidator.afterFormValidate = function(form, errors){
 *		}
 *  3) and then use it as normal,
 *  	<input type="button" onclick="$(this).ajaxFormValidator();">Find Hotels</input>
 */
(function($){
    $.fn.ajaxFormValidator = function(options){
    	var defaults = {
		    suffixUrl 	: '_ajax_form_validation=true'	//default suffix uri parameter pattern
		};
    	
    	var config = $.extend(defaults,options || {});

    	//$(this) means the button clicked
    	var clickedButton = $(this);
    	var form = clickedButton.parents("form");
    	
    	var params = form.serialize();
    	
    	//24/12/2010 by bright_zheng: add the current button to the params for better @RequestMapping
    	//Tools.appendClickedButtonAsUrlParam(clickedButton);
    	//13/01/2011 by bright_zheng: change appending pattern from parameter to part of uri
    	var canContinue = Tools.appendClickedButtonAsAppendingUri(clickedButton);
    	
    	
    	if(!canContinue && canContinue != undefined){
    		return false;
    	}
    	
    	//alert("isConfirmationRequired "+Tools.isConfirmationRequired(clickedButton));
    	
    	//add ajax convention parameter
    	var originalUrl = form.attr("action");
    	var url = originalUrl.indexOf("?")>0 
    				? (originalUrl + '&' + config.suffixUrl)
    				: (originalUrl + '?' + config.suffixUrl);
    	
    	//18/01/2011 by bright: the method should follow the <form> setting instead of hard code POST
    	var md = (form.attr("method")==null) ? 'POST' : form.attr("method");
    	//invoke by jQuery ajax
    	$.ajax({
    		type: md,
    		url: url,
    		data: params,
    		beforeSend: function(xhr){
    			$.fn.ajaxFormValidator.beforeFormValidate(xhr);
    		},
    		success: function(data){
    			//var errors = $(config.renderTo).html(data).hide();
    			var errors = $("div",data);

    			// Check for axjaFormValidatorResult to know if the return is an exception page
    			if($("input[id='axjaFormValidatorResult']",data).length == 1){
    				if($("input[id='axjaFormValidatorResult']",data).val() == "accessDenied"){
    					//The id must be an existing element.
    					errors=$("<div id='logoutApplet.required'>Ajax validation was send to a URL which access is denied. Please verify the access control for "+url+".</div>");
    					window.location.href='/nric/servlet/accessDeny';
    				}
    				else if($("input[id='axjaFormValidatorResult']",data).val() == "sessionTimeout"){
    					//The id must be an existing element.
    					errors=$("<div id='logoutApplet.required'>Session has timeout. Please re-login.</div>");
    					window.location.href='/nric/servlet/sessionTimeout';
    				}
    			}
    			//invoke the callback
    			$.fn.ajaxFormValidator.afterFormValidate(form, errors);
    		}
    	});
    	return false;
    };

	/**
	 * do nothing by default
	 */
	$.fn.ajaxFormValidator.beforeFormValidate = function(xhr){
    	//do nothing by default
    };
	
    /**
     * default callback handler after form validation
     */
	$.fn.ajaxFormValidator.afterFormValidate = function(form, errors){
		Tools.HideWait();
		//console.info(errors);
		if(errors==null || errors.size()==0){
			//no error
			console.info("No error");			
			//submit immediately if no error
			form.submit();
		}else{
			
			//bind errors to tooltip
			console.info(errors.size()+" errors found");
			//reset it first
			$.fn.errorBinder.reset();
			//render errors
			$(errors).errorBinder();
			$("#top").focus();
		}
    };
})(jQuery);