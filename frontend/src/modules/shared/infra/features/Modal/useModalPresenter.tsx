import { useEffect, useState } from 'react';
import { useModalContext } from './ModalContext';
import { useBusSubscriber } from '../../hooks/useBusSubscriber';

export function useModalPresenter({ event }: Props) {
  const { bus } = useModalContext();
  const [show, setShow] = useState(false);
  const [openEventId, setOpenEventId] = useState<string>();
  const [message, setMessage] = useState<any>();

  const onClose = () => setShow(false);

  useBusSubscriber({
    bus,
    eventName: event,
    handler: (msg) => {
      setMessage(msg);
      setOpenEventId(crypto.randomUUID());
    },
  });

  useEffect(() => {
    if (!openEventId) return;
    setShow(true);
  }, [openEventId]);

  return { show, message, onClose };
}

type Props = {
  event: string;
};
