export interface BusSubscriber {
  subscribe(name: string, handler: BusHandler): string;
  unsubscribe(handlerId: string): void;
}

export type BusHandler = (message?: any) => void;
