const labels = $('.form-control label');
const btn = $('#btn');

function validateName(name) {
    if (name == '') {
        return false;
    }
    var regName = /^[a-zA-Z]+ [a-zA-Z]+$/;
    return regName.test(name);
}

function validateBirthDate(birthdate) {
    if (birthdate == '') {
        return false;
    }
    var regBirthDate = /^([0-9]{4})\/([0-9]{2})\/([0-9]{2})$/;
    return regBirthDate.test(birthdate);
}

function validateAge(age) {
    if (age == '') {
        return false;
    }
    var regAge = /^[0-9]+$/;
    return regAge.test(age);
}

function validateEmail(email) {
    if (email == '') {
        return false;
    }
    var regEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return regEmail.test(email);
}

btn.on('click', () => {
    index = 0
    const inputs = $('.form-control input');
    name = inputs[0].value;
    birthdate = inputs[1].value;
    age = inputs[2].value;
    email = inputs[3].value;
    error = '';

    if (!validateName(name)) {
        error += 'Name not valid! ';
        inputs.eq(0).css('border-bottom', '2px solid red');
    } else if (validateName(name)) {
        inputs.eq(0).css('border-bottom', '2px solid #fff');
    }
    if (!validateBirthDate(birthdate)) {
        error += 'Birthdate not valid! ';
        inputs.eq(1).css('border-bottom', '2px solid red');
    } else if (validateBirthDate(birthdate)) {
        inputs.eq(1).css('border-bottom', '2px solid #fff');
    }
    if (!validateAge(age)) {
        error += 'Age not valid! ';
        inputs.eq(2).css('border-bottom', '1px solid red');
    } else if (validateAge(age)) {
        inputs.eq(2).css('border-bottom', '2px solid #fff');
    }
    if (!validateEmail(email)) {
        error += 'Email not valid! ';
        inputs.eq(3).css('border-bottom', '2px solid red');
    } else if (validateEmail(email)) {
        inputs.eq(3).css('border-bottom', '2px solid #fff');
    }
    if (error) {
        alert(error);
    } else {
        alert('Success!');
        inputs.each(function() {
            $(this).val('');
            $(this).css('border-bottom', '2px #fff solid');
        });
    }
});

labels.each(function() {
    // se transforma HTML-ul in span-uri
    $(this).html($(this).text() //se ia fiecare litera
        // din label
        .split('') //se imparte intr-un array
        .map(function(letter, idx) {
            return `<span style="transition-delay:${idx * 50}ms">${letter}</span>`;
        }) //array of spans
        .join('')
    );
});
