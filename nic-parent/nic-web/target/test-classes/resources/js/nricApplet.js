
//Generic function to search for the input type with the Id
function getInputValue(fieldId) {
	var searchCondition = "input[id='" + fieldId + "']";
	return $(searchCondition).val();
}

function getInputValueLowerCase(fieldId) {
	var searchCondition = "input[id='" + fieldId + "']";
	return $(searchCondition).val().toLowerCase();
}

//Generic function to search for the input type with the Id and update the
//value
function updateInputValue(fieldId, value) {
	var searchCondition = "input[id='" + fieldId + "']";
	$(searchCondition).val(value);
}

//Genric function to search for label tag and update the text of the label
function updateLabel(fieldId, value) {
	var searchCondition = "label[id='" + fieldId + "']";
	// alert($(searchCondition).text(value));
	//alert($(searchCondition).length);
	if($(searchCondition).length>0){
		$(searchCondition).text(value);
	}
}

//Genric function to search for label tag and get the text of the label
function getLabel(fieldId) {
	var searchCondition = "label[id='" + fieldId + "']";
	// alert($(searchCondition).text(value));
	//alert($(searchCondition));
	if($(searchCondition).length>0){
		return $(searchCondition).text();
	}
	else return null;
}

//Generic function to search Select tag with the id and get the selected value
function getSelectedValue(fieldId) {
	var searchCondition = "select[id='" + fieldId + "']";
	return $(searchCondition).find(':selected').val();

}

//Search for Option with the Id and set the selected value
function selectSelectValue(fieldId, value) {
	var searchCondition = "select[id='" + fieldId + "']";
	$(searchCondition).val(value);
}

function disableSelect(fieldId){
	var searchCondition = "select[id='" + fieldId + "']";
	$(searchCondition).attr("disabled", true);	
}

function enableSelect(fieldId){
	var searchCondition = "select[id='" + fieldId + "']";
	$(searchCondition).attr("disabled", false);	
}

function isSelectDisabled(fieldId){
	var searchCondition = "select[id='" + fieldId + "']";
	return $(searchCondition).attr("disabled");
}

function showLabel(fieldId) {
	var searchCondition = "label[id='" + fieldId + "']";
	// alert($(searchCondition).text(value));
	return $(searchCondition).show();
}

function hideLabel(fieldId) {
	var searchCondition = "label[id='" + fieldId + "']";
	// alert($(searchCondition).text(value));
	return $(searchCondition).hide();
}

function getSelectedRadioValue(fieldId) {
	var searchCondition = "input:radio[name='" + fieldId + "']:checked";
	//alert($(searchCondition).val());
	return $(searchCondition).val();
}

function selectRadioValue(fieldId, value) {
	var searchCondition = "input:radio[name='" + fieldId + "']";
	//$(searchCondition).val(value);
	
	$.each($(searchCondition), function(index, obj){
		
		if($(this).val() == value){
			
			$(this).attr('checked',true);
			
		}
	});
}

function isChecked(fieldId){
	return $("input[type=checkbox][id="+fieldId+"]").attr("checked");
}

function checkCheckbox(fieldId , value){
	$("input[type=checkbox][id="+fieldId+"]").attr("checked",value);
}

function disableButton(fieldId){
	$("#"+fieldId).attr("disabled", true);
}

function enableButton(fieldId){
	$("#"+fieldId).attr("disabled", false);
}

/** Fingerprint Applet JS Callback functions* */
//Set the Fingerpint Capture Status
function updateFpCapturedStatus(fieldId, value) {
	updateInputValue(fieldId, value);
}

//Retrieve the Fingerprint Capture Status
function getFpCapturedStatus(fieldId) {
	// alert(fieldId);
	var searchCondition = "input[id='" + fieldId + "']";
	// alert(searchCondition);
	// alert($(searchCondition).val());
	return $(searchCondition).val();
}

//Retrieve the Fingerprint match Status
function getFpRematchStatus(fieldId) {
	// alert(fieldId);
	var searchCondition = "input[id='" + fieldId + "']";
	// alert(searchCondition);
	// alert($(searchCondition).val());
	return $(searchCondition).val();
}

//Set the Fingerpint match Status
function updateFpRematchStatus(fieldId, value) {
	updateInputValue(fieldId, value);
}

//Search for Option with the Id and get the selected value
function getFpSelectValue(fieldId) {
	//var searchCondition = "#" + fieldId;
	//return $(searchCondition).find(':selected').val();
	return getSelectedValue(fieldId);

}


//Search for input with the Id and get the value
function getHiddenFpType(fieldId) {
	//var searchCondition = "input:radio[name='"+fieldId+"']:checked";
	//var searchCondition = "input[name='" + fieldId + "']";
	//alert($('input:radio[name='+fieldId+']:checked').val());
	//alert($(searchCondition).val());
	return $('input:radio[name='+fieldId+']:checked').val();

}

function setHiddenFpType(fieldId1, fieldId2, appletId, isQcA) {
	//var searchCondition = "input:radio[name='"+fieldId+"']:checked";
	//var searchCondition = "input[name='" + fieldId + "']";
	//alert($('input:radio[name='+fieldId+']:checked').val());
	//alert($(searchCondition).val());
	//return $('input:radio[name='+fieldId+']:checked').val();
	//alert(fieldId1);
	//alert(fieldId2);
	//alert(isQcA);
	//alert(!isQcA);
	if(appletId=="1"){
		updateRadioFpType(fieldId1, !isQcA, isQcA, false, false);
	}else if(appletId=="2"){
		updateRadioFpType(fieldId1, !isQcA, isQcA, false, false);
	}else if(appletId=="3"){
		updateRadioFpType(fieldId2, !isQcA, isQcA, false, false);
	}else if(appletId=="4"){
		updateRadioFpType(fieldId2, !isQcA, isQcA, false, false);
	}

}


function getFileMatchScore(fieldName){
	return getLabel(fieldName);
}

function getFileMatchResult(fieldName){
	return getInputValue(fieldName);
}

function captureNewFingerprintDone(captureStatus1, captureStatus2,
		captureStatus3, captureStatus4, minutiaCount, patternType,
		minutiaQuality, appletID, matchScore, matchResult, action, finger,
		myForm) {

	// ***********************************************************************************************************************************************
	// To display which fingerprint has been captured
	if (captureStatus1 == "true") {
		updateFpCapturedStatus("captureStatus1", captureStatus1);
	}
	if (captureStatus2 == "true") {
		updateFpCapturedStatus("captureStatus2", captureStatus2);
	}
	if (captureStatus3 == "true") {
		updateFpCapturedStatus("captureStatus3", captureStatus3);
	}
	if (captureStatus4 == "true") {
		updateFpCapturedStatus("captureStatus4", captureStatus4);
	}
	// alert("captureStatus1: "+captureStatus1 + " captureStatus2: " +
	// captureStatus2 + " captureStatus3: " + captureStatus3 + " captureStatus4:
	// " + captureStatus4);

	// To display the result of the fingerprint captured
	if (appletID == "1") {
		updateLabel("minutiaCount1", minutiaCount);
		updateLabel("minutiaQuality1", minutiaQuality);
	} else if (appletID == "2") {
		updateLabel("minutiaCount2", minutiaCount);
		updateLabel("minutiaQuality2", minutiaQuality);
	} else if (appletID == "3") {
		updateLabel("minutiaCount3", minutiaCount);
		updateLabel("minutiaQuality3", minutiaQuality);
	} else if (appletID == "4") {
		updateLabel("minutiaCount4", minutiaCount);
		updateLabel("minutiaQuality4", minutiaQuality);
	}
	// *************************************************************************************************************************************************************
	// To display the relevant fingerprint information for fingerprint applet ID
	// 1 and 2 only
	if ((appletID == "1") || (appletID == "2")) {
		// *************************************************************************************************************************************************************
		// To display the QC score
		if ((captureStatus1 == "true") && (captureStatus2 == "true")) {
			updateLabel("qcMatchScore1", matchScore);
			updateLabel("qcMatchResult1", matchResult);
		}
		// *************************************************************************************************************************************************************
		// To display the pattern of the fingerprint for the first captured
		// fingerprint only
		if (((captureStatus1 == "true") && (captureStatus2 == "false"))
				|| ((captureStatus1 == "false") && (captureStatus2 == "true"))) {
			selectSelectValue("selFPPattern1", patternType);

		}

		// *************************************************************************************************************************************************************
		// To display the relevant fingerprint information for fingerprint
		// applet ID 3 and 4 only
	} else if ((appletID == "3") || (appletID == "4")) {
		// *************************************************************************************************************************************************************
		// To display the QC score
		if ((captureStatus3 == "true") && (captureStatus4 == "true")) {
			updateLabel("qcMatchScore2", matchScore);
			updateLabel("qcMatchResult2", matchResult);
		}

		// *************************************************************************************************************************************************************
		// To display the pattern of the fingerprint for the first captured
		// fingerprint only
		if (((captureStatus3 == "true") && (captureStatus4 == "false"))
				|| ((captureStatus3 == "false") && (captureStatus4 == "true"))) {
			selectSelectValue("selFPPattern2", patternType);
		}

	}
	// ********************************************************************************************************************************************
	// Display QC Result and Finger Number if it is downloaded from the database
	if ((appletID == "1") || (appletID == "2")) {
		// Display Finger Number if it is downloaded from the database
		if (action == "download") {
			selectSelectValue("selFPNo1", finger);
			// Added on 20110221
			selectSelectValue("selFPPattern1", patternType);
		}
		// Display QC Result if it is downloaded from the database
		// displayFingerprintQC1Result(myForm, matchScore, matchResult);
	} else if ((appletID == "3") || (appletID == "4")) {
		// Display Finger Number if it is downloaded from the database
		if (action == "download") {
			selectSelectValue("selFPNo2", finger);
			// Added on 20110221
			selectSelectValue("selFPPattern2", patternType);
		}
		// Display QC Result if it is downloaded from the database
		// displayFingerprintQC2Result(myForm, matchScore, matchResult);
	}
}



//FileFPApplet
function fileFingerprintDone(appletID, finger, patternType, minutiaCount,
		minutiaQuality, matchScore, fileMatchScore, qcMatchResult,
		fileMatchResult, remark, myForm) {

	if (appletID == "0") {
		updateLabel("file_finger", finger);
		updateLabel("file_pattern", patternType);
		updateLabel("file_minutia_count", minutiaCount);
		updateLabel("file_minutia_quality", minutiaQuality);
	} else if (appletID == "a") {
		updateLabel("file_finger1a", finger);
		updateLabel("file_pattern1a", patternType);
		updateLabel("file_minutia_count1a", minutiaCount);
		updateLabel("file_minutia_quality1a", minutiaQuality);
	} else if (appletID == "f") {
		updateLabel("file_finger", finger);
		updateLabel("file_pattern", patternType);
		updateLabel("file_minutia_count", minutiaCount);
		updateLabel("file_minutia_quality", minutiaQuality);
	}
}

function fingerprintAppletError(errorCode, errorString) {
	alert(errorString);
}


/**
 * Determine if verification of Fingerprint is required based on the
 * No Finger indicator and Whether the Finger has previous generation
 * @param hidHasFingerForPrevGen1 - Whether the finger 1 has previous generation
 * @param hidNoFp1 - Whether the finger 1 has finger
 * @param hidHasFingerForPrevGen2 - Whether the finger 2 has previous generation
 * @param hidNoFp2 - Whether the finger 2 has finger
 * @return "true" if verification is required, otherwise "false"
 */
function isVerifyFingerprintRequired(hidHasFingerForPrevGen1, hidNoFp1, hidHasFingerForPrevGen2, hidNoFp2 ){
	// Scenario 1 No finger but has previous fingerprint, need to verify
	if((hidHasFingerForPrevGen1 == "true")
			&& (hidNoFp1 == "true")){
		return "true";
	}

	if((hidHasFingerForPrevGen2 == "true")
			&& (hidNoFp2 == "true")){
		//Need to verify fingerprint 2 for Scenario 1.
		return "true";
	}

	// Scenario 2 Has finger and no previous fingerprint, need to verify
	if((hidHasFingerForPrevGen1 == "false")
			&& (hidNoFp1 == "false")){
		//Need to verify fingerprint 1 for Scenario 2.
		return "true";
	}
	if((hidHasFingerForPrevGen2 == "false")
			&& (hidNoFp2 == "false")){
		//Need to verify fingerprint 2 for Scenario 2.
		return "true";
	}

	// Scenario 3 No finger and no previous fingerprint, no need to verify
	if((hidHasFingerForPrevGen1 == "false") 
			&& (hidNoFp1 == "true")){
		//No need to verify fingerprint 1 for Scenario 3.
		return "false";
	}
	if((hidHasFingerForPrevGen2 == "false") 
			&& (hidNoFp2 == "true")){
		//No need to verify fingerprint 2 for Scenario 3.
		return "false";
	}
	
	//default case
	//Added by xueyan_huang
	return "true";
}

function isVerifyFingerprintRequired_New(hidHasFingerForPrevGen1, hidNoFp1, hidHasFingerForPrevGen2, hidNoFp2 ){
	var verifyFp1Required = true;
	var verifyFp2Required = true;
	
	// Scenario 1 No finger but has previous fingerprint, need to verify
	if((hidHasFingerForPrevGen1 == "true")
			&& (hidNoFp1 == "true")){
		verifyFp1Required = true;
	}

	if((hidHasFingerForPrevGen2 == "true")
			&& (hidNoFp2 == "true")){
		//Need to verify fingerprint 2 for Scenario 1.
		verifyFp2Required = true;
	}

	// Scenario 2 Has finger and no previous fingerprint, need to verify
	if((hidHasFingerForPrevGen1 == "false")
			&& (hidNoFp1 == "false")){
		//Need to verify fingerprint 1 for Scenario 2.
		verifyFp1Required = true;
	}
	if((hidHasFingerForPrevGen2 == "false")
			&& (hidNoFp2 == "false")){
		//Need to verify fingerprint 2 for Scenario 2.
		verifyFp2Required = true;
	}

	// Scenario 4 Has finger and previous fingerprint, need to verify
	if((hidHasFingerForPrevGen1 == "true")
			&& (hidNoFp1 == "false")){
		verifyFp1Required = true;
	}

	if((hidHasFingerForPrevGen2 == "true")
			&& (hidNoFp2 == "false")){
		//Need to verify fingerprint 2 for Scenario 1.
		verifyFp2Required = true;
	}
	
	// Scenario 3 No finger and no previous fingerprint, no need to verify
	if((hidHasFingerForPrevGen1 == "false") 
			&& (hidNoFp1 == "true")){
		//No need to verify fingerprint 1 for Scenario 3.
		verifyFp1Required = false;
	}
	if((hidHasFingerForPrevGen2 == "false") 
			&& (hidNoFp2 == "true")){
		//No need to verify fingerprint 2 for Scenario 3.
		verifyFp2Required = false;
	}

	if(verifyFp1Required == true || verifyFp2Required == true){
		return "true";
	}
	else{
		return "false";
	}
}

/**
 * Check if verification is required for a finger
 */
function isVerifyFingerprintXRequired_New(hidHasFingerForPrevGen1, hidNoFp1){
	// Scenario 1 No finger but has previous fingerprint, need to verify
	if((hidHasFingerForPrevGen1 == "true")
			&& (hidNoFp1 == "true")){
		return "true";
	}
	// Scenario 2 Has finger and no previous fingerprint, need to verify
	if((hidHasFingerForPrevGen1 == "false")
			&& (hidNoFp1 == "false")){
		//Need to verify fingerprint 1 for Scenario 2.
		return "true";
	}

	// Scenario 4 Has finger and previous fingerprint, need to verify
	if((hidHasFingerForPrevGen1 == "true")
			&& (hidNoFp1 == "false")){
		return "true";
	}
	
	// Scenario 3 No finger and no previous fingerprint, no need to verify
	if((hidHasFingerForPrevGen1 == "false") 
			&& (hidNoFp1 == "true")){
		//No need to verify fingerprint 1 for Scenario 3.
		return "false";
	}	
	
	//default case
	//Added by xueyan_huang
	return "true";
}
/**
 * Update the hidden fields that keep track of which finger no has been captured.
 * If finger no is not capture, no change to the field
 * @param captureStatus1 "true" indicate finger 1 has been captured
 * @param captureStatus2 "true" indicate finger 2 has been captured
 * @param captureStatus3 "true" indicate finger 3 has been captured
 * @param captureStatus4 "true" indicate finger 4 has been captured
 */
function updateFpCapturedFieldsStatus(captureStatus1, captureStatus2, captureStatus3, captureStatus4){
	if (captureStatus1 == "true") {
		updateFpCapturedStatus("captureStatus1", captureStatus1);
	}

	if (captureStatus2 == "true") {
		updateFpCapturedStatus("captureStatus2", captureStatus2);
	}

	if (captureStatus3 == "true") {
		updateFpCapturedStatus("captureStatus3", captureStatus3);
	}

	if (captureStatus4 == "true") {
		updateFpCapturedStatus("captureStatus4", captureStatus4);
	}
}

/**
 * Update the fields related to the applet id. 
 * Caller needs to pass in the correct fields for different applet id.
 * @param appletID - current not used, kept for future usage
 * @param minutiaCount
 * @param minutiaQuality
 * @param fileMatchScore
 * @param fileMatchResult
 * @param minutiaCountField
 * @param minutiaQualityField
 * @param fileMatchScoreField
 * @param fileMatchResultField
 * @param idFileMatchResultPassField
 * @param idFileMatchResultFailField
 * @param hidHasFingerForPrevGen
 * @return
 */
function updateFingerprintCapturedAppletFields(appletID, minutiaCount, minutiaQuality, fileMatchScore, fileMatchResult
		, minutiaCountField, minutiaQualityField, fileMatchScoreField, fileMatchResultField
		, idFileMatchResultPassField, idFileMatchResultFailField, hidHasFingerForPrevGen){
	
		updateLabel(minutiaCountField, minutiaCount);
		updateLabel(minutiaQualityField, minutiaQuality);
		updateLabel(fileMatchScoreField, fileMatchScore);
		updateInputValue(fileMatchResultField, fileMatchResult);
		if (fileMatchResult == "FAIL") {
			hideLabel(idFileMatchResultPassField);
			showLabel(idFileMatchResultFailField);
			updateLabel(idFileMatchResultFailField, fileMatchResult);
		} else if (fileMatchResult == "PASS") {
			hideLabel(idFileMatchResultFailField);
			showLabel(idFileMatchResultPassField);
			updateLabel(idFileMatchResultPassField, fileMatchResult);
		} else {			
			if (hidHasFingerForPrevGen == "true") {
				//alert("File Match is set to PASS as it has fingerprint for previous generation 1");
				hideLabel(idFileMatchResultFailField);
				showLabel(idFileMatchResultPassField);
				updateLabel(idFileMatchResultPassField, "PASS");
				fileMatchResult = "PASS";
				updateInputValue(fileMatchResultField, fileMatchResult);
			} else {
				//alert("File Match is set to FAIL as it has fingerprint for previous generation 1");
				hideLabel(idFileMatchResultPassField);
				showLabel(idFileMatchResultFailField);
				updateLabel(idFileMatchResultFailField, "FAIL");
				fileMatchResult = "FAIL";
				updateInputValue(fileMatchResultField, fileMatchResult);
			}
		}

}

/**
 * Update the Qc fields for the two fingers.
 * Caller needs to pass in the correct fields for the different fingers.
 * @param captureStatus1
 * @param captureStatus2
 * @param matchScore
 * @param matchResult
 * @param qcMatchScoreField
 * @param idQcMatchResult1PassField
 * @param idQcMatchResult1FailField
 * @return
 */
function updateQcScoreFields(captureStatus1, captureStatus2, matchScore, matchResult, qcMatchScoreField, 
		idQcMatchResult1PassField, idQcMatchResult1FailField){
	if ((captureStatus1 == "true") && (captureStatus2 == "true")) {
		//alert("Fingerprint for Fingerprint Applet 1 and 2 has been captured.");
		updateLabel(qcMatchScoreField, matchScore);
		if (matchResult == "FAIL") {
			//alert("Match Fail 1");
			hideLabel(idQcMatchResult1PassField);
			showLabel(idQcMatchResult1FailField);
			updateLabel(idQcMatchResult1FailField, matchResult);
		} else {
			//alert("Match Pass 1");
			hideLabel(idQcMatchResult1FailField);
			showLabel(idQcMatchResult1PassField);
			updateLabel(idQcMatchResult1PassField, matchResult);
		}
	}
}

/**
 * Update the storeDB radio button
 * @param radioFpTypeField
 * @param radio1Checked
 * @param radio2Checked
 * @param radio1Disabled
 * @param radio2Disabled
 * @return
 */
function updateRadioFpType(radioFpTypeField, radio1Checked, radio2Checked, radio1Disabled, radio2Disabled){
	$('input:radio[name='+radioFpTypeField+']:nth(0)').attr('checked',radio1Checked);
	$('input:radio[name='+radioFpTypeField+']:nth(1)').attr('checked',radio2Checked);
	$('input:radio[name='+radioFpTypeField+']:nth(0)').attr("disabled", radio1Disabled);
	$('input:radio[name='+radioFpTypeField+']:nth(1)').attr("disabled", radio2Disabled);
}

function updateRadioFpTypeDisableStatus(radioFpTypeField, radio1Disabled, radio2Disabled){
	$('input:radio[name='+radioFpTypeField+']:nth(0)').attr("disabled", radio1Disabled);
	$('input:radio[name='+radioFpTypeField+']:nth(1)').attr("disabled", radio2Disabled);
}

function captureFingerprintDone(captureStatus1, captureStatus2, captureStatus3,
		captureStatus4, minutiaCount, patternType, minutiaQuality, appletID,
		matchScore, matchResult, action, finger, fileMatchScore,
		fileMatchResult, myForm) {
	// To determine whether to show the verification fingerprint or not

	var showVerifyFingerprint = "false";

	//***********************************************************************************************************************************************
	// To update which fingerprint has been captured
	updateFpCapturedFieldsStatus(captureStatus1, captureStatus2, captureStatus3, captureStatus4);
	
	//**************************************************************************************************************************************
	// To display the result of the fingerprint captured
	if (appletID == "1") {
		updateFingerprintCapturedAppletFields(appletID, minutiaCount, minutiaQuality, fileMatchScore, fileMatchResult
				, "minutiaCount1", "minutiaQuality1", "file_match_score1", "file_match_result1"
				, "idFileMatchResult1Pass", "idFileMatchResult1Fail", getInputValueLowerCase("hidHasFingerForPrevGen1"));
		
	} else if (appletID == "2") {
		updateFingerprintCapturedAppletFields(appletID, minutiaCount, minutiaQuality, fileMatchScore, fileMatchResult
				, "minutiaCount2", "minutiaQuality2", "file_match_score2", "file_match_result2"
				, "idFileMatchResult2Pass", "idFileMatchResult2Fail", getInputValueLowerCase("hidHasFingerForPrevGen1"));
	} else if (appletID == "3") {
		updateFingerprintCapturedAppletFields(appletID, minutiaCount, minutiaQuality, fileMatchScore, fileMatchResult
				, "minutiaCount3", "minutiaQuality3", "file_match_score3", "file_match_result3"
				, "idFileMatchResult3Pass", "idFileMatchResult3Fail", getInputValueLowerCase("hidHasFingerForPrevGen2"));
	} else if (appletID == "4") {
		updateFingerprintCapturedAppletFields(appletID, minutiaCount, minutiaQuality, fileMatchScore, fileMatchResult
				, "minutiaCount4", "minutiaQuality4", "file_match_score4", "file_match_result4"
				, "idFileMatchResult4Pass", "idFileMatchResult4Fail", getInputValueLowerCase("hidHasFingerForPrevGen2"));
	}

	//*************************************************************************************************************************************************************
	// To display the relevant fingerprint information for fingerprint applet ID 1 and 2 only
	if ((appletID == "1") || (appletID == "2")) {
		//*************************************************************************************************************************************************************
		// To display the QC score
		updateQcScoreFields(captureStatus1, captureStatus2, matchScore, matchResult, "qcMatchScore1", 
				"idQcMatchResult1Pass", "idQcMatchResult1Fail");
			

		//*************************************************************************************************************************************************************
		// To display the pattern of the fingerprint for the first captured fingerprint only
		if (((captureStatus1 == "true") && (captureStatus2 == "false"))
				|| ((captureStatus1 == "false") && (captureStatus2 == "true"))) {
			selectSelectValue("selFPPattern1", patternType);
		}
		//*************************************************************************************************************************************************************
		// To perform verification only once before the "confirm" has been clicked		
		if (action == "download") {
			
			// FpVerify is false but (matchscore1 & matchscore2 fails) or (matchscore 3 & matchscore4 fails)
			if (getInputValue("hidFpVerify").toLowerCase()  == "false"
			&& ((getInputValue("file_match_result1")  == "FAIL" && getInputValue("file_match_result2") == "FAIL") || 
					(getInputValue("file_match_result3")== "FAIL" && getInputValue("file_match_result4") == "FAIL"))) {
				//alert("Should verify fingerprint 1");
//				showVerifyFingerprint = "true";
				
				showVerifyFingerprint = isVerifyFingerprintRequired_New(getInputValueLowerCase("hidHasFingerForPrevGen1"),getInputValue("hidNoFp1"),getInputValueLowerCase("hidHasFingerForPrevGen2"),getInputValue("hidNoFp2"));				
			}
		} else {
			// FpVerify is false but (all fingerprint are captured)
//			var currentHidFpVerify = getInputValue("hidFpVerify").toLowerCase();
//			if (getInputValue("hidFpVerify").toLowerCase() == "false"
//					&& (captureStatus1 == "true"
//							&& captureStatus2 == "true"
//							&& captureStatus3 == "true" && captureStatus4 == "true")) {
//				//Should verify fingerprint 2
////				showVerifyFingerprint = "true";
//				
//				showVerifyFingerprint = isVerifyFingerprintRequired_New(getInputValueLowerCase("hidHasFingerForPrevGen1"),getInputValue("hidNoFp1"),getInputValueLowerCase("hidHasFingerForPrevGen2"),getInputValue("hidNoFp2"));
//				
//				
//			}
//			
//			if(getInputValue("hidFpVerify").toLowerCase() == "false" &&
//					(captureStatus1 == "true"
//						&& captureStatus2 == "true") &&
//						getInputValue("hidNoFp2")=="true"){
//				showVerifyFingerprint = isVerifyFingerprintRequired_New(getInputValueLowerCase("hidHasFingerForPrevGen1"),getInputValue("hidNoFp1"),getInputValueLowerCase("hidHasFingerForPrevGen2"),getInputValue("hidNoFp2"));
//			}
			showVerifyFingerprint = isVerifyFingerprintRequired_New(getInputValueLowerCase("hidHasFingerForPrevGen1"),getInputValue("hidNoFp1"),getInputValueLowerCase("hidHasFingerForPrevGen2"),getInputValue("hidNoFp2"));
		}
		//*************************************************************************************************************************************************************
		// To perform verification
		if (showVerifyFingerprint == "true") {
			determineFpVerification(appletID, showVerifyFingerprint, fileMatchResult);
		}
	}

	//*************************************************************************************************************************************************************
	// To display the relevant fingerprint information for fingerprint applet ID 3 and 4 only
	if ((appletID == "3") || (appletID == "4")) {
		//*************************************************************************************************************************************************************
		// To display the QC score
		updateQcScoreFields(captureStatus3, captureStatus4, matchScore, matchResult, "qcMatchScore2", 
				"idQcMatchResult2Pass", "idQcMatchResult2Fail");

		//*************************************************************************************************************************************************************
		// To display the pattern of the fingerprint for the first captured fingerprint only
		if (((captureStatus3 == "true") && (captureStatus4 == "false"))
				|| ((captureStatus3 == "false") && (captureStatus4 == "true"))) {
			selectSelectValue("selFPPattern2", patternType);
		}

		//*************************************************************************************************************************************************************
		// To perform verification only once before the "confirm" has been clicked
		 if (action == "download") {
	          if ((getInputValue("hidFpVerify").toLowerCase() == "false")&&
	             (( (getInputValue("file_match_result1") == "FAIL")&&
	             (getInputValue("file_match_result2") == "FAIL"))||
	             ((getInputValue("file_match_result3") == "FAIL")&&
	             (getInputValue("file_match_result4") == "FAIL")))){
				//Should verify fingerprint 4"
//				showVerifyFingerprint = "true";

				showVerifyFingerprint = isVerifyFingerprintRequired_New(getInputValueLowerCase("hidHasFingerForPrevGen1"),getInputValue("hidNoFp1"),getInputValueLowerCase("hidHasFingerForPrevGen2"),getInputValue("hidNoFp2"));
			}
		} else {
			
//			if ((getInputValue("hidFpVerify").toLowerCase() == "false")
//					&& ((captureStatus1 == "true")
//							&& (captureStatus2 == "true")
//							&& (captureStatus3 == "true") && (captureStatus4 == "true"))) {
//				//alert("Should verify fingerprint 5");
////				showVerifyFingerprint = "true";
//				
//				showVerifyFingerprint = isVerifyFingerprintRequired_New(getInputValueLowerCase("hidHasFingerForPrevGen1"),getInputValue("hidNoFp1"),getInputValueLowerCase("hidHasFingerForPrevGen2"),getInputValue("hidNoFp2"));
//			}
			showVerifyFingerprint = isVerifyFingerprintRequired_New(getInputValueLowerCase("hidHasFingerForPrevGen1"),getInputValue("hidNoFp1"),getInputValueLowerCase("hidHasFingerForPrevGen2"),getInputValue("hidNoFp2"));
		}

		//*************************************************************************************************************************************************************
		// To perform verification
		if (showVerifyFingerprint == "true") {
			determineFpVerification(appletID, showVerifyFingerprint, fileMatchResult);
		}
	}

	//********************************************************************************************************************************************
	// Display QC Result and Finger Number if it is downloaded from the database
	if ((appletID == "1") || (appletID == "2")) {
		// Display Finger Number if it is downloaded from the database
		if (action == "download") {
			selectSelectValue("selFPNo1", finger);
		}

		// Display QC Result if it is downloaded from the database
		displayFingerprintQC1Result(myForm, matchScore, matchResult);
	} else if ((appletID == "3") || (appletID == "4")) {
		// Display Finger Number if it is downloaded from the database
		if (action == "download") {
			selectSelectValue("selFPNo2", finger);
		}

		// Display QC Result if it is downloaded from the database
		displayFingerprintQC2Result(myForm, matchScore, matchResult);
	}
}

function isMandatoryVerificationRequired(fingerNo){
	if(fingerNo == "1"){
		if(getInputValueLowerCase("hidHasFingerForPrevGen1") == "true" && getInputValue("hidNoFp1") == "true"){
			return true;
		}
		else if(getInputValueLowerCase("hidHasFingerForPrevGen1") == "false" && getInputValue("hidNoFp1") == "false"){
			return true;
		}
	}
	else if(fingerNo == "2"){
		if(getInputValueLowerCase("hidHasFingerForPrevGen2") == "true" && getInputValue("hidNoFp2") == "true"){
			return true;
		}
		else if(getInputValueLowerCase("hidHasFingerForPrevGen2") == "false" && getInputValue("hidNoFp2") == "false"){
			return true;
		}
	}
	return false;
}
/**
 * Determine if the Team Leader Verify is to be displayed
 * @param appletID - applet Id
 * @param showVerifyFingerprint -  whether verification is needed
 * @param fileMatchResult - file match result for the applet id
 * @return
 */
function determineFpVerification(appletID, showVerifyFingerprint, fileMatchResult){
//	if (showVerifyFingerprint == "true") {
		// Check for change in Fp status from X-N, N-Y, Y-N
		// These cases must display the verification button
		
		if (appletID == "1") {
			// No Finger
			if(getInputValue('hidNoFp1') == 'true'){
				// Has previous fingerprint
				if(getInputValueLowerCase("hidHasFingerForPrevGen1") == 'true'){
					// Must have verification because of Fp status change
					$('#idConfirmFP').show();
				}else{
					// No previous fingerprint
					// Do nothing
					// If finger 2 verification is not mandatory and fp or Qcfp passes, hide
					if(!isMandatoryVerificationRequired("2")){
						if(getInputValue('hidNoFp2') == 'true'){
							$('#idConfirmFP').hide();
						}
						else{
							if(getInputValue("file_match_result3") == "PASS" || getInputValue("file_match_result4") == "PASS"){
								$('#idConfirmFP').hide();
							}
						}
					}
				}
			}else{
				// Capturing fingerprint
				
				// Does not have previous fingerprint
				if(getInputValueLowerCase("hidHasFingerForPrevGen1") == 'false'){
					// Must have verification because of Fp status change
					$('#idConfirmFP').show();
				}else{// Has previous fingerprint and capturing fingerprint
					// Depends on verification result
					
					if (fileMatchResult == "FAIL") {
						if (getInputValue("file_match_result2") == "FAIL") {
							// Need to perform verification only if both fingerprint fail
							$('#idConfirmFP').show();
							// The failed fingerprint cannot be the actual file print
							updateRadioFpTypeDisableStatus("hidFpType1", false, false);
							
						} else if (getInputValue("file_match_result2") == "PASS") {
							// The failed fingerprint cannot be the actual file print
							// Checked the passed fp, disable the other fp
							updateRadioFpType("hidFpType1", false, true, true, false);
							// If finger 2 verification is not mandatory and fp or Qcfp passes, hide
							if(!isMandatoryVerificationRequired("2")){
								if(getInputValue('hidNoFp2') == 'true'){
									$('#idConfirmFP').hide();
								}
								else{
									if(getInputValue("file_match_result3") == "PASS" || getInputValue("file_match_result4") == "PASS"){
										$('#idConfirmFP').hide();
									}
								}
							}
							
						}
					}else if (fileMatchResult == "PASS"){ // fileMatchResult == "PASS"
						if (getInputValue("file_match_result2") == "FAIL") {
							// The failed fingerprint cannot be the actual file print
							// Checked the passed fp, disable the other fp
							updateRadioFpType("hidFpType1", true, false, false, true);
							// If finger 2 verification is not mandatory and fp or Qcfp passes, hide
							if(!isMandatoryVerificationRequired("2")){
								if(getInputValue('hidNoFp2') == 'true'){
									$('#idConfirmFP').hide();
								}
								else{
									if(getInputValue("file_match_result3") == "PASS" || getInputValue("file_match_result4") == "PASS"){
										$('#idConfirmFP').hide();
									}
								}
							}
						}else if (getInputValue("file_match_result2") == "PASS") {
							// If finger 2 verification is not mandatory and fp or Qcfp passes, hide
							if(!isMandatoryVerificationRequired("2")){
								if(getInputValue('hidNoFp2') == 'true'){
									$('#idConfirmFP').hide();
								}
								else{
									if(getInputValue("file_match_result3") == "PASS" || getInputValue("file_match_result4") == "PASS"){
										$('#idConfirmFP').hide();
									}
								}
							}
						}
					}else{ // fileMatchResult == "" disable FP
						// If finger 2 verification is not mandatory and fp or Qcfp passes, hide
						if(!isMandatoryVerificationRequired("2") &&
								(getInputValue("file_match_result3") == "PASS" || getInputValue("file_match_result4") == "PASS")){
							$('#idConfirmFP').hide();
						}
					}
				}
				
			}
		}else if (appletID == "2") {
			// No Finger
			if(getInputValue('hidNoFp1') == 'true'){
				// Has previous fingerprint
				if(getInputValueLowerCase("hidHasFingerForPrevGen1") == 'true'){
					// Must have verification because of Fp status change
					$('#idConfirmFP').show();
				}else{
					// No previous fingerprint
					// Do nothing
					// If finger 2 verification is not mandatory and fp or Qcfp passes, hide
					if(!isMandatoryVerificationRequired("2")){
						if(getInputValue('hidNoFp2') == 'true'){
							$('#idConfirmFP').hide();
						}
						else{
							if(getInputValue("file_match_result3") == "PASS" || getInputValue("file_match_result4") == "PASS"){
								$('#idConfirmFP').hide();
							}
						}
					}
				}
			}else{
				// Capturing fingerprint
				
				// Does not have previous fingerprint
				if(getInputValueLowerCase("hidHasFingerForPrevGen1") == 'false'){
					// Must have verification because of Fp status change
					$('#idConfirmFP').show();
				}else{// Has previous fingerprint and capturing fingerprint
					// Depends on verification result
					
					if (fileMatchResult == "FAIL") {
						if (getInputValue("file_match_result1") == "FAIL") {
							// Need to perform verification only if both fingerprint fail
							$('#idConfirmFP').show();
							// The failed fingerprint cannot be the actual file print
							updateRadioFpTypeDisableStatus("hidFpType1", false, false);
							
						} else if (getInputValue("file_match_result1") == "PASS") {
							// The failed fingerprint cannot be the actual file print
							// Checked the passed fp, disable the other fp
							updateRadioFpType("hidFpType1", true, false, false, true);
							// If finger 2 verification is not mandatory and fp or Qcfp passes, hide
							if(!isMandatoryVerificationRequired("2")){
								if(getInputValue('hidNoFp2') == 'true'){
									$('#idConfirmFP').hide();
								}
								else{
									if(getInputValue("file_match_result3") == "PASS" || getInputValue("file_match_result4") == "PASS"){
										$('#idConfirmFP').hide();
									}
								}
							}
							
						}
					}else if(fileMatchResult == "PASS"){ // fileMatchResult == "PASS"
						if (getInputValue("file_match_result1") == "FAIL") {
							// The failed fingerprint cannot be the actual file print
							// Checked the passed fp, disable the other fp
							updateRadioFpType("hidFpType1", false, true, true, false);
							// If finger 2 verification is not mandatory and fp or Qcfp passes, hide
							if(!isMandatoryVerificationRequired("2")){
								if(getInputValue('hidNoFp2') == 'true'){
									$('#idConfirmFP').hide();
								}
								else{
									if(getInputValue("file_match_result3") == "PASS" || getInputValue("file_match_result4") == "PASS"){
										$('#idConfirmFP').hide();
									}
								}
							}
						}else if (getInputValue("file_match_result1") == "PASS") {
							// If finger 2 verification is not mandatory and fp or Qcfp passes, hide
							// If finger 2 verification is not mandatory and fp or Qcfp passes, hide
							if(!isMandatoryVerificationRequired("2")){
								if(getInputValue('hidNoFp2') == 'true'){
									$('#idConfirmFP').hide();
								}
								else{
									if(getInputValue("file_match_result3") == "PASS" || getInputValue("file_match_result4") == "PASS"){
										$('#idConfirmFP').hide();
									}
								}
							}
						}
					}else{ // fileMatchResult == "" disable FP
						// If finger 2 verification is not mandatory and fp or Qcfp passes, hide
						if(!isMandatoryVerificationRequired("2") &&
								(getInputValue("file_match_result3") == "PASS" || getInputValue("file_match_result4") == "PASS")){
							$('#idConfirmFP').hide();
						}
					}
				}
				
			}
		}else if (appletID == "3") {
			// No Finger
			if(getInputValue('hidNoFp2') == 'true'){
				// Has previous fingerprint
				if(getInputValueLowerCase("hidHasFingerForPrevGen2") == 'true'){
					// Must have verification because of Fp status change
					$('#idConfirmFP').show();
				}else{
					// No previous fingerprint
					// Do nothing
					// If finger 1 verification is not mandatory and fp or Qcfp passes, hide
					if(!isMandatoryVerificationRequired("1")){
						if(getInputValue('hidNoFp1') == 'true'){
							$('#idConfirmFP').hide();
						}
						else{
							if(getInputValue("file_match_result1") == "PASS" || getInputValue("file_match_result2") == "PASS"){
								$('#idConfirmFP').hide();
							}
						}
					}
				}
			}else{
				// Capturing fingerprint
				
				// Does not have previous fingerprint
				if(getInputValueLowerCase("hidHasFingerForPrevGen2") == 'false'){
					// Must have verification because of Fp status change
					$('#idConfirmFP').show();
				}else{// Has previous fingerprint and capturing fingerprint
					// Depends on verification result
					
					if (fileMatchResult == "FAIL") {
						if (getInputValue("file_match_result4") == "FAIL") {
							// Need to perform verification only if both fingerprint fail
							$('#idConfirmFP').show();
							// The failed fingerprint cannot be the actual file print
							updateRadioFpTypeDisableStatus("hidFpType2", false, false);
							
						} else if (getInputValue("file_match_result4") == "PASS") {
							// The failed fingerprint cannot be the actual file print
							// Checked the passed fp, disable the other fp
							updateRadioFpType("hidFpType2", false, true, true, false);
							// If finger 1 verification is not mandatory and fp or Qcfp passes, hide
							if(!isMandatoryVerificationRequired("1")){
								if(getInputValue('hidNoFp1') == 'true'){
									$('#idConfirmFP').hide();
								}
								else{
									if(getInputValue("file_match_result1") == "PASS" || getInputValue("file_match_result2") == "PASS"){
										$('#idConfirmFP').hide();
									}
								}
							}
							
						}
					}else if(fileMatchResult == "PASS"){ // fileMatchResult == "PASS"
						if (getInputValue("file_match_result4") == "FAIL") {
							// The failed fingerprint cannot be the actual file print
							// Checked the passed fp, disable the other fp
							updateRadioFpType("hidFpType2", true, false, false, true);
							// If finger 1 verification is not mandatory and fp or Qcfp passes, hide
							if(!isMandatoryVerificationRequired("1")){
								if(getInputValue('hidNoFp1') == 'true'){
									$('#idConfirmFP').hide();
								}
								else{
									if(getInputValue("file_match_result1") == "PASS" || getInputValue("file_match_result2") == "PASS"){
										$('#idConfirmFP').hide();
									}
								}
							}
						}else if (getInputValue("file_match_result4") == "PASS") {
							// If finger 1 verification is not mandatory and fp or Qcfp passes, hide
							if(!isMandatoryVerificationRequired("1")){
								if(getInputValue('hidNoFp1') == 'true'){
									$('#idConfirmFP').hide();
								}
								else{
									if(getInputValue("file_match_result1") == "PASS" || getInputValue("file_match_result2") == "PASS"){
										$('#idConfirmFP').hide();
									}
								}
							}
						}
					}else{ // fileMatchResult == "" disable FP
						// If finger 2 verification is not mandatory and fp or Qcfp passes, hide
						if(!isMandatoryVerificationRequired("1") &&
								(getInputValue("file_match_result1") == "PASS" || getInputValue("file_match_result2") == "PASS")){
							$('#idConfirmFP').hide();
						}
					}
				}
				
			}
		}else if (appletID == "4") {
			// No Finger
			if(getInputValue('hidNoFp2') == 'true'){
				// Has previous fingerprint
				if(getInputValueLowerCase("hidHasFingerForPrevGen2") == 'true'){
					// Must have verification because of Fp status change
					$('#idConfirmFP').show();
				}else{
					// No previous fingerprint
					// Do nothing
					// If finger 1 verification is not mandatory and fp or Qcfp passes, hide
					if(!isMandatoryVerificationRequired("1")){
						if(getInputValue('hidNoFp1') == 'true'){
							$('#idConfirmFP').hide();
						}
						else{
							if(getInputValue("file_match_result1") == "PASS" || getInputValue("file_match_result2") == "PASS"){
								$('#idConfirmFP').hide();
							}
						}
					}
				}
			}else{
				// Capturing fingerprint
				
				// Does not have previous fingerprint
				if(getInputValueLowerCase("hidHasFingerForPrevGen2") == 'false'){
					// Must have verification because of Fp status change
					$('#idConfirmFP').show();
				}else{// Has previous fingerprint and capturing fingerprint
					// Depends on verification result
					
					if (fileMatchResult == "FAIL") {
						if (getInputValue("file_match_result3") == "FAIL") {
							// Need to perform verification only if both fingerprint fail
							$('#idConfirmFP').show();
							// The failed fingerprint cannot be the actual file print
							updateRadioFpTypeDisableStatus("hidFpType2", false, false);
							
						} else if (getInputValue("file_match_result3") == "PASS") {
							// The failed fingerprint cannot be the actual file print
							// Checked the passed fp, disable the other fp
							updateRadioFpType("hidFpType2", true, false, false, true);
							// If finger 1 verification is not mandatory and fp or Qcfp passes, hide
							if(!isMandatoryVerificationRequired("1")){
								if(getInputValue('hidNoFp1') == 'true'){
									$('#idConfirmFP').hide();
								}
								else{
									if(getInputValue("file_match_result1") == "PASS" || getInputValue("file_match_result2") == "PASS"){
										$('#idConfirmFP').hide();
									}
								}
							}
							
						}
					}else if(fileMatchResult == "PASS"){ // fileMatchResult == "PASS"
						if (getInputValue("file_match_result3") == "FAIL") {
							// The failed fingerprint cannot be the actual file print
							// Checked the passed fp, disable the other fp
							updateRadioFpType("hidFpType2", false, true, true, false);
							// If finger 1 verification is not mandatory and fp or Qcfp passes, hide
							if(!isMandatoryVerificationRequired("1")){
								if(getInputValue('hidNoFp1') == 'true'){
									$('#idConfirmFP').hide();
								}
								else{
									if(getInputValue("file_match_result1") == "PASS" || getInputValue("file_match_result2") == "PASS"){
										$('#idConfirmFP').hide();
									}
								}
							}
						}else if (getInputValue("file_match_result3") == "PASS") {
							// If finger 1 verification is not mandatory and fp or Qcfp passes, hide
							if(!isMandatoryVerificationRequired("1")){
								if(getInputValue('hidNoFp1') == 'true'){
									$('#idConfirmFP').hide();
								}
								else{
									if(getInputValue("file_match_result1") == "PASS" || getInputValue("file_match_result2") == "PASS"){
										$('#idConfirmFP').hide();
									}
								}
							}
						}
					}else{
						// If finger 1 verification is not mandatory and fp or Qcfp passes, hide
						if(!isMandatoryVerificationRequired("1") &&
								(getInputValue("file_match_result1") == "PASS" || getInputValue("file_match_result2") == "PASS")){
							$('#idConfirmFP').hide();
						}
					}
				}
				
			}
		}
//	}	
}


function displayFingerprintQC1Result(myForm, matchScore, matchResult) {

	//alert("Displaying QC Result 1 for applet 1:" + eval(myForm + ".captureStatus1.value") + " and applet 2:" + eval(myForm + ".captureStatus2.value") + " Form:" + myForm + " Match Score:" + matchScore + " Match Result:" + matchResult);
	if ( getFpCapturedStatus("captureStatus1") == 'true' && getFpCapturedStatus("captureStatus2") == 'true') {
		//alert("Displaying QC Result for Fingerprint Applet 1 and 2 BEGIN.");
		if (matchResult == "FAIL") {
			//alert("Displaying QC Result 1 -- Match Fail 1");
			hideLabel("idQcMatchResult1Pass");
			showLabel("idQcMatchResult1Fail");
			updateLabel("idQcMatchResult1Fail", matchResult);
		} else {
			//alert("Displaying QC Result 1 -- Match Pass 1");
			hideLabel("idQcMatchResult1Fail");
			showLabel("idQcMatchResult1Pass");
			updateLabel("idQcMatchResult1Pass", matchResult);
		}

		//alert("Displaying QC Result for Fingerprint Applet 1 and 2 END.");
		updateLabel("qcMatchScore1", matchScore);
		//eval(myForm + ".qcMatchResult1.value=matchResult");
	} else {
		//alert("Cannot Displaying QC Result for Fingerprint Applet 1 and 2.");
	}
}

//To display the QC score and result for first fingerprint-------------------------------------------------------------
function displayFingerprintQC2Result(myForm, matchScore, matchResult){

	//alert("Displaying QC Result 2 for applet 3:" + eval(myForm + ".captureStatus3.value") + " and applet 4:" + eval(myForm + ".captureStatus4.value") + " Form:" + myForm + " Match Score:" + matchScore + " Match Result:" + matchResult);
	if ( getFpCapturedStatus("captureStatus3") == 'true' && getFpCapturedStatus("captureStatus4") == 'true') {
		//alert("Displaying QC Result for Fingerprint Applet 3 and 4 BEGIN.");
		if (matchResult == "FAIL") {
			//alert("Displaying QC Result -- Match Fail 2");
			hideLabel("idQcMatchResult2Pass");
			showLabel("idQcMatchResult2Fail");
			updateLabel("idQcMatchResult2Fail", matchResult);
		} else {
			//alert("Displaying QC Result -- Match Pass 2");
			hideLabel("idQcMatchResult2Fail");
			showLabel("idQcMatchResult2Pass");
			updateLabel("idQcMatchResult2Pass", matchResult);
		}

		//alert("Displaying QC Result for Fingerprint Applet 3 and 4 END.");
		updateLabel("qcMatchScore2", matchScore);
		//eval(myForm + ".qcMatchResult2.value=matchResult");
	} else {
		//alert("Cannot Displaying QC Result for Fingerprint Applet 3 and 4.");
	}
}

function disableFP(fieldId, finger) {
	if (isChecked(fieldId)) {		
		if (finger == "1") {
			if($('#fp1').length >0){
				$('#fp1').get(0).disableFingerprint();
				clearFingerprintCapturedAppletFields("" , "minutiaCount1", "minutiaQuality1", 
						"file_match_score1", "file_match_result1", "idFileMatchResult1Pass",
						"idFileMatchResult1Fail","qcMatchScore1","qcMatchResult1","idQcMatchResult1Pass","idQcMatchResult1Fail");
			}
			else{
				alert("Applet fp1 is not loaded");
			}			
			if($('#fpQC1').length >0){
				$('#fpQC1').get(0).disableFingerprint();
				clearFingerprintCapturedAppletFields("" , "minutiaCount2", "minutiaQuality2", 
						"file_match_score2", "file_match_result2", "idFileMatchResult2Pass",
						"idFileMatchResult2Fail","qcMatchScore1","qcMatchResult1","idQcMatchResult1Pass","idQcMatchResult1Fail");
			}
			else{
				alert("Applet fpQC1 is not loaded");
			}
			disableSelect("selFPPattern1");
			disableSelect("selFPNo1");			
			$("input:radio[name='hidFpType1']:nth(0)").attr("disabled", true);
			$("input:radio[name='hidFpType1']:nth(1)").attr("disabled", true);
			updateInputValue("hidNoFp1","true");
			
			// If user disable the FP and has previous fingerprint, must show the Team Leader Verify section 
//			if(isVerifyFingerprintRequired_New(getInputValueLowerCase("hidHasFingerForPrevGen1"),getInputValue("hidNoFp1"),getInputValueLowerCase("hidHasFingerForPrevGen2"),getInputValue("hidNoFp2")) == "true" ){
//				//$('#idConfirmFP').show();
//				determineFpVerification("1", "true", getInputValue("file_match_result1"));
//				determineFpVerification("2", "true", getInputValue("file_match_result2"));
//			}			
			
			determineFpVerification("1", "true", "");
			determineFpVerification("2", "true", "");
			// clear the capture status
			updateFpCapturedStatus("captureStatus1", "false");
			updateFpCapturedStatus("captureStatus2", "false");
			
			
			
		} else if (finger == "2") {
			if($('#fp2').length >0){
				$('#fp2').get(0).disableFingerprint();
				clearFingerprintCapturedAppletFields("" , "minutiaCount3", "minutiaQuality3", 
						"file_match_score3", "file_match_result3", "idFileMatchResult3Pass",
						"idFileMatchResult3Fail","qcMatchScore2","qcMatchResult2","idQcMatchResult2Pass","idQcMatchResult2Fail");
			}
			else{
				alert("Applet fp2 is not loaded");
			}			
			if($('#fpQC2').length >0){
				$('#fpQC2').get(0).disableFingerprint();
				clearFingerprintCapturedAppletFields("" , "minutiaCount4", "minutiaQuality4", 
						"file_match_score4", "file_match_result4", "idFileMatchResult4Pass",
						"idFileMatchResult4Fail","qcMatchScore2","qcMatchResult2","idQcMatchResult2Pass","idQcMatchResult2Fail");
			}
			else{
				alert("Applet fpQC2 is not loaded");
			}
//			$('#fp2').get(0).disableFingerprint();
//			$('#fpQC2').get(0).disableFingerprint();
			disableSelect("selFPPattern2");
			disableSelect("selFPNo2");			
			$("input:radio[name='hidFpType2']:nth(0)").attr("disabled", true);
			$("input:radio[name='hidFpType2']:nth(1)").attr("disabled", true);
			updateInputValue("hidNoFp2","true");
			
			
			// If user disable the FP and has previous fingerprint, must show the Team Leader Verify section 
//			if(isVerifyFingerprintRequired_New(getInputValueLowerCase("hidHasFingerForPrevGen1"),getInputValue("hidNoFp1"),getInputValueLowerCase("hidHasFingerForPrevGen2"),getInputValue("hidNoFp2")) == "true" ){
//				//$('#idConfirmFP').show();
//				determineFpVerification("3", "true", getInputValue("file_match_result3"));
//				determineFpVerification("4", "true", getInputValue("file_match_result4"));
//			}
			
			determineFpVerification("3", "true", "");
			determineFpVerification("4", "true", "");
			// clear the capture status
			updateFpCapturedStatus("captureStatus3", "false");
			updateFpCapturedStatus("captureStatus4", "false");
		}
	} else {
		// Uncheck no finger
		if (finger == "1") {
			if($('#fp1').length >0){
				$('#fp1').get(0).enableFingerprint();
			}
			else{
				alert("Applet fp1 is not loaded");
			}			
			if($('#fpQC1').length >0){
				$('#fpQC1').get(0).enableFingerprint();
			}
			else{
				alert("Applet fpQC1 is not loaded");
			}
//			$('#fp1').get(0).enableFingerprint();
//			$('#fpQC1').get(0).enableFingerprint();
			enableSelect("selFPPattern1");
			enableSelect("selFPNo1");			
			$("input:radio[name='hidFpType1']:nth(0)").attr("disabled", false);
			$("input:radio[name='hidFpType1']:nth(1)").attr("disabled", false);
			updateInputValue("hidNoFp1","false");
			
			// Need to check the status of other fingerprint
			var showVerifyFingerprint = isVerifyFingerprintRequired_New(getInputValueLowerCase("hidHasFingerForPrevGen1"),getInputValue("hidNoFp1"),getInputValueLowerCase("hidHasFingerForPrevGen2"),getInputValue("hidNoFp2"));			
			determineFpVerification("3", showVerifyFingerprint, getInputValue("file_match_result3"));
			determineFpVerification("4", showVerifyFingerprint, getInputValue("file_match_result4"));
		} else if (finger == "2") {
			if($('#fp2').length >0){
				$('#fp2').get(0).enableFingerprint();
			}
			else{
				alert("Applet fp2 is not loaded");
			}			
			if($('#fpQC2').length >0){
				$('#fpQC2').get(0).enableFingerprint();
			}
			else{
				alert("Applet fpQC2 is not loaded");
			}
//			$('#fp2').get(0).enableFingerprint();
//			$('#fpQC2').get(0).enableFingerprint();
			enableSelect("selFPPattern2");
			enableSelect("selFPNo2");			
			$("input:radio[name='hidFpType2']:nth(0)").attr("disabled", false);
			$("input:radio[name='hidFpType2']:nth(1)").attr("disabled", false);
			updateInputValue("hidNoFp2","false");
			
			// Need to check the status of other fingerprint
			var showVerifyFingerprint = isVerifyFingerprintRequired_New(getInputValueLowerCase("hidHasFingerForPrevGen1"),getInputValue("hidNoFp1"),getInputValueLowerCase("hidHasFingerForPrevGen2"),getInputValue("hidNoFp2"));			
			determineFpVerification("1", showVerifyFingerprint, getInputValue("file_match_result1"));
			determineFpVerification("2", showVerifyFingerprint, getInputValue("file_match_result2"));
		}
	}
	
	
}


function jobFingerprintDone(appletID, finger, patternType, minutiaCount, minutiaQuality, matchScore, fileMatchScore, qcMatchResult, fileMatchResult) {
	if (appletID == "1") {
		updateLabel("job_finger1", finger);
		updateLabel("job_pattern1", patternType);
		updateLabel("job_minutia_count1", finger);
		updateLabel("job_minutia_quality1", minutiaQuality);
		updateLabel("job_qc_score1", matchScore);
		updateLabel("job_match_score1", fileMatchScore);

		if (qcMatchResult == "FAIL") {
			hideLabel("idQcMatchResult1Pass");
			showLabel("idQcMatchResult1Fail");
			updateLabel("idQcMatchResult1Fail", qcMatchResult);
		} else if (qcMatchResult == "PASS") {
			hideLabel("idQcMatchResult1Fail");
			showLabel("idQcMatchResult1Pass");
			updateLabel("idQcMatchResult1Pass", qcMatchResult);
		}

		if (fileMatchResult == "FAIL") {
			hideLabel("idFileMatchResult1Pass");
			showLabel("idFileMatchResult1Fail");
			updateLabel("idFileMatchResult1Fail", fileMatchResult);
		} else if (fileMatchResult == "PASS") {
			hideLabel("idFileMatchResult1Fail");
			showLabel("idFileMatchResult1Pass");
			updateLabel("idFileMatchResult1Pass", fileMatchResult);
		}
	} else if (appletID == "2") {
		updateLabel("job_finger2", finger);
		updateLabel("job_pattern2", patternType);
		updateLabel("job_minutia_count2", minutiaCount);
		updateLabel("job_minutia_quality2", minutiaQuality);
		updateLabel("job_qc_score2", matchScore);
		updateLabel("job_match_score2", fileMatchScore);

		if (qcMatchResult == "FAIL") {
			hideLabel("idQcMatchResult2Pass");
			showLabel("idQcMatchResult2Fail");
			updateLabel("idQcMatchResult2Fail", qcMatchResult);
		} else if (qcMatchResult == "PASS") {
			hideLabel("idQcMatchResult2Fail");
			showLabel("idQcMatchResult2Pass");
			updateLabel("idQcMatchResult2Pass", qcMatchResult);
		}

		if (fileMatchResult == "FAIL") {
			hideLabel("idFileMatchResult2Pass");
			showLabel("idFileMatchResult2Fail");
			updateLabel("idFileMatchResult2Fail", fileMatchResult);
		} else if (fileMatchResult == "PASS") {
			hideLabel("idFileMatchResult2Fail");
			showLabel("idFileMatchResult2Pass");
			updateLabel("idFileMatchResult2Pass", fileMatchResult);
		}
	} else if (appletID == "3") {
		updateLabel("job_finger3", finger);
		updateLabel("job_pattern3", patternType);
		updateLabel("job_minutia_count3", minutiaCount);
		updateLabel("job_minutia_quality3", minutiaQuality);
		updateLabel("job_qc_score3", matchScore);
		updateLabel("job_match_score3", fileMatchScore);

		if (qcMatchResult == "FAIL") {
			hideLabel("idQcMatchResult3Pass");
			showLabel("idQcMatchResult3Fail");
			updateLabel("idQcMatchResult3Fail", qcMatchResult);
		} else if (qcMatchResult == "PASS") {
			hideLabel("idQcMatchResult3Fail");
			showLabel("idQcMatchResult3Pass");
			updateLabel("idQcMatchResult3Pass", qcMatchResult);
		}

		if (fileMatchResult == "FAIL") {
			hideLabel("idFileMatchResult3Pass");
			showLabel("idFileMatchResult3Fail");
			updateLabel("idFileMatchResult3Fail", fileMatchResult);
		} else if (fileMatchResult == "PASS") {
			hideLabel("idFileMatchResult3Fail");
			showLabel("idFileMatchResult3Pass");
			updateLabel("idFileMatchResult3Pass", fileMatchResult);
		}
	} else if (appletID == "4") {
		updateLabel("job_finger4", finger);
		updateLabel("job_pattern4", patternType);
		updateLabel("job_minutia_count4", minutiaCount);
		updateLabel("job_minutia_quality4", minutiaQuality);
		updateLabel("job_qc_score4", matchScore);
		updateLabel("job_match_score4", fileMatchScore);

		if (qcMatchResult == "FAIL") {
			hideLabel("idQcMatchResult4Pass");
			showLabel("idQcMatchResult4Fail");
			updateLabel("idQcMatchResult4Fail", qcMatchResult);
		} else if (qcMatchResult == "PASS") {
			hideLabel("idQcMatchResult4Fail");
			showLabel("idQcMatchResult4Pass");
			updateLabel("idQcMatchResult4Pass", qcMatchResult);
		}

		if (fileMatchResult == "FAIL") {
			hideLabel("idFileMatchResult4Pass");
			showLabel("idFileMatchResult4Fail");
			updateLabel("idFileMatchResult4Fail", fileMatchResult);
		} else if (fileMatchResult == "PASS") {
			hideLabel("idFileMatchResult4Fail");
			showLabel("idFileMatchResult4Pass");
			updateLabel("idFileMatchResult4Pass", fileMatchResult);
		}
	}
}

function clearFingerprintCapturedAppletFields(appletID
		, minutiaCountField, minutiaQualityField, fileMatchScoreField, fileMatchResultField
		, idFileMatchResultPassField, idFileMatchResultFailField, qcMatchScoreField, qcMatchResultField, idQcMatchResultPassField, idQcMatchResultFailField){
	updateLabel(minutiaCountField, "");
	updateLabel(minutiaQualityField, "");
	updateLabel(fileMatchScoreField, "");
	updateInputValue(fileMatchResultField, "");
	hideLabel(idFileMatchResultPassField);
	hideLabel(idFileMatchResultFailField);
	
	updateLabel(qcMatchScoreField, "");
	updateLabel(qcMatchResultField, "");
	hideLabel(idQcMatchResultPassField);
	hideLabel(idQcMatchResultFailField);
}
