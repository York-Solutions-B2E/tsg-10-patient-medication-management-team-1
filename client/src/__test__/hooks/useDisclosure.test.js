import { renderHook, act } from "@testing-library/react-hooks";
import { describe, it, expect } from "@jest/globals";

import useDisclosure from "../../hooks/useDisclosure";

describe("useDisclosure", () => {
  it("should return the initial state of isOpen as false", () => {
    const { result } = renderHook(() => useDisclosure());
    expect(result.current.isOpen).toBe(false);
  });

  it("should set isOpen to true when onOpen is called", () => {
    const { result } = renderHook(() => useDisclosure());
    act(() => {
      result.current.onOpen();
    });
    expect(result.current.isOpen).toBe(true);
  });

  it("should set isOpen to false when onClose is called", () => {
    const { result } = renderHook(() => useDisclosure());
    act(() => {
      result.current.onOpen();
    });
    act(() => {
      result.current.onClose();
    });
    expect(result.current.isOpen).toBe(false);
  });
});
