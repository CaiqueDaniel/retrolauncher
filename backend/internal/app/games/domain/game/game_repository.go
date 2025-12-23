package game

type GameRepository interface {
	Save(entity *Game) error
	Get(id string) (*Game, error)
	List(input ListGamesParams) []Game
}

type ListGamesParams struct {
	Name string
}
