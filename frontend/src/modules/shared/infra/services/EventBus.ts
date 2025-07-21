import { BusDispacher } from '../../application/BusDispacher';
import {
  BusSubscriber,
  BusHandler,
} from '../../application/BusSubscriber';

export class EventBus implements BusSubscriber, BusDispacher {
  private readonly eventBuses = new Map<string, Map<string, BusHandler>>();
  private static instance?: EventBus;

  private constructor() {}

  static getInstance() {
    if (EventBus.instance) return EventBus.instance;
    EventBus.instance = new EventBus();
    return EventBus.instance;
  }

  subscribe(name: string, handler: BusHandler): string {
    const handlerId = crypto.randomUUID();
    const handlers = this.eventBuses.get(name) ?? new Map<string, BusHandler>();

    handlers.set(handlerId, handler);
    this.eventBuses.set(name, handlers);

    return handlerId;
  }

  unsubscribe(handlerId: string): void {
    Array.from(this.eventBuses.keys()).forEach((queueName) => {
      const handlers = this.eventBuses.get(queueName);
      if (!handlers) return;
      handlers.delete(handlerId);
    });
  }

  dispatch(name: string, message?: any): void {
    const handlers = this.eventBuses.get(name) ?? [];
    handlers.forEach((handler) => handler(message));
  }

  clear() {
    this.eventBuses.clear();
  }
}
