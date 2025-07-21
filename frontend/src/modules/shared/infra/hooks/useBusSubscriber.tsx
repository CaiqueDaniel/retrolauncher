import { useEffect, useState } from 'react';
import { BusSubscriber } from '../../application/BusSubscriber';

export function useBusSubscriber({ bus, eventName, handler }: Props) {
  const [updatedAt] = useState<number>(new Date().getTime());

  useEffect(() => {
    const listenerId = bus.subscribe(eventName, handler);
    return () => bus.unsubscribe(listenerId);
  }, [updatedAt]);
}

type Props = {
  bus: BusSubscriber;
  eventName: string;
  handler: Callback;
};

type Callback = (msg: any) => void;
