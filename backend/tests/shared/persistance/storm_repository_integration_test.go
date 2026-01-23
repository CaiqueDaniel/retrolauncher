package shared_persistance_test

import (
	"os"
	"testing"

	shared_persistance "retrolauncher/backend/src/shared/persistance"
)

func TestMain(m *testing.M) {
	// Setup: Create tmp directory
	if err := os.MkdirAll("./tmp", 0755); err != nil {
		panic("Failed to create tmp directory: " + err.Error())
	}

	// Run tests
	code := m.Run()

	// Cleanup: Remove tmp directory
	if err := os.RemoveAll("./tmp"); err != nil {
		panic("Failed to remove tmp directory: " + err.Error())
	}

	os.Exit(code)
}

type TestData struct {
	Id   string `storm:"id"`
	Name string `storm:"index"`
}

func Test_it_should_save_data_to_storm_database(t *testing.T) {
	// Arrange
	sut := &shared_persistance.StormRepository[TestData]{}
	testDB := "./tmp/test_storm.db"
	testData := &TestData{
		Id:   "1",
		Name: "Test Item",
	}

	// Act
	err := sut.Save(testData, testDB)

	// Assert
	if err != nil {
		t.Errorf("Expected no error, but got %v", err)
	}
}

func Test_it_should_save_multiple_data_items(t *testing.T) {
	// Arrange
	sut := &shared_persistance.StormRepository[TestData]{}
	testDB := "./tmp/test_storm_multiple.db"

	testDataItems := []*TestData{
		{Id: "1", Name: "Item 1"},
		{Id: "2", Name: "Item 2"},
		{Id: "3", Name: "Item 3"},
	}

	// Act & Assert
	for _, item := range testDataItems {
		err := sut.Save(item, testDB)

		if err != nil {
			t.Errorf("Expected no error while saving item %s, but got %v", item.Id, err)
		}
	}
}

func Test_it_should_handle_invalid_database_path_gracefully(t *testing.T) {
	// Arrange
	sut := &shared_persistance.StormRepository[TestData]{}
	invalidPath := "/invalid/path/that/does/not/exist/test_storm.db"
	testData := TestData{
		Id:   "1",
		Name: "Test Item",
	}

	// Act
	err := sut.Save(testData, invalidPath)

	// Assert
	if err == nil {
		t.Error("Expected an error for invalid path, but got nil")
	}
}

func Test_it_should_persist_data_across_different_saves(t *testing.T) {
	// Arrange
	sut := &shared_persistance.StormRepository[TestData]{}
	testDB := "./tmp/test_storm_persist.db"

	dataItem1 := &TestData{Id: "1", Name: "First"}
	dataItem2 := &TestData{Id: "2", Name: "Second"}

	// Act
	err1 := sut.Save(dataItem1, testDB)
	err2 := sut.Save(dataItem2, testDB)

	// Assert
	if err1 != nil {
		t.Errorf("First save failed: %v", err1)
	}

	if err2 != nil {
		t.Errorf("Second save failed: %v", err2)
	}
}

func Test_it_should_get_data_from_storm_database(t *testing.T) {
	// Arrange
	sut := &shared_persistance.StormRepository[TestData]{}
	testDB := "./tmp/test_storm.db"
	testData := &TestData{
		Id:   "1",
		Name: "Test Item",
	}
	sut.Save(testData, testDB)

	// Act
	data, err := sut.Get(testData.Id, testDB)

	// Assert
	if err != nil {
		t.Errorf("Expected no error, but got %v", err)
		return
	}

	if data == nil {
		t.Error("Expected data, but got nil")
		return
	}
}

func Test_it_should_get_no_data_from_storm_database_given_nothing_was_found(t *testing.T) {
	// Arrange
	sut := &shared_persistance.StormRepository[TestData]{}
	testDB := "./tmp/test_storm.db"
	testData := &TestData{
		Id:   "1",
		Name: "Test Item",
	}
	sut.Save(testData, testDB)

	// Act
	data, err := sut.Get("no-id", testDB)

	// Assert
	if err != nil {
		t.Errorf("Expected no error, but got %v", err)
		return
	}

	if data != nil {
		t.Error("Expected nil, but got data")
		return
	}
}

func Test_it_should_list_data_from_storm_database(t *testing.T) {
	// Arrange
	sut := &shared_persistance.StormRepository[TestData]{}
	testDB := "./tmp/test_storm.db"
	testData := &TestData{
		Id:   "1",
		Name: "Test Item",
	}
	sut.Save(testData, testDB)

	// Act
	data, err := sut.List("", "", testDB)

	// Assert
	if err != nil {
		t.Errorf("Expected no error, but got %v", err)
		return
	}

	if len(data) == 0 {
		t.Error("Expected data, but got nil")
		return
	}
}

func Test_it_should_search_data_from_storm_database(t *testing.T) {
	// Arrange
	sut := &shared_persistance.StormRepository[TestData]{}
	testDB := "./tmp/test_storm.db"
	testDatas := []*TestData{
		{
			Id:   "1",
			Name: "Test Item",
		}, {
			Id:   "2",
			Name: "Another Item",
		},
	}

	for _, testData := range testDatas {
		sut.Save(testData, testDB)
	}

	// Act
	data, err := sut.List("Name", "Test", testDB)

	// Assert
	if err != nil {
		t.Errorf("Expected no error, but got %v", err)
		return
	}

	if len(data) == 0 {
		t.Error("Expected data, but got empty list")
		return
	}

	if len(data) == 2 {
		t.Error("Expected filtered data, but got all items")
		return
	}

	if data[0].Name != "Test Item" {
		t.Errorf("Expected 'Test Item', but got %s", data[0].Name)
		return
	}
}

func Test_it_should_return_empty_list_from_storm_database_given_nothing_was_found(t *testing.T) {
	// Arrange
	sut := &shared_persistance.StormRepository[TestData]{}
	testDB := "./tmp/test_storm.db"
	testDatas := []*TestData{
		{
			Id:   "1",
			Name: "Test Item",
		}, {
			Id:   "2",
			Name: "Another Item",
		},
	}

	for _, testData := range testDatas {
		sut.Save(testData, testDB)
	}

	// Act
	data, err := sut.List("Name", "abc", testDB)

	// Assert
	if err != nil {
		t.Errorf("Expected no error, but got %v", err)
		return
	}

	if len(data) != 0 {
		t.Error("Expected data, but got empty list")
		return
	}
}
