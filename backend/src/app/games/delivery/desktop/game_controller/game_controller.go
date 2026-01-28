package game_controller

import (
	game_app "retrolauncher/backend/src/app/games/application"
	"retrolauncher/backend/src/app/games/application/create_game"
	"retrolauncher/backend/src/app/games/application/get_game"
	"retrolauncher/backend/src/app/games/application/get_platform_types"
	"retrolauncher/backend/src/app/games/application/start_game"
)

type GameController struct {
	createGame       *create_game.CreateGame
	updateGame       game_app.UpdateGame
	listGames        game_app.ListGames
	getGame          *get_game.GetGame
	getPlatformTypes *get_platform_types.GetPlatformTypes
	startGame        *start_game.StartGame
}

func New(
	createGame *create_game.CreateGame,
	updateGame game_app.UpdateGame,
	getGame *get_game.GetGame,
	listGames game_app.ListGames,
	getPlatformTypes *get_platform_types.GetPlatformTypes,
	startGame *start_game.StartGame,
) *GameController {
	return &GameController{
		createGame:       createGame,
		updateGame:       updateGame,
		listGames:        listGames,
		getGame:          getGame,
		getPlatformTypes: getPlatformTypes,
		startGame:        startGame,
	}
}

func (gc *GameController) Create(input CreateInputDto) []string {
	errors := gc.createGame.Execute(create_game.Input{
		Name:         input.Name,
		PlatformType: input.PlatformType,
		PlatformPath: input.PlatformPath,
		Path:         input.Path,
		Cover:        input.Cover,
	})

	var errorMessages []string

	for _, err := range errors {
		errorMessages = append(errorMessages, err.Error())
	}

	return errorMessages
}

func (gc *GameController) Update(input UpdateInputDto) []string {
	errors := gc.updateGame.Execute(game_app.UpdateGameInput{
		ID:           input.ID,
		Name:         input.Name,
		PlatformType: input.PlatformType,
		PlatformPath: input.PlatformPath,
		Path:         input.Path,
		Cover:        input.Cover,
	})

	var errorMessages []string

	for _, err := range errors {
		errorMessages = append(errorMessages, err.Error())
	}

	return errorMessages
}

func (gc *GameController) List(input ListInputDto) []*game_app.ListGamesOutput {
	return gc.listGames.Execute(game_app.ListGamesInput{
		Name: input.Name,
	})
}

func (gc *GameController) Get(input GetInputDto) (*get_game.Output, error) {
	return gc.getGame.Execute(get_game.Input{
		Id: input.Id,
	})
}

func (c *GameController) GetPlatformTypes() []string {
	return *c.getPlatformTypes.Execute()
}

func (gc *GameController) StartGame(input GetInputDto) error {
	return gc.startGame.Execute(start_game.Input{
		GameId: input.Id,
	})
}

type CreateInputDto struct {
	Name         string `json:"name"`
	PlatformType string `json:"platformType"`
	PlatformPath string `json:"platformPath"`
	Path         string `json:"path"`
	Cover        string `json:"cover"`
}

type UpdateInputDto struct {
	ID string `json:"id"`
	CreateInputDto
}

type ListInputDto struct {
	Name string `json:"name"`
}

type GetInputDto struct {
	Id string `json:"id"`
}
