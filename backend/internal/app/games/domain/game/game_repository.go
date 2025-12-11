package game

type GameRepository interface {
	Save(entity *Game) error
	List(input ListGamesParams) []Game
}

type ListGamesParams struct {
	Name string
}
