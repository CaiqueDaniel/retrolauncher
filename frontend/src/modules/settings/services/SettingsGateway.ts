
export interface SettingsService {
    saveSettings(data: SettingsFormData): Promise<void>;
    getSettings(): Promise<SettingsFormData>;
}

export type SettingsFormData = {
    romsFolderPath: string;
    retroarchFolderPath: string;
};

