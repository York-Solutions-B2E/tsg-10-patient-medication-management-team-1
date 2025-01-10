import { useMemo } from "react";
import useForm from "../../hooks/useForm";
import { required, email, phone, patientId } from "../../utils/validations";
import {
  TextField,
  MenuItem,
  Checkbox,
  RadioGroup,
  Radio,
  FormControlLabel,
  Autocomplete,
  Divider,
} from "@mui/material";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import dayjs from "dayjs";
import Button from "./Button";

const Form = ({
  fields,
  onSubmit,
  isLoading,
  clearable,
  classNames,
  title,
}) => {
  /**
   * Get the validation test function based on the validation type
   *
   * @param {String} validation
   * @returns
   */
  const getValidationTest = (validation) => {
    switch (validation) {
      case "email":
        return email;
      case "phone":
        return phone;
      case "patientId":
        return patientId;
      default:
        return required;
    }
  };

  /**
   * Initial values for the form fields.
   */
  const initialValues = fields.reduce((acc, field) => {
    if (field.type !== "divider") {
      acc[field.name] = field.defaultValue || "";
    }
    return acc;
  }, {});

  /**
   * List of required field names.
   */
  const requiredFields = fields
    .filter((field) => field.required)
    .map((field) => field.name);

  /**
   * Object containing validation functions for each field with validation.
   */
  const validations = fields.reduce((acc, field) => {
    if (field.validation) {
      acc[field.name] = { test: getValidationTest(field.validation) };
    }
    return acc;
  }, {});

  const {
    values,
    errors,
    isValid,
    handleChange,
    handleBlur,
    validateForm,
    clearForm,
  } = useForm(initialValues, requiredFields, validations);

  const selectedValue = useMemo(() => {
    return fields.reduce((acc, field) => {
      if (field.type === "select" && field.autocomplete) {
        const selected = field.options.find(
          (option) => option.value === values[field.name]
        );
        acc[field.name] = selected || null;
      }
      return acc;
    }, {});
  }, [fields, values]);

  return (
    <div className={`form` + classNames ? classNames : ""}>
      {title && <h2>{title}</h2>}
      {fields.map((field) => {
        switch (field.type) {
          case "text":
          case "email":
          case "password":
            return (
              <TextField
                key={field.name}
                hidden={field.hidden || false}
                name={field.name}
                label={field.label}
                type={field.type}
                value={values[field.name]}
                onChange={handleChange}
                onBlur={handleBlur}
                error={errors[field.name] ? true : false}
                helperText={errors[field.name]}
              />
            );
          case "select":
            return (
              <>
                {field.autocomplete ? (
                  <Autocomplete
                    key={field.name}
                    options={field.options}
                    value={selectedValue.value}
                    onChange={(e, v) => {
                      console.log(v);
                      handleChange({
                        target: {
                          name: field.name,
                          value: v.value,
                        },
                      });
                    }}
                    renderInput={(params) => (
                      <TextField
                        {...params}
                        label={field.label}
                        error={errors[field.name] ? true : false}
                        helperText={errors[field.name]}
                      />
                    )}
                  />
                ) : (
                  <TextField
                    key={field.name}
                    name={field.name}
                    select
                    label={field.label}
                    value={values[field.name]}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    error={errors[field.name] ? true : false}
                    helperText={errors[field.name]}
                  >
                    {field.options.map((option) => (
                      <MenuItem key={option.value} value={option.value}>
                        {option.label}
                      </MenuItem>
                    ))}
                  </TextField>
                )}
              </>
            );
          case "checkbox":
            return (
              <FormControlLabel
                key={field.name}
                control={
                  <Checkbox
                    name={field.name}
                    checked={values[field.name]}
                    onChange={handleChange}
                  />
                }
                label={field.label}
              />
            );
          case "radio":
            return (
              <RadioGroup
                key={field.name}
                name={field.name}
                value={values[field.name]}
                onChange={handleChange}
              >
                {field.options.map((option) => (
                  <FormControlLabel
                    key={option.value}
                    value={option.value}
                    control={<Radio />}
                    label={option.label}
                  />
                ))}
              </RadioGroup>
            );
          case "date":
            return (
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker
                  key={field.name}
                  name={field.name}
                  label={field.label}
                  value={dayjs(values[field.name])}
                  onChange={(d) =>
                    handleChange({
                      target: {
                        name: field.name,
                        value: dayjs(d).format("YYYY-MM-DD"),
                      },
                    })
                  }
                  views={field.views || ["year", "month", "day"]}
                  openTo={field.openTo || "year"}
                  onBlur={handleBlur}
                  disableFuture={field.disableFuture || false}
                  disablePast={field.disablePast || false}
                  disableOpenPicker={field.disableOpenPicker || false}
                  format={field.format || "YYYY-MM-DD"}
                  error={errors[field.name] ? true : false}
                  helperText={errors[field.name]}
                  loading={isLoading || false}
                  shouldDisableDate={field.shouldDisableDate || null}
                  disableDate={field.disableDate || null}
                  disableMonth={field.disableMonth || null}
                  disableYear={field.disableYear || null}
                  renderInput={(params) => <TextField {...params} />}
                />
              </LocalizationProvider>
            );
          case "number":
            return (
              <TextField
                key={field.name}
                hidden={field.hidden || false}
                name={field.name}
                label={field.label}
                type="number"
                value={values[field.name]}
                onChange={handleChange}
                onBlur={handleBlur}
                error={errors[field.name] ? true : false}
                helperText={errors[field.name]}
              />
            );
          case "divider":
            return (
              <div key={field.label} className="form-divider">
                <span>{field.label}</span>
                <Divider key={field.label} />
              </div>
            );
          default:
            return null;
        }
      })}
      <div className="action-section">
        <Button
          type="submit"
          action={() => {
            console.log("submitting");
            if (validateForm() || isValid) {
              onSubmit(values);
            }
          }}
          loading={isLoading}
          text="Submit"
          disabled={false}
        />
        {clearable && (
          <Button
            type="secondary"
            action={() => {
              clearForm();
            }}
            text="Clear"
          />
        )}
      </div>
    </div>
  );
};

export default Form;
