import { useState } from "react";

const useForm = (initialValue, requiredFields, validations) => {
  const [values, setValues] = useState(initialValue);
  const [errors, setErrors] = useState({});
  const [isValid, setIsValid] = useState(false);

  const handleChange = (e) => {
    setValues({
      ...values,
      [e.target.name]: e.target.value,
    });
  };

  const handleBlur = (e) => {
    const { name, value } = e.target;
    const error = validateField(name, value);
    if (error === "" && errors[name]) {
      delete errors[name];
    } else if (error !== "") {
      setErrors({
        ...errors,
        [name]: error,
      });
    } else {
      return;
    }
  };

  const validateField = (name, value) => {
    if (requiredFields.includes(name) && value === "") {
      return `${name} is required`;
    }
    if (validations[name] && !validations[name].test(value)) {
      return `Invalid ${name}`;
    }
    return "";
  };

  const validateForm = () => {
    const newErrors = {};
    Object.keys(values).forEach((name) => {
      const error = validateField(name, values[name]);
      if (error !== "") {
        newErrors[name] = error;
      }
    });
    setErrors(newErrors);
    const isValid = Object.keys(newErrors).length === 0;
    setIsValid(isValid);
    return isValid;
  };

  const clearForm = () => {
    setValues(initialValue);
    setErrors({});
    setIsValid(false);
  };

  return {
    values,
    errors,
    isValid,
    handleChange,
    handleBlur,
    validateForm,
    clearForm,
  };
};

export default useForm;
