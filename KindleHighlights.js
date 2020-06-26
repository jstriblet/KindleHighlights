class Database {
	constructor() {
		this.records = new Map();
	}

	insert(record) {
		this.records.set(record, 'N');
	}

	get size() {
		return this.records.size;
	}

	clean(){
		for (let h of this.records.keys()) {
			if (h.text === null)
				this.records.delete(h);
		}
	}

	static pickRandom(from, check) {
		let record;
		let loop = 0;
		do {
			let index = Math.floor(Math.random() * from.size);
			let cntr = 0;
			for (let key of from.records.keys()) {
				if (cntr++ === index) {
					record = key;
				}
			}
			loop++;
		} while(check.records.has(record) && loop < from.size * 1000)

		return record;
	}
	
}

class HighlightRecord {
	constructor(text) {
		this.title;
		this.author;
		this.page;
		this.location;
		this.text;
		this.date_time;
	}

	init(text) {
		let arr = text.split('\r');
		let highlightLen = arr[3] ? arr[3].split(' ').length : 0;

		if (highlightLen < 5) {
			//console.log('this is ignored ' + arr[4]);
			return false;
		}

		this.title = arr[0].split('(')[0]; 
		this.author = arr[0].split('(')[1];
		this.page = arr[1].split('|')[0];
		this.location = arr[1].split('|')[1];
		this.date_time = arr[1].split('|')[2];
		this.text = arr[3];

	}

	trim() {
		this.title = this.title ? this.title.replace(')', '').replace('\n', '').trim() : null;
		this.author = this.author ? this.author.replace(')', '').replace('\n', '').trim() : null;
		this.page = this.page ? this.page.replace(')', '').replace('\n', '').replace('- Your Highlight on page', '').trim() : null;
		this.location = this.location ? this.location.replace(')', '').replace('\n', '').replace('location', '').trim() : null;
		this.date_time = this.date_time ? this.date_time.replace(')', '').replace('\n', '').replace('Added on', '').trim() : null;
		this.text = this.text ? this.text.replace(')', '').replace('\n', '').trim() : null;
		this.author = this.title == 'A Guide to the Good Life' ? 'William Braxton Irvine' : this.author;
	}
}

const createDB = function(file, db) {
	const fs = require('fs');
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
	const fs = require('fs');

	for (let record of arr) {
		console.log(record.text)
		fs.appendFileSync(file, record.title + '( ' + record.author + ' )\r');
		fs.appendFileSync(file, record.page + ' | ' + record.location + ' | ' + record.date_time + '\r');
		fs.appendFileSync(file, '\r\n');
		fs.appendFileSync(file, record.text + '\r');
		fs.appendFileSync(file,'==========' + '\r');
	}
}

const resetUsedFile = function(file) {
		const fs = require('fs');
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
		db2.insert(record)
		arr.push(record)
		//console.log(record.text + '\r\n')
	}

	return arr
}


const createEmail = function(file, arr) {
	const fs = require('fs');
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

// to-do: seperate out the classes into their own files;

