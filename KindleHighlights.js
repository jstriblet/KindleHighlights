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
			if (h.highlight === null)
				this.records.delete(h);
		}
	}

	static pickRandom(from, check) {
		let record;
		do {
			let index = Math.floor(Math.random() * from.size);
			let cntr = 0;
			for (let key of from.records.keys()) {
				if (cntr++ === index) {
					record = key;
				}
			}
		} while(check.records.has(record))

		return record;
	}
	
}

class HighlightRecord {
	constructor(text) {
		this.title;
		this.author;
		this.page;
		this.location;
		this.highlight;
		this.date_time;
	}

	init(text) {
		let arr = text.split('\r');
		let highlightLen = arr[4] ? arr[4].split(' ').length : 0;

		if (highlightLen < 5) {
			//console.log('this is ignored ' + arr[4]);
			return false;
		}

		this.title = arr[1].split('(')[0]; 
		this.author = arr[1].split('(')[1];
		this.page = arr[2].split('|')[0];
		this.location = arr[2].split('|')[1];
		this.date_time = arr[2].split('|')[2];
		this.highlight = arr[4];

	}

	trim() {
		this.title = this.title ? this.title.replace(')', '').replace('\n', '').trim() : null;
		this.author = this.author ? this.author.replace(')', '').replace('\n', '').trim() : null;
		this.page = this.page ? this.page.replace(')', '').replace('\n', '').replace('- Your Highlight on page', '').trim() : null;
		this.location = this.location ? this.location.replace(')', '').replace('\n', '').replace('location', '').trim() : null;
		this.date_time = this.date_time ? this.date_time.replace(')', '').replace('\n', '').replace('Added on', '').trim() : null;
		this.highlight = this.highlight ? this.highlight.replace(')', '').replace('\n', '').trim() : null;

		if (this.title == 'A Guide to the Good Life'){
			this.author = 'William Braxton Irvine'; 
		}
	}
}

const createDB = function(file, db) {
	const fs = require('fs');
	let data = fs.readFileSync(file, 'utf8');

	(function make(data) {
		let arr = data.split('==========');
		for (let i = 0; i < arr.length; i++) {
			let record = new HighlightRecord();
			record.init(arr[i]);
			record.trim();
			db.insert(record);
		}
	})(data);
}

// ... write the reference / used highlights data structure to file possibly use the appendFile method.
const createFile = function(file, db) {
	const fs = require('fs');
	fs.writeFileSync(file, db)
}


const getHighlights = function(inFile, usedFile) {

	const db1 = new Database();
	const db2 = new Database();

	createDB(inFile, db1);
	createDB(usedFile, db2);
	db1.clean();
	db2.clean();

	let record;

	record = Database.pickRandom(db1, db2);
	db2.insert(record)
	record = Database.pickRandom(db1, db2);
	db2.insert(record)
	record = Database.pickRandom(db1, db2);
	db2.insert(record)


	 console.log(db2)

}

let file1 = './My Clippings.txt';
let file2 = './Sent Clippings.txt';

getHighlights(file1, file2);













