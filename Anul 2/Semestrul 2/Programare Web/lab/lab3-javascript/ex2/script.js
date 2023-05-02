const labels = document.querySelectorAll('.form-control label')
const btn = document.getElementById('btn')

function validateName(name) {
    if (name == '')
        return false;
    var regName = /^[a-zA-Z]+ [a-zA-Z]+$/;
    return regName.test(name);
}

function validateBirthDate(birthdate) {
    if (birthdate == '')
        return false;
    var regBirthDate = /^([0-9]{4})\/([0-9]{2})\/([0-9]{2})$/;
    return regBirthDate.test(birthdate);
}

function validateAge(age) {
    if (age == '')
        return false;
    var regAge = /^[0-9]+$/;
    return regAge.test(age);
}

function validateEmail(email) {
    if (email == '')
        return false;
    var regEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return regEmail.test(email);
}

btn.addEventListener('click', () => {
    index = 0
    const inputs = document.querySelectorAll('.form-control input')
    name = inputs[0].value
    birthdate = inputs[1].value
    age = inputs[2].value
    email = inputs[3].value
    error = ''

    if (!validateName(name)) {
        error += 'Name not valid! '
        inputs[0].style.borderBottom = '2px solid red'
    } else if (validateName(name)) {
        inputs[0].style.borderBottom = '2px solid #fff'
    }
    if (!validateBirthDate(birthdate)) {
        error += 'Birthdate not valid! '
        inputs[1].style.borderBottom = '2px solid red'
    } else if (validateBirthDate(birthdate)) {
        inputs[1].style.borderBottom = '2px solid #fff'
    }
    if (!validateAge(age)) {
        error += 'Age not valid! '
        inputs[2].style.borderBottom = '1px solid red'
    } else if (validateAge(age)) {
        inputs[2].style.borderBottom = '2px solid #fff'
    }
    if (!validateEmail(email)) {
        error += 'Email not valid! '
        inputs[3].style.borderBottom = '2px solid red'
    } else if (validateEmail(email)) {
        inputs[3].style.borderBottom = '2px solid #fff'
    }
    if (error)
        alert(error)
    else {
        alert('Success!')
        inputs.forEach(input => {
            input.value = ''
            input.style.borderBottom = '2px #fff solid'
        })
    }
})

labels.forEach(label => {
    // se transforma HTML-ul in span-uri
    label.innerHTML = label.innerText //se ia fiecare litera
    // din label
    .split('') //se imparte intr-un array
    .map((letter, idx) => `<span 
    style="transition-delay:${idx * 50}ms">${letter}</span>`) //array of
    // spans
    .join('')
})