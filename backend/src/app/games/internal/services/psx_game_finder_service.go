package services

import (
	"bufio"
	"os"
	"path/filepath"
	"regexp"
	"retrolauncher/backend/src/app/games/internal/application"
	"strings"
)

type psxGameFinderService struct{}

func NewPSXGameFinderService() application.GameFinderService {
	return &psxGameFinderService{}
}

func (s *psxGameFinderService) GetFilesFrom(folder string) []string {
	files, err := os.ReadDir(folder)
	if err != nil {
		return []string{}
	}

	var identifiedFiles []string
	for _, file := range files {
		if file.IsDir() {
			continue
		}

		filePath := filepath.Join(folder, file.Name())

		if !s.isCUEFile(file.Name()) {
			continue
		}

		binFile, ok := s.getBinFileFrom(filePath)
		if !ok {
			continue
		}

		if !s.isPSXFile(binFile) {
			continue
		}

		identifiedFiles = append(identifiedFiles, filePath)
	}

	return identifiedFiles
}

func (s *psxGameFinderService) isCUEFile(fileName string) bool {
	return strings.HasSuffix(strings.ToLower(fileName), ".cue")
}

func (s *psxGameFinderService) getBinFileFrom(cueFilePath string) (string, bool) {
	pattern := regexp.MustCompile(`FILE "([^"]+)" BINARY`)
	binFileName := s.searchForContentOnFile(cueFilePath, pattern)

	if binFileName == "" {
		return "", false
	}

	binFilePath := filepath.Join(filepath.Dir(cueFilePath), binFileName)

	if _, err := os.Stat(binFilePath); os.IsNotExist(err) {
		return "", false
	}

	return binFilePath, true
}

func (s *psxGameFinderService) isPSXFile(binFilePath string) bool {
	pattern := regexp.MustCompile(`(Sony Computer Entertainment)`)
	return s.searchForContentOnFile(binFilePath, pattern) != ""
}

func (s *psxGameFinderService) searchForContentOnFile(filePath string, pattern *regexp.Regexp) string {
	file, err := os.Open(filePath)
	if err != nil {
		return ""
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	// Aumentar o buffer para arquivos binários grandes
	buf := make([]byte, 0, 64*1024)
	scanner.Buffer(buf, 1024*1024)

	for scanner.Scan() {
		line := scanner.Text()
		matches := pattern.FindStringSubmatch(line)
		if len(matches) > 1 {
			return matches[1]
		}
	}

	return ""
}
