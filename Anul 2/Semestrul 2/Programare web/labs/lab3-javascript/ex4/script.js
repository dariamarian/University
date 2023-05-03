function sorteazaColoana(indexColoana) {
    var table, rows, switching, i, x, y, shouldSwitch;
    table = document.getElementById("tabel");
    switching = true;
    while (switching) {
        switching = false;
        rows = table.rows;
        for (i = 1; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("td")[indexColoana];
            y = rows[i + 1].getElementsByTagName("td")[indexColoana];
            if (parseFloat(x.innerHTML) > parseFloat(y.innerHTML)
                || (indexColoana === 0 && x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase())) {
                shouldSwitch = true;
                break;
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
        }
    }
}

function sorteazaLinia(indexLinie) {
    let table = document.getElementById("tabel2");
    let rows = table.rows;
    let columns = [];
    for (let index = 0; index < rows[0].children.length - 1; index++) {
        columns.push([]);
    }
    for (let index = 0; index < rows.length; index++) {
        for (let nthChild = 1; nthChild < rows[index].children.length; nthChild++) {
            columns[nthChild - 1].push(rows[index].children[nthChild].querySelector("div").innerHTML);
            // nu merge cu td in loc de div
        }
    }

    function ascending(a, b) {
        if (a[indexLinie] < b[indexLinie]) {
            return -1;
        }
        if (a[indexLinie] > b[indexLinie]) {
            return 1;
        }
        return 0;
    }

    function descending(a, b) {
        if (a[indexLinie] > b[indexLinie]) {
            return -1;
        }
        if (a[indexLinie] < b[indexLinie]) {
            return 1;
        }
        return 0;
    }

    let before = columns.toString();
    if (!isNaN(parseInt(columns[0][indexLinie]))) {
        for (let index = 0; index < columns.length; index++) {
            columns[index][indexLinie] = parseInt(columns[index][indexLinie]);
        }
    }
    columns.sort(ascending);
    if (before === columns.toString()) {
        columns.sort(descending);
    }

    for (let index = 0; index < rows.length; index++) {
        for (let nthChild = 0; nthChild < rows[index].children.length - 1; nthChild++) {
            rows[index].children[nthChild + 1].querySelector("div").innerHTML = columns[nthChild][index];
            // nu merge cu td in loc de div
        }
    }
}