const Database = require('./Database.js');
const HighlightRecord = require('./HighlightRecord.js');
const fs = require('fs');

const createDB = function(file, db) {
	let data = fs.readFileSync(file, 'utf8');

	(function make(data) {
		let arr = data.split('==========\r');
		for (let i = 0; i < arr.length; i++) {
			let record = new HighlightRecord();
			record.init(arr[i]);
			record.trim();
			db.insert(record);
		}
	})(data);
}

const createFile = function(file, arr) {
	for (let record of arr) {
		fs.appendFileSync(file, record.title + '( ' + record.author + ' )\r');
		fs.appendFileSync(file, record.page + ' | ' + record.location + ' | ' + record.date_time + '\r');
		fs.appendFileSync(file, '\r\n');
		fs.appendFileSync(file, record.text + '\r');
		fs.appendFileSync(file,'==========' + '\r');
	}
}

const resetUsedFile = function(file) {
		fs.writeFileSync(file, '');
}

const getHighlights = function(inFile, usedFile) {
	const db1 = new Database();
	const db2 = new Database();
	let record;
	let arr = [];

	createDB(inFile, db1);
	createDB(usedFile, db2);
	db1.clean();
	db2.clean();

	if (db1.size < db2.size) resetUsedFile(usedFile);

	for (let i = 0; i < 3; i++) {
		record = Database.pickRandom(db1, db2);
		db2.insert(record);
		arr.push(record);
	}
	return arr;
}

const createEmail = function(file, arr) {

	fs.writeFileSync(file, '');

	for (let record of arr) {
		fs.appendFileSync(file,
			`<table class="main" style="border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background: #fff; border-radius: 3px; width: 100%;" width="100%">
			<tr>
			<td class="wrapper" style="font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px;" >
			<table border="0" cellpadding="0" cellspacing="0" style="border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;" width="100%">
			<tr>
			<td style="font-size: 14px; vertical-align: top;">
			<span style="font-weight: bold; font-size: 18px;">${record.title}</span>
			<br>
			<span style="color:#9f8e7d; font-size:85%; vertical-align: bottom;">by: ${record.author}</span>
			<hr>
			<span style="margin-left: 0px;">${record.text}</span>
			</td>
			</tr>
			</table>
			</td>
			</tr>
			</table>
			<div style="width:100%;">&nbsp;</div>`
		);
	}
}

let file1 = './My Clippings.txt';
let file2 = './Sent Clippings.txt';
let file3 = './Todays Email Body.htm';
let todaysHilights = getHighlights(file1, file2);
		createFile(file2, todaysHilights);
		createEmail(file3, todaysHilights);

console.log('KindleHighlights.js ran without errors. Enjoy your Highlights!');
