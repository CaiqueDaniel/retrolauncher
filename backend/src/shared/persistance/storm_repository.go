package persistance

import (
	"github.com/asdine/storm"
)

type StormRepository[T any] struct{}

func (r *StormRepository[T]) Save(data interface{}, tableName string) error {
	db, err := storm.Open(tableName)

	if err != nil {
		if db != nil {
			defer db.Close()
		}
		return err
	}

	err = db.Save(data)

	defer db.Close()
	return err
}

func (r *StormRepository[T]) Get(id string, tableName string) (*T, error) {
	var result T
	db, err := storm.Open(tableName)

	if err != nil {
		if db != nil {
			defer db.Close()
		}
		return nil, err
	}

	err = db.One("Id", id, &result)

	defer db.Close()

	if err != nil && err == storm.ErrNotFound {
		return nil, nil
	}

	return &result, err
}
