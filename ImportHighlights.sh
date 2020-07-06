#!/bin/bash
cd ~/Documents/CS/KindleHighlights/

NOW=$(date +'%Y%m%d')

if [ -d "/Volumes/Kindle/documents/" ] 
then

	cp "My Clippings.txt" "hist/My Clippings.txt-$NOW"
	cp "/Volumes/Kindle/documents/My Clippings.txt" .

	echo "Directory Exists $NOW"
else
	echo "Directory Does Not Exists"
fi
