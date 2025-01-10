import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { describe, expect, jest, test, beforeEach } from "@jest/globals";
import { AppProvider, useAppContext } from "../../context/AppContext";
// import ManagementApi from "../../managementApi";
import { useCookies } from "react-cookie";

// Mocking the react-cookie library
jest.mock("react-cookie", () => ({
  useCookies: jest.fn(),
}));

jest.mock("../../managementApi", () => ({
  logout: jest.fn(),
  authenticateDoctorUser: jest.fn(),
  getPatient: jest.fn(),
  getPagePatients: jest.fn(),
  createPatient: jest.fn(),
  updatePatient: jest.fn(),
  deletePatient: jest.fn(),
  createPrescription: jest.fn(),
  cancelPrescription: jest.fn(),
  getPrescription: jest.fn(),
  getPagePrescriptions: jest.fn(),
}));

// Helper component to test context usage
const TestComponent = () => {
  const {
    isLoading,
    doctorUser,
    error,
    alert,
    setAlert,
    newPatientDisc,
    editPatientDisc,
    confirmPatientDeleteDisc,
    confirmRxCancelDisc,
    confirmNewRxDisc,
    handleLogin,
    handleLogout,
    handleGetPatient,
    handleGetPatients,
    handleCreatePatient,
    handleUpdatePatient,
    handleDeletePatient,
    handleCreatePrescription,
    handleCancelPrescription,
    handleGetPrescription,
    handleGetPrescriptions,
  } = useAppContext();

  return (
    <div>
      <button onClick={handleLogin}>Login</button>
      <button onClick={handleLogout}>Logout</button>
      <div>{isLoading ? "Loading..." : "Not Loading"}</div>
      <div>
        {doctorUser
          ? `Dr. ${doctorUser.firstName} ${doctorUser.lastName}`
          : "No doctor"}
      </div>
      <div>{error ? error.message : "No error"}</div>
      <button onClick={handleGetPatient}>Get Patient</button>
      <button onClick={handleGetPatients}>Get Patients</button>
      <button onClick={handleCreatePatient}>Create Patient</button>
      <button onClick={handleUpdatePatient}>Update Patient</button>
      <button onClick={handleDeletePatient}>Delete Patient</button>
      <button onClick={handleCreatePrescription}>Create Prescription</button>
      <button onClick={handleCancelPrescription}>Cancel Prescription</button>
      <button onClick={handleGetPrescription}>Get Prescription</button>
      <button onClick={handleGetPrescriptions}>Get Prescriptions</button>
      <button id="npdisc" onClick={newPatientDisc.onOpen}>
        {newPatientDisc.isOpen}
      </button>
      <button id="epdisc" onClick={editPatientDisc.onOpen}>
        {editPatientDisc.isOpen}
      </button>
      <button id="cpddisc" onClick={confirmPatientDeleteDisc.onOpen}>
        {confirmPatientDeleteDisc.isOpen}
      </button>
      <button id="crxdisc" onClick={confirmRxCancelDisc.onOpen}>
        {confirmRxCancelDisc.isOpen}
      </button>
      <button id="cnrdisc" onClick={confirmNewRxDisc.onOpen}>
        {confirmNewRxDisc.isOpen}
      </button>
      <button
        id="setalert"
        onClick={() => setAlert({ message: "Success", type: "success" })}
      >
        {alert ? alert.message : "No alert"}
      </button>
    </div>
  );
};

// Unit tests for the context and functions
describe("AppContext", () => {
  beforeEach(() => {
    useCookies.mockReturnValue([{}, jest.fn(), jest.fn()]);
  });

  test("should handle login", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("Login"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should handle logout", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("Logout"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should get a patient", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("Get Patient"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should get all patients", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("Get Patients"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should create a patient", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("Create Patient"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should update a patient", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("Update Patient"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should delete a patient", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("Delete Patient"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should create a prescription", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("Create Prescription"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should cancel a prescription", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("Cancel Prescription"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should get a prescription", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("Get Prescription"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should get all prescriptions", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("Get Prescriptions"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  // test("should open new patient dialog", async () => {
  //   const { container } = render(
  //     <AppProvider>
  //       <TestComponent />
  //     </AppProvider>
  //   );

  //   fireEvent.click(screen.getBy());

  //   await waitFor(() => {
  //     expect(container).toMatchSnapshot();
  //   });
  // });

  // test("should open edit patient dialog", async () => {
  //   const { container } = render(
  //     <AppProvider>
  //       <TestComponent />
  //     </AppProvider>
  //   );

  //   fireEvent.click(screen.getByText("false"));

  //   await waitFor(() => {
  //     expect(container).toMatchSnapshot();
  //   });
  // });

  // test("should open confirm patient delete dialog", async () => {
  //   const { container } = render(
  //     <AppProvider>
  //       <TestComponent />
  //     </AppProvider>
  //   );

  //   fireEvent.click(screen.getByText("false"));

  //   await waitFor(() => {
  //     expect(container).toMatchSnapshot();
  //   });
  // });

  // test("should open confirm prescription cancel dialog", async () => {
  //   const { container } = render(
  //     <AppProvider>
  //       <TestComponent />
  //     </AppProvider>
  //   );

  //   fireEvent.click(screen.getByText("false"));

  //   await waitFor(() => {
  //     expect(container).toMatchSnapshot();
  //   });
  // });

  // test("should open confirm new prescription dialog", async () => {
  //   const { container } = render(
  //     <AppProvider>
  //       <TestComponent />
  //     </AppProvider>
  //   );

  //   fireEvent.click(screen.getByText("false"));

  //   await waitFor(() => {
  //     expect(container).toMatchSnapshot();
  //   });
  // });

  test("should set an alert", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("No alert"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should handle error", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("No error"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should handle loading", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("Not Loading"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should handle doctor user", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("No doctor"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should handle error", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("No error"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should handle alert", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("No alert"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should handle alert", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("No alert"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should handle alert", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("No alert"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should handle alert", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("No alert"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should handle alert", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("No alert"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should handle alert", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("No alert"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });

  test("should handle alert", async () => {
    const { container } = render(
      <AppProvider>
        <TestComponent />
      </AppProvider>
    );

    fireEvent.click(screen.getByText("No alert"));

    await waitFor(() => {
      expect(container).toMatchSnapshot();
    });
  });
});
