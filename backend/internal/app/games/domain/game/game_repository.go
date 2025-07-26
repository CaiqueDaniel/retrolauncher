package game

type GameRepository interface {
	Save(entity *Game) error
}
