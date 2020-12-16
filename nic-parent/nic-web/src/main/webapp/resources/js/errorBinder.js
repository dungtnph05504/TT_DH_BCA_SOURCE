/**
 * Form validation errors auto binder - jQuery plugin
 * This plugin can be used to 
 * 1. change an existing normal tooltip to an error tooltip,
 * 2. or create an error tooltip if the error can match the input element which don't have tooltip yet,
 * 3. or treat the error as global error if both above conditions can't be met.
 *
 * @Author bright_zheng
 * 
 * Usages:
 * 	We should not use it directly, please refer to {@link ajaxFormValidator.js}:
 *  	$(errors).errorBinder();
 */
(function($){
    $.fn.errorBinder = function(options){
    	var config = $.extend($.fn.errorBinder.defaults, options || {});
    	//render error icon here
		var html = '<table class="ui-state-error" style="font:14px Verdana, Arial, Helvetica, sans-serif;font-weight:bold;" width="100%"><tr><td><span class="error ui-icon ui-icon-alert"></span>Error Message(s)</td></tr></table>';    	
    	$(config.renderErrorIconTo).append(html);
		
    	return this.each(function() {
    		var $this = $(this);
    		
    		buildOneTooltip($this, config);
    	});
    };
    
    /**
     * default setting
     */
    $.fn.errorBinder.defaults = {
	    errorIcon 			: 'X',						//default error icon is a 'X' char
	    tooltipIcon 		: '?',						//default tooltip icon is a '?' char
	    multiErrorSplitor 	: ' -- ',					//default error messages splitor
	    errorClass			: 'errors',					//global error element css class
	    addAllErrorsToGlobal: true,						//all errors add to global container?
	    renderTo			: '#global_error_div',		//default global error container
	    renderErrorIconTo	: '#global_icon_div',
	    renderMessage		: '#message'
    };

	/**
	 * reset tooltip to default
	 */
	$.fn.errorBinder.reset = function(options){
		var config = $.extend({}, $.fn.errorBinder.defaults, options);
    	//1. remove global error messages
		if($(config.renderTo).html() != "")
			$(config.renderTo).html("");
		if($(config.renderErrorIconTo).html() != "")
			$(config.renderErrorIconTo).html("");
		if($(config.renderMessage).html() != "")
			$(config.renderMessage).html("");
		resetErrorFields();
		
		//2. reset tooltips
		$(".errors").each(function(index, element){
			var e = $(element);
			if(e.attr("newFlag")!=null){
				//remove new created error tooltips
				e.remove();
			}else{
				//reset error tooltip to normal tooltip
				e.removeClass("errors").html(config.tooltipIcon);
				//reset the message
				var currentMsg = e.attr("title-original");
				var originalMsg = currentMsg.substring(0,currentMsg.indexOf(config.multiErrorSplitor));
				e.removeAttr("title-original").removeAttr("tooltipType");
				e.attr("title", originalMsg);
				e.tooltip();
			}
			
		});
    }
    
    /**
     * build normal tooltip or error tooltip
     * based on existing title or error message
     */
    function buildOneTooltip(error, config){
		//must escape char '.' in id. e.g, 
    	//original	: hotel.query.keyword.required
    	//target	: hotel\.query\.keyword\.required
    	var id = error.attr("id").replace(/\./g, "\\.");
    	var message = error.text().replace(/\"/g, "\"");
		
		//check if the same input binding id exist
		var o = getBindingContainer(id, "span");
		
		if(o != null && o.size()>0){
			//exist
			if(o.attr("tooltipType")==null){
				//normal tooltip
				console.info("change to error tooltip: id=" + id + ", message=" + message);
				changeToErrorTooltip(o, message, config);
			}else{
				//error tooltip
				console.info("add error to existing error tooltip: id=" + id + ", message=" + message);
				addToExistingErrorTooltip(o, message, config);
			}

    		//add to global by config
    		if (config.addAllErrorsToGlobal){
    			appendErrorToGlobalContainer(id, message, config);
    		}
		}else{
			//not exist, build the error tooltip if same id binding found
			//or put the error to global container
			console.info("build new error tooltip: id=" + id + ", message=" + message);
			createExtraErrorTooltip(id, message, config);
		}
    }
    
    /**
     * based on the id, get the binding container
     */
    function getBindingContainer(id, type){
    	var i = id.lastIndexOf("\\\.");
    	var bindingId = type + "#" + id.substring(0,i);
    	console.info("binding container id: "+bindingId);
    	
    	return $(bindingId);
    }
    
    /**
     * Change existing normal tooltip to error tooltip
     */
    function changeToErrorTooltip(originalObject, message, config){
    	originalObject.html(config.errorIcon).addClass("errors").attr("tooltipType","E");
    	var newTitle = originalObject.attr("title-original") + config.multiErrorSplitor + message;
    	originalObject.attr("title", newTitle);
    	originalObject.tooltip({
    		showBody: config.multiErrorSplitor
		});
    }
    
    /**
     * Support multiple error binding to one same tooltip
     */
    function addToExistingErrorTooltip(errorTooltip, message, config){
    	var newTitle = errorTooltip.attr("title-original") + config.multiErrorSplitor + message;
    	errorTooltip.attr("title", newTitle);
    	errorTooltip.tooltip({
    		showBody: config.multiErrorSplitor
		});
    }
    
    /**
     * Build a new error tooltip
     * 
     * <span id="hotel.query.keyword_required" class="tips" title="ABC" />">X</span>
     */
    function createExtraErrorTooltip(id, message, config){
    	var objects = getBindingContainer(id, ":input");
    	
    	if(objects!=null && objects.size()>0){
    		//found
    		var element = objects[0];
    		var html = '<span id="'+id+'" class="tips '+config.errorClass+'" newFlag="1" title="'+message+'">'+config.errorIcon+'</span>';
    		//$(element).after($(html).tooltip());
    		//alert(element.name);
    		$(element).css("background","yellow");
    		
    		//add to global by config
    		if (config.addAllErrorsToGlobal){
    			appendErrorToGlobalContainer(id, message, config);
    		}
    	}else{
    		//not found
    		//alert(objects[0].name);
    		appendErrorToGlobalContainer(id, message, config);
    	}
    }
    
    /**
     * append error to global div container 
     */
    function appendErrorToGlobalContainer(id, message, config){
    	var html = '<div id="'+id+'" class="'+config.errorClass+'" title="'+message+'">'+"&#x2022;&nbsp;"+message+"</div>";    	
    	$(config.renderTo).append(html);
    }
    
	function resetErrorFields() {
    	var inputs = document.getElementsByTagName('input');
    	var inputs2 = document.getElementsByTagName('select');
    	//alert(inputs.length);
    	if(inputs.length >0){
	    	for (var i=0; i < inputs.length; i++) {
	    		if ((inputs[i].getAttribute('type') == 'text') || (inputs[i].getAttribute('type') == 'radio') || (inputs[i].getAttribute('type') == 'password') ) {
					var fieldName = inputs[i];
					/**
					 * Check whether it is text field that is readonly
					 * If true, do not need to reset the background color because 
					 * value inside will not fail the validation
					 * 
					 * @author xueyan_huang
					 */
					var style = fieldName.getAttribute('readonly');					
					if (style != null)
					{
						if (style == false)
						{
							$(fieldName).css("background","#E0EAFB");
						}
					}
					else //This happen for firefox
					{
						$(fieldName).css("background","#E0EAFB");
					}
				}
			}
    	}
    	
    	if(inputs2.length >0){
    	for (var x=0; x < inputs2.length; x++) {
			var fieldName = inputs2[x];
		//alert(fieldName);
			$(fieldName).css("background","#E0EAFB");			
		}
    	}
    }
})(jQuery);