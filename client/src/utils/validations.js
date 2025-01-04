/**
 * Returns true if the value is a valid email.
 *
 * @param {String} value
 * @returns
 */
export const email = (value) => {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
};

/**
 * Returns true if the value is not an empty string.
 *
 * @param {String} value
 * @returns {Boolean}
 */
export const required = (value) => {
  return value !== "";
};

/**
 * Returns true if the value is a valid phone number.
 *
 * A valid phone number is a 10 digit number.
 *
 * @param {String} value
 * @returns {Boolean}
 */
export const phone = (value) => {
  return /^\d{10}$/.test(value);
};

/**
 * Returns true if the value is 8 characters long
 *
 * @param {String} value
 * @returns {Boolean}
 */
export const patientId = (value) => {
  return value.length === 8;
};
