package persistance

import (
	"github.com/asdine/storm"
	"github.com/asdine/storm/q"
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

func (r *StormRepository[T]) List(fieldName, likeValue, tableName string) ([]T, error) {
	var results []T

	db, err := storm.Open(tableName)

	if err != nil {
		if db != nil {
			defer db.Close()
		}
		return nil, err
	}

	if likeValue == "" {
		err = db.All(&results)
		defer db.Close()
		return results, err
	}

	err = db.Select(q.Re(fieldName, likeValue)).Find(&results)

	defer db.Close()

	if err != nil && err == storm.ErrNotFound {
		return []T{}, nil
	}

	return results, err
}
