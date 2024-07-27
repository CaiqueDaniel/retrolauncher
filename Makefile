ifeq ($(OS), Windows_NT)
  RM = if exist "build" rmdir "build" /q /s
else
  RM = rm "build" -f
endif

compile:
	$(RM)
	mvn clean package
	jpackage --type app-image --input target --name retrolauncher --dest build --main-jar retro-launcher-1.0.0.jar --main-class org.retrolauncher.Main

dev:
	mvn javafx:run