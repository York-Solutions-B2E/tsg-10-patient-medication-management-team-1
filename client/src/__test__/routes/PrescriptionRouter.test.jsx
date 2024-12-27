import { render, screen } from "@testing-library/react";
import { describe, expect, test, jest } from "@jest/globals";
import { MemoryRouter } from "react-router-dom";

// Mock the rendered components
jest.mock("../../components/prescriptionsPage/PrescriptionsPage", () => {
  return function mockPrescriptionsPage() {
    return <div>Mocked Prescriptions</div>;
  };
});

jest.mock(
  "../../components/prescriptionCreationPage/PrescriptionCreationPage",
  () => {
    return function mockPrescriptionCreationPage() {
      return <div>Mocked Prescription Creation</div>;
    };
  }
);

import PrescriptionRouter from "../../routes/PrescriptionRouter";

describe("PrescriptionRouter", () => {
  test("should render prescriptions page", () => {
    render(
      <MemoryRouter initialEntries={["/"]}>
        <PrescriptionRouter />
      </MemoryRouter>
    );
    expect(screen.getByText("Mocked Prescriptions")).toBeInTheDocument();
  });

  test("should render prescription creation page", () => {
    render(
      <MemoryRouter initialEntries={["/create"]}>
        <PrescriptionRouter />
      </MemoryRouter>
    );
    expect(
      screen.getByText("Mocked Prescription Creation")
    ).toBeInTheDocument();
  });

  test("should redirect to prescriptions page", () => {
    render(
      <MemoryRouter initialEntries={["/laskdfjlaskfj"]}>
        <PrescriptionRouter />
      </MemoryRouter>
    );
    expect(screen.getByText("Mocked Prescriptions")).toBeInTheDocument();
  });
});
