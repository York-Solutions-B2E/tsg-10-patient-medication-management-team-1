import { describe, it, expect, afterEach, jest } from "@jest/globals";
import { render, screen, fireEvent } from "@testing-library/react";
import Button from "../../../components/common/Button";

describe("Button Component", () => {
  const mockAction = jest.fn();

  afterEach(() => {
    jest.clearAllMocks();
  });

  it("renders correctly without tooltip", () => {
    render(
      <Button
        type="primary"
        action={mockAction}
        selected={false}
        loading={false}
        disabled={false}
        text="Click Me"
        icon={<span>Icon</span>}
      />
    );

    const button = screen.getByRole("button");
    expect(button).toBeInTheDocument();
    expect(button).not.toBeDisabled();
    expect(button).toHaveClass("button primary");
    expect(screen.getByText("Icon")).toBeInTheDocument();
    expect(screen.getByText("Click Me")).toBeInTheDocument();
  });

  it("renders correctly with tooltip", () => {
    render(
      <Button
        type="secondary"
        action={mockAction}
        selected={true}
        loading={false}
        disabled={false}
        text="Tooltip Button"
        icon={<span>Icon</span>}
        tooltipText="Tooltip Text"
      />
    );

    const button = screen.getByRole("button");
    const tooltip = screen.getByLabelText("Tooltip Text");
    expect(tooltip).toBeInTheDocument();
    expect(button).toBeInTheDocument();
    expect(button).not.toBeDisabled();
    expect(button).toHaveClass("button secondary selected");
  });

  it("disables the button when loading is true", () => {
    render(
      <Button
        type="primary"
        action={mockAction}
        selected={false}
        loading={true}
        disabled={false}
        text="Loading Button"
        icon={null}
      />
    );

    const button = screen.getByRole("button");
    expect(button).toBeDisabled();
    expect(screen.getByRole("progressbar")).toBeInTheDocument();
  });

  it("disables the button when disabled is true", () => {
    render(
      <Button
        type="primary"
        action={mockAction}
        selected={false}
        loading={false}
        disabled={true}
        text="Disabled Button"
        icon={null}
      />
    );

    const button = screen.getByRole("button", { name: "Disabled Button" });
    expect(button).toBeDisabled();
  });

  it("calls the action function when clicked", () => {
    render(
      <Button
        type="primary"
        action={mockAction}
        selected={false}
        loading={false}
        disabled={false}
        text="Clickable Button"
        icon={null}
      />
    );

    const button = screen.getByRole("button", { name: "Clickable Button" });
    fireEvent.click(button);
    expect(mockAction).toHaveBeenCalledTimes(1);
  });

  it("handles both icon and text being null", () => {
    render(
      <Button
        type="primary"
        action={mockAction}
        selected={false}
        loading={false}
        disabled={false}
        text={null}
        icon={null}
      />
    );

    const button = screen.getByRole("button");
    expect(button).toBeInTheDocument();
    expect(button).toHaveTextContent("");
  });

  it("applies the selected class when selected is true", () => {
    render(
      <Button
        type="primary"
        action={mockAction}
        selected={true}
        loading={false}
        disabled={false}
        text="Selected Button"
        icon={null}
      />
    );

    const button = screen.getByRole("button", { name: "Selected Button" });
    expect(button).toHaveClass("selected");
  });
});
