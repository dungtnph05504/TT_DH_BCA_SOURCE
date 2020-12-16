/**
 * useful javascript tools for project
 * 
 * @author bright_zheng
 */

/**
 * this is the script which can avoid 'console is undefined' error
 * when we use console.info/console.debug etc. to get log info in our javascript
 * without firebug installed
 * 
 * @author bright_zheng
 */
if (!("console" in window) || !("firebug" in console)) {
	var names = ["log", "debug", "info", "warn", "error", "assert", "dir", "dirxml", "group", "groupEnd", "time", "timeEnd", "count", "trace", "profile", "profileEnd"];
	window.console = {};
	for (var i = 0, len = names.length; i < len; ++i) {
		window.console[names[i]] = function(){};
	}
}


/** 
 * extends the default String object, 
 * so all String objects can get the trim() method
 * 
 * @author bright_zheng
 */
String.prototype.trim = function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");  
};

Tools = {};

/**
 * automatically add the clicked button as one of the parameters of request
 * for instance, if the button is:
 * 	<input type="button" name="decision" command="proceed" value="Proceed"/>
 * then the url will be:
 * 	original: /hotels/query
 *  target: /hotels/query?decission=proceed
 * 
 * If the value of the button has blank or may be changeable, 
 * we should use custom attribute 'command'
 * 
 * for example:
 * 	<input type="button" name="decision" value="Proceed"/>
 * or if no space in the value or the value is NOT changeable
 *  <input type="button" value="Proceed"/>
 * or for more stability, always use custom property of 'command'
 *  <input type="button" name="decision" command="Process" value="Please Proceed"/>
 *  
 * @author bright_zheng
 */
Tools.appendClickedButtonAsUrlParam = function(clickedButton){
	// 11/05/2911 - by Boon Seng
	// Add a check to determine if the confirmation is required
	// for the clicked button. If true, display the confirmation.
	if(Tools.isConfirmationRequired(clickedButton)){
		var requiredMsg =Tools.getConfirmationMsg(clickedButton);
		//alert(requiredMsg);
		 if( requiredMsg =='' || requiredMsg == undefined){
			 requiredMsg ='Confirm to ' + $(clickedButton).attr("value")+' ?';
		 }
		 else{
			 requiredMsg ='Confirm to ' + requiredMsg+' ?';
	 	}
		//alert(confirm(requiredMsg));
			var handler = function(event) {    event.preventDefault();Tools.HideWait();};
			if(!confirm(requiredMsg)){
				//alert('Cancel');
				var form = clickedButton.parents("form");
				form.unbind('submit.submitEvent');
				form.bind('submit.submitEvent',handler);
				return false;
			}
			else{
				var form = clickedButton.parents("form");
				form.unbind('submit.submitEvent');
			}
	}
	else{
		var form = clickedButton.parents("form");
		form.unbind('submit.submitEvent');
	}
	
	var commandName = clickedButton.attr("command")!=null 
						? clickedButton.attr("command")
						: clickedButton.val();
	var clickedButtonParam = clickedButton.attr("name") + "=" + commandName;
	var form = clickedButton.parents("form");
	var originalUrl = form.attr("original-action");
	if (originalUrl==null){
		originalUrl = form.attr("action");
		form.attr("original-action", originalUrl);
	}
	var url = (originalUrl.indexOf("?") > -1) 
			? (originalUrl + '&' + clickedButtonParam)
			: (originalUrl + '?' + clickedButtonParam);
	form.attr("action", url);
	// Convert input to uppercase
	Tools.convertUpperCase();
};
/**
 * Almost the same as Tools.appendClickedButtonAsUrlParam
 * but the url pattern will be appended as part of URI, not parameter
 * 
 * for instance, if the button is:
 * 	<input type="button" name="decision" command="proceed" value="Proceed"/>
 * then the url will be:
 * 	original: /hotels/query
 *  target: /hotels/query/proceed
 */
Tools.appendClickedButtonAsAppendingUri = function(clickedButton){
	
	// 11/05/2911 - by Boon Seng
	// Add a check to determine if the confirmation is required
	// for the clicked button. If true, display the confirmation.
	if(Tools.isConfirmationRequired(clickedButton)){
		var requiredMsg =Tools.getConfirmationMsg(clickedButton);
		//alert(requiredMsg);
		if( requiredMsg =='' || requiredMsg == undefined){
			 requiredMsg ='Confirm to ' + $(clickedButton).attr("value")+' ?';
		 }
		 else{
			 requiredMsg ='Confirm to ' + requiredMsg+' ?';
	 	}
		//alert(confirm(requiredMsg));
		var handler = function(event) {event.preventDefault();Tools.HideWait();};
		if(!confirm(requiredMsg)){
			//alert('Cancel');
			var form = clickedButton.parents("form");
			form.unbind('submit.submitEvent');
			form.bind('submit.submitEvent',handler);
			return false;
		}
		else{
			var form = clickedButton.parents("form");
			form.unbind('submit.submitEvent');
		}
	}
	else{
		var form = clickedButton.parents("form");
		form.unbind('submit.submitEvent');
	}

	var commandName = clickedButton.attr("command")!=null 
						? clickedButton.attr("command")
						: clickedButton.val();
	var form = clickedButton.parents("form");
	var originalUrl = form.attr("original-action");
	if (originalUrl==null){
		originalUrl = form.attr("action");
		form.attr("original-action", originalUrl);
	}
	//if action is '/hotels' then '/hotels/proceed'
	//but if action is '/hotels?param=1' then '/hotels/proceed?param=1'
	var questionMark = originalUrl.indexOf("?");
	//at first application access will be registration;jsessionid=79mi6yx6jkyt?param=1
	var semicolonMark = originalUrl.indexOf(";jsessionid=");
	var url = (semicolonMark > -1) 
			? (originalUrl.substring(0,semicolonMark) + '/' + commandName + originalUrl.substring(semicolonMark))
			: (questionMark > -1) 
			? (originalUrl.substring(0,questionMark) + '/' + commandName + originalUrl.substring(questionMark))
			: (originalUrl + '/' + commandName);
	console.info("revised URL:" + url);
	form.attr("action", url);
	// Convert input to uppercase
	Tools.convertUpperCase();
};

/**
 * Convert all inputs other then button and submit value to uppercase.
 * If the page define a excludeList() function that returns a Array of String
 * that contains the id of the input to exclude in the converting to uppercase
 *  onclick="Tools.convertUpperCase();"
 */
Tools.convertUpperCase = function(){
	// Get all the input other than button, submit
	var filteredInputs = $(":input:not(input:[type='button'],input:[type='submit'],input:[type='reset'],input:[type='password'])");
	
	// Check if the excludeList function exist
	if($.isFunction($.exludeList)){
		//Get the list of fields to exclude
		var excludeItems =$.exludeList();
		
		// Loop through all the input fields
		filteredInputs.each(function() {
	        if($.inArray($(this).attr("id"),excludeItems)==-1){
	        	//var inputField =$(":input:[id='"+value+"']");
	        	$(this).val($(this).val().toUpperCase());
	        }	
	    });	
	}
	else{
		//conver all fields to uppercase
		filteredInputs.each(function() {
			if($(this).val() !=null && $(this).val() !="" ){
				$(this).val($(this).val().toUpperCase());
			}
			
	    });	
	}
};

Tools.displayWait = function(){
	/*if(parseInt($.browser.version, 10) > 6) {
		$('#loading').removeClass('displayOff').addClass('displayOn');
	}
	if(parseInt($.browser.version, 10) == 6) {
		$('#load').removeClass('displayOff').addClass('displayOn');
	}*/
  /*	 $.blockUI({ css: { 
         border: 'none', 
         padding: '15px', 
         backgroundColor: '#000', 
         '-webkit-border-radius': '10px', 
         '-moz-border-radius': '10px', 
         opacity: .5, 
         color: '#fff'
     }});*/
	 /** message to display while waiting, uncomment to use customise message**/
	 /*$.blockUI({ 
         message: '<h1 style="font: italic 16px Verdana, Arial, Helvetica, sans-serif;">Please Wait . . .</h1>'
	 });*/
};

Tools.HideWait = function(){
	//$('#loading').removeClass('displayOn').addClass('displayOff');
	//$('#load').removeClass('displayOn').addClass('displayOff');
	$.unblockUI();
};

/**
 * Bind a confirmation dialog to the click event to buttons
 * that does not call ajaxValidator and Tools.appendClickedButtonAsAppending*.
 */
Tools.bindButtonClickEvent = function(){
	
	if($.isFunction($.confirmButtonList)){
		var confirmItems =$.confirmButtonList();
		//alert($.isArray(confirmItems));
		
		$.each(confirmItems, function(index, value){			
			//alert(this.property('Name'));
			var requiredName = this.property('Name');
			var requiredMsg = this.property('Msg');
			
			var filteredInputs = $("input:[type='button'][name='"+requiredName+"'],input:[type='submit'][name='"+requiredName+"'],input:[type='cancel'][name='"+requiredName+"'],input:[type='reset'][name='"+requiredName+"']");	

			//if(filteredInputs!= undefined && filteredInputs.length>1){
				//alert('There are multiple input type that have the same name: '+requiredName+' and are applicable for confirmation. Please check your codes.');
			//}
			
			filteredInputs.each(function() {
				//alert($(this).attr("command"));
				var filteredName = $(this).attr("name");
				
				var onclickEvent = ""+$(this).attr("onclick");
				// Exclude those that are calling ajaxValidator and Tools.appendClickedButtonAsAppending*
				if(onclickEvent.search("Tools.appendClickedButtonAsAppending") ==-1  && onclickEvent.search(".ajaxFormValidator") ==-1){
					//alert(filteredName);
					if(filteredName != undefined){
						if(requiredName == filteredName){
							//alert('match ' + requiredName);
							 $(this).bind('click',function(e){ 
								//alert(requiredMsg);
								 if( requiredMsg =='' || requiredMsg == undefined){
									 
									 return confirm('Confirm to ' + $(this).attr("value")+' ?');
								 }
								 else{
									 return confirm('Confirm to ' + requiredMsg+' ?');
							 	}
							});
						}
					}
				}
				
			});
			
		});
	}
	
};

/**
 * Check if the clickedButton needs to show confirmation dialog.
 * Return true if required, otherwise false.
 */
Tools.isConfirmationRequired = function(clickedButton){
	var result = false;
	if($.isFunction($.confirmButtonList)){
		var confirmItems =$.confirmButtonList();
		$.each(confirmItems, function(index, value){
			var requiredName = this.property('Name');
			var filteredName = clickedButton.attr("name");
			//alert("|"+requiredName +"| |" + filteredName +"|");
			if(filteredName != undefined ){
				if(requiredName == filteredName){
					//alert('match');
					
					result = true;
					
				}
			}
		});		
	}
	//alert(result);
	return result;
};

/**
 * Return the confirmation dialog msg for the clicked button if it require to show confirmation.
 */
Tools.getConfirmationMsg = function(clickedButton){
	var msg = '';
	if($.isFunction($.confirmButtonList)){
		var confirmItems =$.confirmButtonList();
		$.each(confirmItems, function(index, value){
			var requiredName = this.property('Name');
			var requiredMsg= this.property('Msg');
			var clickedCommand = clickedButton.attr("name");
			//alert("|"+requiredName +"| |" + filteredName +"|");
			if(clickedCommand != undefined ){
				if(requiredName == clickedCommand){
					//alert('match');
					
					msg = requiredMsg;
					
				}
			}
		});		
	}
	//alert(msg);
	return msg;
};

Tools.displayConfirmation = function (clickedButton, msg){
	var buttonValue = clickedButton.attr("value");
	var requiredMsg = buttonValue;
	//alert(clickedButton);
	if(msg ==  undefined || msg ==''){
		requiredMsg = 'Confirm to ' + buttonValue + '?';
	}
	else{
		requiredMsg = 'Confirm to ' + msg + '?';		
	}
	var handler = function(event) {event.preventDefault();form.unbind('submit.submitEvent');Tools.HideWait();};
	if(!confirm(requiredMsg)){
//		alert($(clickedButton.get(0)).is('input'));
		var form = clickedButton.parents("form");		
		// Check if the object clicked is Input. If not Input, will not result in form submit.
		// Hence no need to bind to the submit event.
		if($(clickedButton.get(0)).is('input')){
			// If there is only one submit button, the form will auto submit
			// Hence, must bind to the submit event to prevent it to submit
			if($("input[type=submit]",form.get(0)).length >= 1){
				form.bind('submit.submitEvent',handler);				
			}
		}
		return false;
	}
	else{
		var form = clickedButton.parents("form");
//		form.unbind('submit.submitEvent');
		return true;
	}
};


