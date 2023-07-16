# ApartmentFinderBot
Application is designed to search for new items on the website and, as soon as they appear, send a request to the owner of they apartment. The bot is implemented with Telegram API.

## Useful commands
Creates a fat .jar file that can be run with *java -jar* command

    mvn clean compile assembly:single

Runs the command in the background and sends logs to a file

    java -jar yourfile.jar > output.log 2>&1 &

