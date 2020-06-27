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

module.exports = Database;
