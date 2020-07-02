# Kindle Highlights

KindleHighlights is a small Node.js utility that will sends readers text highlights made on their Kindle by way of a shell script and a Launchd entry.

The hope for this project is to leverage spaced repetition to combat the forgetting curve by serving up randomly selected highlights from your Kindle Clippings -- Helping readers remember the content that resonated most.

Inspired by:

[readwise.](https://readwise.io/)

the [Forgetting curve](https://en.wikipedia.org/wiki/Forgetting_curve)

& [Spaced Repetition](https://en.wikipedia.org/wiki/Spaced_repetition)

##  Requirements

Node.js v13.8.0+

Launchd(8)

[LaunchControl](https://www.soma-zone.com/LaunchControl/) -- totally optional but I found it simpler to set up launchd daemons 

## Installation

Clone the repository to a convenient location. 

## Setup & Usage

### My Clippings.txt

Plug your Kindle into your computer and locate your "My Clippings.txt" file.
Overwrite the "My Clippings.txt" file that came with this repository with the one from your Kindle.
Delete the contents of the "Sent Clippings.txt" file.

### Launchd

An example of the what my launch agent file looks like should be located in the repository as "com.KindleHighlights.plist". It is probably a smart idea to learn how to write the xml directly for this launch daemon but if you decide to go the lazy route like I have, the [LaunchControl app](https://www.soma-zone.com/LaunchControl/) is pretty easy to figure out and will make ensure your "com.KindleHighlights.plist" is configured correctly. 

### sendmail (mail/mailx)

Setting up send mail for your particular OS is out of the scope for this README, however here are some links to help you get started:

[mailx command examples](https://www.binarytides.com/linux-mailx-command/)

[How to Send Email Using mailx/s-nail in Linux Through Internal SMTP](https://www.systutorials.com/sending-email-using-mailx-in-linux-through-internal-smtp/)

[https://coderwall.com/p/ez1x2w/send-mail-like-a-boss](https://coderwall.com/p/ez1x2w/send-mail-like-a-boss)

Additionally, the "KindleHighlights.sh" file has an example of of how my sendmail command arguments are formatted. 

### KindleHighlights.sh

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## To-Do

- Automate the importing of the "My Clippings.txt" file when plugging in the Kindle.
