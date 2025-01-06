import { jest, describe, it, expect, afterEach } from "@jest/globals";
import { render, screen, fireEvent } from "@testing-library/react";
import ConfirmModal from "../../../components/common/BasicModal";

// Mock the Button component
jest.mock("../../../components/common/Button", () =>
  jest.fn(({ icon, text, action, loading }) => (
    <button onClick={action} disabled={loading}>
      {icon} {text}
    </button>
  ))
);

describe("ConfirmModal", () => {
  const mockOnClose = jest.fn();
  const mockAction = jest.fn();

  afterEach(() => {
    jest.clearAllMocks();
  });

  it("renders correctly when open", () => {
    render(
      <ConfirmModal
        isOpen
        title="Test Title"
        content="Test Content"
        action={mockAction}
        isLoading={false}
        onClose={mockOnClose}
      />
    );

    expect(screen.getByText("Test Title")).toBeInTheDocument();
    expect(screen.getByText("Test Content")).toBeInTheDocument();
    expect(screen.getByText("Confirm")).toBeInTheDocument();
    expect(screen.getByText("Cancel")).toBeInTheDocument();
  });

  it("renders correctly when closed", () => {
    const { container } = render(
      <ConfirmModal isOpen={false} onClose={mockOnClose} />
    );
    expect(container.querySelector(".MuiModal-root")).toBeNull();
  });

  it("renders default buttons correctly when no action is provided", () => {
    render(<ConfirmModal isOpen onClose={mockOnClose} />);

    expect(screen.queryByText("Confirm")).not.toBeInTheDocument();
    expect(screen.getByText("Close")).toBeInTheDocument();
  });

  it("calls the action function when the Confirm button is clicked", () => {
    render(<ConfirmModal isOpen action={mockAction} onClose={mockOnClose} />);

    fireEvent.click(screen.getByText("Confirm"));
    expect(mockAction).toHaveBeenCalledTimes(1);
  });

  it("calls the onClose function when the Cancel button is clicked", () => {
    render(<ConfirmModal isOpen action={mockAction} onClose={mockOnClose} />);

    fireEvent.click(screen.getByText("Cancel"));
    expect(mockOnClose).toHaveBeenCalledTimes(1);
  });

  it("disables buttons when isLoading is true", () => {
    render(
      <ConfirmModal
        isOpen
        isLoading
        action={mockAction}
        onClose={mockOnClose}
      />
    );

    const confirmButton = screen.getByText("Confirm").closest("button");
    const cancelButton = screen.getByText("Cancel").closest("button");

    expect(confirmButton).toBeDisabled();
    expect(cancelButton).toBeDisabled();
  });

  it("renders children correctly", () => {
    render(
      <ConfirmModal isOpen>
        <div>Child Content</div>
      </ConfirmModal>
    );

    expect(screen.getByText("Child Content")).toBeInTheDocument();
  });

  it("renders title and content conditionally", () => {
    const { rerender } = render(<ConfirmModal isOpen />);

    expect(screen.queryByText("Test Title")).not.toBeInTheDocument();
    expect(screen.queryByText("Test Content")).not.toBeInTheDocument();

    rerender(<ConfirmModal isOpen title="Test Title" content="Test Content" />);
    expect(screen.getByText("Test Title")).toBeInTheDocument();
    expect(screen.getByText("Test Content")).toBeInTheDocument();
  });
});
