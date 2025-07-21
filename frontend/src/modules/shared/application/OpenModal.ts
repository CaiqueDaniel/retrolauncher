import { BusDispacher } from './BusDispacher';
import { UseCase } from './UseCase';

export class OpenModal implements UseCase<Input, void> {
  constructor(private readonly bus: BusDispacher) {}

  execute(input: Input): void {
    this.bus.dispatch(input.eventName, input.data);
  }
}

type Input = {
  eventName: string;
  data?: Record<string, any>;
};
