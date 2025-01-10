import { useState } from "react";

/**
 * Hook to handle the state of a modal or dropdown.
 *
 * This is very useful when you need to manage the state of a modal or dropdown
 * from somewhere else in your component or application.
 *
 * @example
 * const loginModalDisc = useDisclosure();
 *
 * <button onClick={loginModalDisc.open}>Open Login Modal</button>
 *
 * <Modal isOpen={loginModalDisc.isOpen} onClose={loginModalDisc.close} />
 *
 * @returns {Object} { isOpen, open, close }
 */
const useDisclosure = () => {
  const [isOpen, setIsOpen] = useState(false);

  const onOpen = () => setIsOpen(true);
  const onClose = () => setIsOpen(false);

  return { isOpen, onOpen, onClose };
};

export default useDisclosure;
