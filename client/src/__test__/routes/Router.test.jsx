import { render, screen } from "@testing-library/react";
import { describe, expect, test, jest } from "@jest/globals";
import { MemoryRouter } from "react-router-dom";

// Mock the rendered components
jest.mock("../../routes/PrescriptionRouter", () => {
  return function mockPrescriptionRouter() {
    return <div>Mocked Prescription Router</div>;
  };
});

jest.mock("../../components/patientsPage/PatientsPage", () => {
  return function mockPatientsPage() {
    return <div>Mocked Patients</div>;
  };
});

jest.mock("../../components/publicPage/PublicPage", () => {
  return function mockPublicPage() {
    return <div>Mocked Public</div>;
  };
});

import Router from "../../routes/Router";

describe("Router", () => {
  test("should render public page", () => {
    render(
      <MemoryRouter>
        <Router isLoggedIn={false} />
      </MemoryRouter>
    );
    expect(screen.getByText("Mocked Public")).toBeInTheDocument();
  });

  test("should render patients page", () => {
    render(
      <MemoryRouter initialEntries={["/patients"]}>
        <Router isLoggedIn={true} />
      </MemoryRouter>
    );
    expect(screen.getByText("Mocked Patients")).toBeInTheDocument();
  });

  test("should render prescription router", () => {
    render(
      <MemoryRouter initialEntries={["/prescriptions"]}>
        <Router isLoggedIn={true} />
      </MemoryRouter>
    );
    expect(screen.getByText("Mocked Prescription Router")).toBeInTheDocument();
  });

  test("should redirect to patients page", () => {
    render(
      <MemoryRouter initialEntries={["/laskdfjlaskfj"]}>
        <Router isLoggedIn={true} />
      </MemoryRouter>
    );
    expect(screen.getByText("Mocked Patients")).toBeInTheDocument();
  });
});
