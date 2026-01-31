package game_doubles_test

import "sync"

// MockFileSystem é um dublê para a interface FileSystem
type MockFileSystem struct {
	mu    sync.RWMutex
	files map[string][]byte
}

// NewMockFileSystem cria uma nova instância de MockFileSystem
func NewMockFileSystem() *MockFileSystem {
	return &MockFileSystem{
		files: make(map[string][]byte),
	}
}

// SaveFile salva um arquivo em memória
func (m *MockFileSystem) SaveFile(path string, data []byte) error {
	m.mu.Lock()
	defer m.mu.Unlock()
	m.files[path] = data
	return nil
}

// ReadFile lê um arquivo da memória
func (m *MockFileSystem) ReadFile(path string) ([]byte, error) {
	m.mu.RLock()
	defer m.mu.RUnlock()
	data, exists := m.files[path]
	if !exists {
		return nil, nil
	}
	return data, nil
}

// ExistsFile verifica se um arquivo existe
func (m *MockFileSystem) ExistsFile(path string) bool {
	m.mu.RLock()
	defer m.mu.RUnlock()
	_, exists := m.files[path]
	return exists
}

// CopyFile copia um arquivo de src para dst
func (m *MockFileSystem) CopyFile(src string, dst string) error {
	m.mu.Lock()
	defer m.mu.Unlock()
	data, exists := m.files[src]
	if !exists {
		return nil
	}
	m.files[dst] = data
	return nil
}

// RemoveFile remove um arquivo
func (m *MockFileSystem) RemoveFile(path string) error {
	m.mu.Lock()
	defer m.mu.Unlock()
	delete(m.files, path)
	return nil
}

func (m *MockFileSystem) ListFiles() []string {
	m.mu.RLock()
	defer m.mu.RUnlock()
	paths := make([]string, 0, len(m.files))
	for path := range m.files {
		paths = append(paths, path)
	}
	return paths
}
