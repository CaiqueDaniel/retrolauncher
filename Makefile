package:
	mvn clean package
	jpackage --type app-image --input target --name retrolauncher --dest build --main-jar retro-launcher-1.0.0.jar --main-class org.retrolauncher.Main