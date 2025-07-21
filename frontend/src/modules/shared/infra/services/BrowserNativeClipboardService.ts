import { TextClipboardService } from '../../application/ClipboardService';

export class BrowserNativeClipboardService implements TextClipboardService {
  async write(value: string): Promise<void> {
    await navigator.clipboard.writeText(value);
  }

  read(): Promise<string> {
    return navigator.clipboard.readText();
  }
}
