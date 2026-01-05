test:
	go test ./... -v

dev:
	xvfb-run wails dev -tags webkit2_41