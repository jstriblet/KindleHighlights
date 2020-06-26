#!/bin/bash
cd /Users/striblet/Documents/CS/KindleHighlights

#java KindleHighlights

/usr/local/bin/node KindleHighlights.js

cat Todays\ Email\ Header.htm Todays\ Email\ Body.htm Todays\ Email\ Footer.htm  | /usr/sbin/sendmail -t

# osascript SendEmail.scpt
# Create a LaunchAgent @ ~/Library/LaunchAgent to run this script automatically
# A copy of the one I used is located in: com.KindleHighlights.plist
# sudo postmap /etc/postfix/sasl_passwd if updating the password to create a new .db file

