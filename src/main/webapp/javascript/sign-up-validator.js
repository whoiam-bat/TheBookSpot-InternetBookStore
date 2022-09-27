// read all values from form by id
const fullnameEl = document.querySelector('#fullname');
const emailEl = document.querySelector('#email');
const passwordEl = document.querySelector('#password');

const form = document.querySelector('#signup');



// return true if the input element is empty
const isRequired = value => value === '' ? false : true;

// ------------------------------------------------
//      Fields validation
//-------------------------------------------------

// check is email valid
const isEmailValid = (email) => {
    const regEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3})|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regEmail.test(email);
};

// check is password valid
const isPasswordValid = (password) => {
    const regPassword = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,12})");
    return regPassword.test(password);
};

// check is full name valid
const isFullNameValid = (fullName) => {
    const regFullName = /^[a-zA-Z]{1,127} [a-zA-Z-]{1,127}$/
    return regFullName.test(fullName);
};



// ------------------------------------------------
//      Functions that shows an error/success
//-------------------------------------------------

const showError = (input, message) => {
    // get the form-field element
    const formField = input.parentElement;

    // add the error class
    formField.classList.remove('success');
    formField.classList.add('error');

    // show the error message
    const error = formField.querySelector('small');

    // set the error message to its textContent property of the <small> element
    error.textContent = message;
};

const showSuccess = (input) => {
    // get the form-field element
    const formField = input.parentElement;

    // add the error class
    formField.classList.remove('error');
    formField.classList.add('success');

    // show the error message
    const error = formField.querySelector('small');

    // set the error message to its textContent property of the <small> element
    error.textContent = '';
};


// ------------------------------------------------
//      Input field validating functions
//-------------------------------------------------

const checkFullName = () => {

    let valid = false;

    const fullname = fullnameEl.value.trim();

    if (!isRequired(fullname)) {
        showError(fullnameEl, 'Full name cannot be blank.');
    } else if (!isFullNameValid(fullname)) {
        showError(fullnameEl, `Full name is not valid.`)
    } else {
        showSuccess(fullnameEl);
        valid = true;
    }
    return valid;
};

const checkEmail = () => {
    let valid = false;

    const email = emailEl.value.trim();

    if(!isRequired(email)) {
        showError(emailEl, 'Email cannot be blank.');
    } else if(!isEmailValid(email)) {
        showError(emailEl, 'Email is not valid.');
    } else {
        showSuccess(emailEl);
        valid = true;
    }
    return valid;
};

const checkPassword = () => {
    let valid =  false;

    const password = passwordEl.value.trim();
    if (!isRequired(password)) {
        showError(passwordEl, 'Password cannot be blank.');
    } else if (!isPasswordValid(password)) {
        showError(passwordEl, 'Password must has at least 8 characters that include at least 1 lowercase character, 1 uppercase characters, 1 number, and 1 special character in (!@#$%^&*)');
    } else {
        showSuccess(passwordEl);
        valid = true;
    }
    return valid;
}



// attach submit event listener using addEventListener()
form.addEventListener('submit', function (e) {

    // prevent the form from submitting
    e.preventDefault();

    // validate forms
    let isFullname = checkFullName(),
        isEmail = checkEmail(),
        isPassword = checkPassword();

    let isFormValid = isFullname &&
        isEmail &&
        isPassword;

    // submit to the server if the form is valid
    if (isFormValid) {
        form.submit();
    }
});


const debounce = (fn, delay = 500) => {
    let timeoutId;
    return (...args) => {
        // cancel the previous timer
        if (timeoutId) {
            clearTimeout(timeoutId);
        }
        // set up a new timer
        timeoutId = setTimeout(() => {
            fn.apply(null, args)
        }, delay);
    };
};

form.addEventListener('input', debounce(function (e) {
    switch (e.target.id) {
        case 'fullname':
            checkFullName();
            break;
        case 'email':
            checkEmail();
            break;
        case 'password':
            checkPassword();
            break;
    }
}));