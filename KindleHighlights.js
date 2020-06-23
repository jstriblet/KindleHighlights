class Database {
	constructor() {
		this.sz;
		this.db = new Map();
	}
	insert(record) {
		this.db.set(record, 'N');
	}
	get size() {
		return this.db.size;
	}
	static pickRandom(db1, db2) {
		console.log('random highlight');
	}
	
}

class HilightRecord {
	constructor(text) {
		this.title;
		this.author;
		this.page;
		this.location;
		this.hilight;
		this.date_time;
	}

	init(text) {
		let arr = text.split('\r');
		let hilightLen = arr[4] ? arr[4].split(' ').length : 0;

		if (hilightLen < 5) return false;

		this.title = arr[1].split('(')[0]; 
		this.author = arr[1].split('(')[1];
		this.page = arr[2].split('|')[0];
		this.location = arr[2].split('|')[1];
		this.date_time = arr[2].split('|')[2];
		this.hilight = arr[4];

	}

	clean() {
		this.title = this.title ? this.title.replace(')', '').replace('\n', '').trim() : null;
		this.author = this.author ? this.author.replace(')', '').replace('\n', '').trim() : null;
		this.page = this.page ? this.page.replace(')', '').replace('\n', '').replace('- Your Highlight on page', '').trim() : null;
		this.location = this.location ? this.location.replace(')', '').replace('\n', '').replace('location', '').trim() : null;
		this.date_time = this.date_time ? this.date_time.replace(')', '').replace('\n', '').replace('Added on', '').trim() : null;
		this.hilight = this.hilight ? this.hilight.replace(')', '').replace('\n', '').trim() : null;

		if (this.title = 'Guide to the good life') this.author = 'William Braxton Irvine'; 
	}
}

const createDB = function(file, db) {
	const fs = require('fs');
	let data = fs.readFileSync(file, 'utf8')
	function make(data) {
		let arr = data.split('==========');
		for (let i = 0; i < arr.length; i++) {
			let record = new HilightRecord();
			record.init(arr[i]);
			record.clean();
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

console.log(db1.size)
console.log(db2.size)
// todo: Implement pickRandom() for static method above;
Database.pickRandom()


