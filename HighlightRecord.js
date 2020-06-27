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

		if (highlightLen < 5)	return false;

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


module.exports = HighlightRecord;	
