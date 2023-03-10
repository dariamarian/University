#include "domain.h"

const string Telefon::tostring() {
    return to_string(cod) + " " + brand + " " + model + "\n";
}

const string Telefon::tostring2() {
    return to_string(cod) + " " + brand + " " + model + " " + to_string(pret) + "\n";
}