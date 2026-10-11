ifeq ($(OS),Windows_NT)
	BIN_NAME := start-game.exe
else
	BIN_NAME := start-game
endif

test:
	go test ./... -v

dev:
	wails dev

build-gui:
	wails build

build-start-game-cli:
	go build -o build/bin/$(BIN_NAME) entrypoints/cli/games/main.go