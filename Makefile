test:
	go test ./... -v

dev:
	xvfb-run wails dev -tags webkit2_41

build-gui:
	wails build

build-start-game-cli:
	go build -o build/bin/start-game.exe entrypoints/cli/games/main.go