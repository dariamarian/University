var matrix = [1, 1, 2, 2, 3, 3, 4, 4];

matrix.sort(function () {
    return 0.5 - Math.random();
});

var first = null;
var second = null;

var matchedPairs = 0;

function checkPair() {
    var id1 = first.id;
    var id2 = second.id;

    if (matrix[id1] === matrix[id2]) {
        first.removeEventListener("click", selectie);
        second.removeEventListener("click", selectie);
        first.style.backgroundColor = "hotpink";
        second.style.backgroundColor = "hotpink";

        first = null;
        second = null;

        matchedPairs++;

        if (matchedPairs === matrix.length / 2) {
            alert("You won");
        }
    } else {
        setTimeout(function () {
            first.style.backgroundColor = "#CCC";
            second.style.backgroundColor = "#CCC";
            first.textContent = "";
            second.textContent = "";

            first = null;
            second = null;
        }, 1000);
    }
}

function selectie() {
    if (first && second) {
        return;
    }

    if (this === first) {
        return;
    }

    this.textContent = matrix[this.id];

    if (!first) {
        first = this;
        return;
    }

    second = this;
    checkPair();
}

var elements = document.getElementsByTagName("td");
for (var i = 0; i < elements.length; i++) {
    elements[i].addEventListener("click", selectie);
}