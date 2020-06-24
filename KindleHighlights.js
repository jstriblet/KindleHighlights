class Database {
	constructor() {
		this.db = new Map();
	}

	insert(record) {
		this.db.set(record, 'N');
	}

	get size() {
		return this.db.size;
	}

	clean(){
		for (let h of this.db.keys()) {
			if (h.highlight === null)
				console.log(h)
				this.db.delete(h);

		}
	}

	static pickRandom(from, check) {
		let record;
		do {
			let index = Math.floor(Math.random() * from.size);
			let cntr = 0;
			for (let key of from.db.keys()) {
				if (cntr++ === index) {
					record = key;
				}
			}
		} while(check.db.has(record))

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

		if (highlightLen < 5) return false;

		this.title = arr[1].split('(')[0]; 
		this.author = arr[1].split('(')[1];
		this.page = arr[2].split('|')[0];
		this.location = arr[2].split('|')[1];
		this.date_time = arr[2].split('|')[2];
		this.highlight = arr[4];

		if (this.title.replace(')', '').replace('\n', '').trim() == 'A Guide to the Good Life') {
			console.log(arr[4].length );
		}

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
	let data = fs.readFileSync(file, 'utf8')
	function make(data) {
		let arr = data.split('==========');
		for (let i = 0; i < arr.length; i++) {
			let record = new HighlightRecord();
			record.init(arr[i]);
			record.trim();
			db.insert(record);
		}
	}
	make(data);	
}

const db1 = new Database();
const db2 = new Database();

file1 = './My Clippings.txt';
file2 = './Sent Clippings.txt';

createDB(file1, db1);
createDB(file2, db2);

db1.clean();

//let rndmRcrd = Database.pickRandom(db1, db2);

//console.log(rndmRcrd);
