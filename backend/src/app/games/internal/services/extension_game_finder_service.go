package services

import (
	"os"
	"path/filepath"
	"retrolauncher/backend/src/app/games/internal/application"
	"strings"
)

// extensionGameFinderService é a implementação base que filtra por extensões
type extensionGameFinderService struct {
	extensions []string
}

func newExtensionGameFinderService(extensions []string) *extensionGameFinderService {
	return &extensionGameFinderService{
		extensions: extensions,
	}
}

func (s *extensionGameFinderService) GetFilesFrom(folder string) []string {
	files, err := os.ReadDir(folder)
	if err != nil {
		return []string{}
	}

	var identifiedFiles []string
	for _, file := range files {
		if file.IsDir() {
			continue
		}
		if s.isFileWithinAllowedExtensions(file.Name()) {
			identifiedFiles = append(identifiedFiles, filepath.Join(folder, file.Name()))
		}
	}

	return identifiedFiles
}

func (s *extensionGameFinderService) isFileWithinAllowedExtensions(fileName string) bool {
	for _, ext := range s.extensions {
		if strings.HasSuffix(strings.ToLower(fileName), "."+ext) {
			return true
		}
	}
	return false
}

// NES Game Finder
type nesGameFinderService struct {
	*extensionGameFinderService
}

func NewNESGameFinderService() application.GameFinderService {
	return &nesGameFinderService{
		extensionGameFinderService: newExtensionGameFinderService([]string{"nes"}),
	}
}

// SNES Game Finder
type snesGameFinderService struct {
	*extensionGameFinderService
}

func NewSNESGameFinderService() application.GameFinderService {
	return &snesGameFinderService{
		extensionGameFinderService: newExtensionGameFinderService([]string{"sfc"}),
	}
}

// GB/GBC Game Finder
type gbGameFinderService struct {
	*extensionGameFinderService
}

func NewGBGameFinderService() application.GameFinderService {
	return &gbGameFinderService{
		extensionGameFinderService: newExtensionGameFinderService([]string{"gb", "gbc"}),
	}
}

// N64 Game Finder
type n64GameFinderService struct {
	*extensionGameFinderService
}

func NewN64GameFinderService() application.GameFinderService {
	return &n64GameFinderService{
		extensionGameFinderService: newExtensionGameFinderService([]string{"n64", "z64"}),
	}
}

// SMS Game Finder
type smsGameFinderService struct {
	*extensionGameFinderService
}

func NewSMSGameFinderService() application.GameFinderService {
	return &smsGameFinderService{
		extensionGameFinderService: newExtensionGameFinderService([]string{"sms"}),
	}
}
