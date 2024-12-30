import "../styles/Navbar.scss";

const Navbar = ({ isLoggedIn }) => {
  return (
    <nav className="navbar">
      {/* Home Link */}
      <div className="nav-left">
        <div className="nav-item">Home</div>
      </div>

      {/* Title in the Center */}
      <div className="nav-center">Medical Prescription Manager</div>

      {/* Links on the Right */}
      <div className="nav-right">
        <div className="nav-item">Patient Management</div>
        <div className="nav-item">Prescription Management</div>
        <div className="nav-item">New Prescription</div>
      </div>
    </nav>
  );
};

export default Navbar;
