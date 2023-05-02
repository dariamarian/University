var matrix = [1, 1, 2, 2, 3, 3, 4, 4];

matrix.sort(function () {
    return 0.5 - Math.random();
});

var first = null;
var second = null;

var matchedPairs = 0;

function checkPair() {
    var id1 = $(first).attr("id");
    var id2 = $(second).attr("id");

    if (matrix[id1] === matrix[id2]) {
        $(first).off("click", selectie);
        $(second).off("click", selectie);
        $(first).css("background-color", "hotpink");
        $(second).css("background-color", "hotpink");

        first = null;
        second = null;

        matchedPairs++;

        if (matchedPairs === matrix.length / 2) {
            alert("You won");
        }
    } else {
        setTimeout(function () {
            $(first).css("background-color", "#CCC");
            $(second).css("background-color", "#CCC");
            $(first).text("");
            $(second).text("");

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

    $(this).text(matrix[$(this).attr("id")]);

    if (!first) {
        first = this;
        return;
    }

    second = this;
    checkPair();
}

var elements = $("td");
for (var i = 0; i < elements.length; i++) {
    $(elements[i]).on("click", selectie);
}
