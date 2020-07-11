class Database {
	constructor() {
		this.records = new Map();
	}

	insert(record) {
		this.records.set(record, 'Y');
	}

	get size() {
		return this.records.size;
	}

	clean() {
		for (let h of this.records.keys()) {
			if (h.text === null)
				this.records.delete(h);
		}
	}

	static pickRandom(from, check) {
		let record;
		let loop = 0;
		let used = false;

		do {
			let index = Math.floor(Math.random() * from.size);
			let cntr = 0;

			for (let key of from.records.keys()) {
				if (cntr++ === index) {
					record = key;
				}
			}

			for (let key of check.records.keys()) {
				if (key.text === record.text) {
					used = true;
					//console.log(key, 'has been used')
				}
			}

			loop++;

		} while (used && loop < from.size * 1000)

		return record;
	}
	
}

module.exports = Database;
