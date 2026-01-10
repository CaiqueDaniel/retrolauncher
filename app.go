package main

import (
	"context"

	"github.com/wailsapp/wails/v2/pkg/runtime"
)

// App struct
type App struct {
	ctx context.Context
}

// NewApp creates a new App application struct
func NewApp() *App {
	return &App{}
}

// startup is called when the app starts. The context is saved
// so we can call the runtime methods
func (a *App) startup(ctx context.Context) {
	a.ctx = ctx
}

func (a *App) SelectFile() string {
	options := runtime.OpenDialogOptions{
		Title: "Selecione o arquivo de configuração",
		Filters: []runtime.FileFilter{
			{DisplayName: "Arquivos de Texto"},
		},
	}

	selection, err := runtime.OpenFileDialog(a.ctx, options)
	if err != nil {
		return ""
	}
	return selection
}
