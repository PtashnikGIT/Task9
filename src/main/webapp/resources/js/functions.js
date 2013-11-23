//for edit/add page
function saveSubmit() {

	var newsTitle = document.getElementById('j_idt30:newsTitle').value;

	var newsDate = document.getElementById('j_idt30:newsDate').value;
	var newsBrief = document.getElementById('j_idt30:newsBrief').value;
	var newsContent = document.getElementById('j_idt30:newsContent').value;
	var errorList = [];

	if (newsTitle.length == 0) {
		errorList.push(titleNotFulfilled);
	}
	if (newsTitle.length > 100) {
		errorList.push(titleIsOutOfLength);
	}
	if (newsDate.length == 0) {
		errorList.push(dateNotFulfilled);
	} else {
		errorList = validateDate(newsDate, errorList);
	}

	if (newsBrief.length == 0) {
		errorList.push(briefNotFulfilled);
	}
	if (newsTitle.length > 500) {
		errorList.push(briefIsOutOfLength);
	}
	if (newsContent.length == 0) {
		errorList.push(contentNotFulfilled);
	}
	if (newsContent.length > 2048) {
		errorList.push(contentIsOutOfLength);
	}
	errorList.reverse();

	var mess = "";
	var errorNumber = errorList.length;
	for (var i = 0; i < errorNumber; i++) {
		mess += errorList.pop() + "\n";
	}

	if (errorNumber == 0) {
		return true;
	} else {
		alert(mess);
		return false;
	}
}

function validateDate(date, errorList) {
	var datePattern = /^(\d{2})\/(\d{2})\/(\d{4})$/;
	if (!datePattern.test(date)) {
		errorList.push(dateWrongFormat);

	}
	var month = parseInt(RegExp.$1, 10);
	if (month <= 0 || month > 12) {
		errorList.push(dateWrongMonth);
	}
	var year = parseInt(RegExp.$3, 10);
	var daysArray = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	if (year % 4 == 0) {
		daysArray[1] = 29;
	}
	var date = parseInt(RegExp.$2, 10);
	if (date <= 0 || date > daysArray[month - 1]) {
		errorList.push(dateWrongDate);

	}
	return errorList;
}


// For List page
function isCheckedAtLeastOne() {
	var inputs = document.getElementsByTagName("input");
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].checked) {
			return true;
		}
	}
	return false;
}

function submitDeleteNews() {
	if (isCheckedAtLeastOne()) {
		if (confirm(deleteCheckedQuestionMessage)) {
			return true;
		} else {
			return false;
		}
	} else {
		alert(checkAnyMessage);
	}
	return false;
}
