package application

import "fmt"

var _ error = NotFoundError("")

type NotFoundError string

func (e NotFoundError) Error() string {
	return fmt.Sprintf("Not found: %s", string(e))
}
