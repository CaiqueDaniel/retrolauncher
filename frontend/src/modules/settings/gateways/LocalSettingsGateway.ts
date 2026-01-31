import { SettingsService } from "../services/SettingsGateway";
import { SettingsFormData } from "../services/SettingsGateway";
import { Save, Get } from "~/../wailsjs/go/desktop/settingsController";

export class LocalSettingsGateway implements SettingsService {
  async saveSettings(data: SettingsFormData): Promise<void> {
    await Save(data);
  }

  async getSettings(): Promise<SettingsFormData> {
    const settings = await Get();
    return {
      romsFolderPath: settings.RomsFolderPath,
      retroarchFolderPath: settings.RetroarchFolderPath,
    };
  }
}
