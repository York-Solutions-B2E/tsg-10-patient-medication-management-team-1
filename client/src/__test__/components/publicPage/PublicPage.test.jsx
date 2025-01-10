import {describe, expect, it, jest, afterEach} from '@jest/globals';
import { render, screen, fireEvent } from '@testing-library/react';
import { useAppContext } from '../../../context/AppContext';
import PublicPage from '../../../components/publicPage/PublicPage';

// Mock the useAppContext hook
jest.mock('../../../context/AppContext.jsx');

describe('PublicPage', () => {
        afterEach(() => { jest.clearAllMocks(); });

  it('renders the header correctly', () => {
    useAppContext.mockReturnValue({
      handleLogin: jest.fn(),
      isLoading: false,
    });

    render(<PublicPage />);
    expect(screen.getByText('The Medical Prescription Manager')).toBeInTheDocument();
  });

  it('renders the Login button with the correct initial state', () => {
    useAppContext.mockReturnValue({
      handleLogin: jest.fn(),
      isLoading: false,
    });

    render(<PublicPage />);
    const loginButton = screen.getByRole('button', { name: /login/i });
    expect(loginButton).toBeInTheDocument();
    expect(loginButton).toBeEnabled();
  });

  it('disables the button and shows "Loading..." when isLoading is true', () => {
    useAppContext.mockReturnValue({
      handleLogin: jest.fn(),
      isLoading: true,
    });

    render(<PublicPage />);
    const loginButton = screen.getByRole('button', { name: /loading/i });
    expect(loginButton).toBeDisabled();
  });

  it('calls handleLogin when the button is clicked', () => {
    const handleLoginMock = jest.fn();
    useAppContext.mockReturnValue({
      handleLogin: handleLoginMock,
      isLoading: false,
    });

    render(<PublicPage />);
    const loginButton = screen.getByRole('button', { name: /login/i });
    fireEvent.click(loginButton);

    expect(handleLoginMock).toHaveBeenCalledTimes(1);
  });
});