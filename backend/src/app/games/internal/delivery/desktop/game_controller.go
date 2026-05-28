package desktop

import (
	"retrolauncher/backend/src/app/games/internal/application"
	"retrolauncher/backend/src/shared/cache"
)

type GameController struct {
	createGame                 application.CreateGame
	updateGame                 application.UpdateGame
	listGames                  application.ListGames
	getGame                    application.GetGame
	getPlatformTypes           application.GetPlatformTypes
	startGame                  application.StartGame
	autoIndexGames             application.AutoIndexGames
	createDesktopShortcut      application.CreateShortcut
	listAchievements           application.ListAchievementsFromGame
	gamesAchievementsListCache cache.Cache[string, []application.Achievement]
}

func New(
	createGame application.CreateGame,
	updateGame application.UpdateGame,
	getGame application.GetGame,
	listGames application.ListGames,
	getPlatformTypes application.GetPlatformTypes,
	startGame application.StartGame,
	autoIndexGames application.AutoIndexGames,
	createDesktopShortcut application.CreateShortcut,
	listAchievements application.ListAchievementsFromGame,
	gamesAchievementsListCache cache.Cache[string, []application.Achievement],
) *GameController {
	return &GameController{
		createGame:                 createGame,
		updateGame:                 updateGame,
		listGames:                  listGames,
		getGame:                    getGame,
		getPlatformTypes:           getPlatformTypes,
		startGame:                  startGame,
		autoIndexGames:             autoIndexGames,
		createDesktopShortcut:      createDesktopShortcut,
		listAchievements:           listAchievements,
		gamesAchievementsListCache: gamesAchievementsListCache,
	}
}

func (gc *GameController) Create(input CreateInputDto) []string {
	errors := gc.createGame.Execute(application.CreateGameInput{
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
	errors := gc.updateGame.Execute(application.UpdateGameInput{
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

func (gc *GameController) List(input ListInputDto) []*application.ListGamesOutput {
	return gc.listGames.Execute(application.ListGamesInput{
		Name: input.Name,
	})
}

func (gc *GameController) Get(input GetInputDto) (*application.GetGameOutput, error) {
	return gc.getGame.Execute(application.GetGameInput{
		Id: input.Id,
	})
}

func (c *GameController) GetPlatformTypes() []string {
	return c.getPlatformTypes.Execute()
}

func (gc *GameController) StartGame(input GetInputDto) error {
	return gc.startGame.Execute(application.StartGameInput{
		GameId: input.Id,
	})
}

func (gc *GameController) AutoIndexGames() error {
	return gc.autoIndexGames.Execute()
}

func (gc *GameController) CreateDesktopShortcut(input GetInputDto) error {
	return gc.createDesktopShortcut.Execute(input.Id)
}

func (gc *GameController) GetAchievements(input GetInputDto) ([]application.Achievement, error) {
	achievements := gc.gamesAchievementsListCache.Get(input.Id)

	if len(achievements) > 0 {
		return achievements, nil
	}

	achievements, err := gc.listAchievements.Execute(application.ListAchievementsFromGameInput{
		Id: input.Id,
	})

	if err != nil {
		return nil, err
	}

	gc.gamesAchievementsListCache.Set(input.Id, achievements)

	return achievements, nil
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
