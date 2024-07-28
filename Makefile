ifeq ($(OS), Windows_NT)
  RM = if exist "build" rmdir "build" /q /s
else
  RM = rm "build" -f
endif

TYPE = app-image
NAME = retrolauncher
MAIN_JAR = retro-launcher-1.0.0.jar
MAIN_CLASS = org.retrolauncher.Main
ICON = icon.ico

compile:
	$(RM)
	mvn clean package
	jpackage --type $(TYPE) --input target --name $(NAME) --dest build --main-jar $(MAIN_JAR) --main-class $(MAIN_CLASS) --icon $(ICON)

dev:
	mvn javafx:run