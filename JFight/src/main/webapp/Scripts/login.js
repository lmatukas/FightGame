// window.addEventListener("load", function () {
//     // Access the form element...
//     var form = document.getElementById("myForm");
//     // ...and take over its submit event.
//     form.addEventListener("submit", function (event) {
//         event.preventDefault();
//
//     });
// });

var loginCon = document.getElementById("loginContainer"),
    registerCon = document.getElementById("registerContainer"),
    goToReg = document.getElementById("goToReg"),
    goToLog = document.getElementById("goToLog"),
    nameLogin = document.getElementById("nameLogin"),
    passLogin = document.getElementById("passwordLogin"),
    nameReg = document.getElementById("nameRegister"),
    passReg = document.getElementById("passwordRegister"),
    passConReg = document.getElementById("passwordConfirmRegister"),
    emailReg = document.getElementById("emailRegister");

goToReg.onclick = function() {
    loginCon.classList.add('fadeout');
    setTimeout(function() {
        loginCon.classList.add('hide');
        loginCon.classList.remove('fadeout');
        registerCon.classList.remove('hide');
    }, 300);
};

goToLog.onclick = function() {
    registerCon.classList.add('fadeout');
    setTimeout(function() {
        registerCon.classList.add('hide');
        registerCon.classList.remove('fadeout');
        loginCon.classList.remove('hide');
    }, 300);
};

function validatePassword(){
    if(passReg.value != passConReg.value) {
        passConReg.setCustomValidity("Passwords Don't Match");
        passConReg.classList.add("border");
        passConReg.classList.add("border-danger");
        passConReg.classList.remove("border-success");
    } else {
        passConReg.setCustomValidity('');
        passConReg.classList.add("border");
        passConReg.classList.remove("border-danger");
        passConReg.classList.add("border-success");
    }
}

passReg.onchange = validatePassword;
passConReg.onkeyup = validatePassword;



















