export interface ClipboardService<T> {
  write(value: T): Promise<void>;
  read(): Promise<T>;
}

export interface TextClipboardService extends ClipboardService<string> {}
