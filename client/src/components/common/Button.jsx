import { Tooltip, CircularProgress } from "@mui/material"

const Button = ({type, action, selected, loading, disabled, text, icon, tooltipText}) => {
  return (
  {tooltipText ? (
    <Tooltip title={tooltipText}>
      <button
        onClick={action}
        disabled={disabled || loading}
        className={`button ${type} ${selected ? "selected" : ""}`}
      >
        {loading ? (
          <div className="loader"></div>)
        ) : (
          <>
            {icon && <span className="material-icons icon">{icon}</span>}
            { loading ? <CircularProgress size={20} /> : <span className="button-text">{text}</span> }
          </>
        )}
      </button>
    </Tooltip>
  ) : (
    <button
      onClick={action}
      disabled={disabled}
      className={`button ${type} ${selected ? "selected" : ""}`}
    >
      {loading ? (
        <div className="loader"></div>
      ) : (
        <>
          {icon && <span className="material-icons">{icon}</span>}
          <span>{text}</span>
        </>
      )}
    </button>
  )})}

export default Button