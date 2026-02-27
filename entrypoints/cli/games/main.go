package main

import (
	"fmt"
	"os"
	"retrolauncher/backend/src/app/games"
)

func main() {
	args := os.Args

	if len(args) != 2 {
		fmt.Println("Usage: start-game <game_id>")
		os.Exit(1)
		return
	}

	fmt.Println("Opening game")
	games.NewGamesCLIModule().StartGameCommand.Execute(args[1])
}
