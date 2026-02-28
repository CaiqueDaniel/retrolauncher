package persistance

import (
	"os"
	"path/filepath"

	"github.com/asdine/storm"
	"github.com/asdine/storm/q"
)

type StormRepository[T any] struct{}

func (r *StormRepository[T]) Save(data interface{}, tableName string) error {
	tablePath, err := r.getTablePath(tableName)

	if err != nil {
		return err
	}

	db, err := storm.Open(tablePath)

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
	tablePath, err := r.getTablePath(tableName)

	if err != nil {
		return nil, err
	}

	db, err := storm.Open(tablePath)

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

func (r *StormRepository[T]) List(fieldName, likeValue, tableName, orderBy string) ([]*T, error) {
	var results []*T

	tablePath, err := r.getTablePath(tableName)

	if err != nil {
		return nil, err
	}

	db, err := storm.Open(tablePath)

	if err != nil {
		if db != nil {
			defer db.Close()
		}
		return nil, err
	}

	if likeValue == "" {
		err = db.Select().OrderBy(orderBy).Find(&results)
		defer db.Close()
		return results, err
	}

	err = db.Select(q.Re(fieldName, likeValue)).OrderBy(orderBy).Find(&results)

	defer db.Close()

	if err != nil && err == storm.ErrNotFound {
		return []*T{}, nil
	}

	return results, err
}

func (r *StormRepository[T]) getTablePath(tableName string) (string, error) {
	binPath, err := os.Executable()

	if err != nil {
		return "", err
	}

	return filepath.Join(filepath.Dir(binPath), tableName), nil
}
