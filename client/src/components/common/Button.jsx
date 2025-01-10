import { Tooltip, CircularProgress } from "@mui/material";

const Button = ({
  type,
  action,
  selected,
  loading,
  disabled,
  text,
  icon,
  tooltipText,
  classNames,
}) => {
  return (
    <>
      {tooltipText ? (
        <Tooltip title={tooltipText}>
          <button
            onClick={action}
            disabled={disabled || loading}
            className={`button ${type} ${classNames} ${
              selected ? "selected" : ""
            }`}
            style={{
              backgroundColor: "#ff6f61", // Add your styles here
              color: "black",
              border: "1px solid black",
              boxShadow: "5px 5px black",
            }}
          >
            <span>
              {loading ? (
                <CircularProgress size={20} />
              ) : (
                <>
                  {icon && <span>{icon}</span>}
                  {text && <span>{text}</span>}
                </>
              )}
            </span>
          </button>
        </Tooltip>
      ) : (
        <button
          onClick={action}
          disabled={disabled || loading}
          className={`button ${type} ${selected ? "selected" : ""}`}
          style={{
            backgroundColor: "#ffd755", // Golden yellow
            color: "black",
            border: "1px solid black",
            boxShadow: "5px 5px black", // Drop shadow
            padding: "10px 20px", // Added padding for spacing
            borderRadius: "5px", // Lightly rounded corners
          }}
        >
          <span>
            {loading ? (
              <CircularProgress size={20} />
            ) : (
              <>
                {icon && <span>{icon}</span>}
                {text && <span>{text}</span>}
              </>
            )}
          </span>
        </button>
      )}
    </>
  );
};

export default Button;
