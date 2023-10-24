#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <map>
#include <regex>
#include <algorithm>

using namespace std;

enum Atom {
    ID = 0, CONST = 1, INT = 2, FLOAT = 3, DOUBLE = 4, STRUCT = 5,
    ADDITION = 6, SUBTRACTION = 7, MULTIPLICATION = 8, DIVISION = 9,
    GT = 10, LT = 11, NEQ = 12, EQ = 13, LE = 14, GE = 15,
    ASSIGN = 16, IF = 17, ELSE = 18, WHILE = 19, FOR = 20,
    CIN = 21, COUT = 22, RETURN = 23, SEMICOLON = 24, COMMA = 25,
    OPEN_PAREN = 26, CLOSE_PAREN = 27, OPEN_BRACE = 28, CLOSE_BRACE = 29,
    INPUT = 30, OUTPUT = 31, MAIN = 32, UNKNOWN = 33
};

string AtomToString(Atom atom) {
    static const char* atomStrings[] = {
        "ID", "CONST", "int", "float", "double", "struct", "+", "-", "*", "/", ">", "<",
        "!=", "==", "<=", ">=", "=", "if", "else", "while", "for", "cin", "cout",
        "return", ";", ",", "(", ")", "{", "}", ">>", "<<", "main()", "unknown"
    };
    return atomStrings[atom];
}

const map<string, Atom> tokenMappings = {
    {";", SEMICOLON}, {"cin", CIN}, {"cout", COUT},
    {"=", ASSIGN}, {"if", IF}, {"else", ELSE},
    {"while", WHILE}, {"for", FOR}, {"return", RETURN},
    {"*", MULTIPLICATION}, {"/", DIVISION},
    {"+", ADDITION}, {"-", SUBTRACTION},
    {"{", OPEN_BRACE}, {"}", CLOSE_BRACE},
    {"(", OPEN_PAREN}, {")", CLOSE_PAREN},
    {",", COMMA}, {">", GT}, {"<", LT},
    {"!=", NEQ}, {"==", EQ}, {"<=", LE}, {">=", GE},
    {">>", INPUT}, {"<<", OUTPUT}, {"main()", MAIN},
    {"int", INT}, {"float", FLOAT}, {"double", DOUBLE}
};

vector<pair<string, int>> symbolTable;
vector<pair<Atom, int>> fipTable;
int currentPosition = 1;

void addTokenToSymbolTable(const std::string& token) {
    auto it = std::lower_bound(symbolTable.begin(), symbolTable.end(), token,
        [](const std::pair<std::string, int>& entry, const std::string& token) {
            return entry.first < token;
        });

    if (it == symbolTable.end() || it->first != token) {
        symbolTable.insert(it, { token, currentPosition });
        currentPosition++;
    }
}

void addTokenToFIP(Atom atom, int code) {
    fipTable.push_back({ atom, code });
}

void writeAtomsToFile(const string& outputFile) {
    ofstream file(outputFile);
    file << "Atom lexical | Cod atom" << "\n";
    for (int i = 0; i <= 32; ++i) {
        Atom atom = static_cast<Atom>(i);
        file << AtomToString(atom) << " " << i << endl;
    }
    file.close();
}

void writeFIP(const string& outputFile) {
    ofstream file(outputFile);
    file << "Cod atom | Cod TS" << "\n";
    for (const auto& entry : fipTable) {
        file << entry.first << " " << entry.second << "\n";
    }
    file.close();
}

void writeTS(const string& outputFile) {
    ofstream file(outputFile);
    file << "Simbol | Cod TS" << "\n";

    vector<pair<string, int>> sortedSymbolTable(symbolTable.begin(), symbolTable.end());
    sort(sortedSymbolTable.begin(), sortedSymbolTable.end(),
        [](const pair<string, int>& a, const pair<string, int>& b) {
            return a.first < b.first;
        });

    for (const auto& entry : sortedSymbolTable) {
        file << entry.first << " " << entry.second << "\n";
    }
    file.close();
}

void reorderSymbolTable() {
    sort(symbolTable.begin(), symbolTable.end(),
        [](const pair<string, int>& a, const pair<string, int>& b) {
            return a.first < b.first;
        });

    for (int i = 0; i < symbolTable.size(); i++) {
        symbolTable[i].second = i + 1;
    }
}

void lexicalAnalysis(const string& sourceCode) {
    regex identifierPattern("[a-zA-Z_][a-zA-Z0-9_]{0,7}");
    regex identifierAuxPattern("[a-zA-Z_][a-zAZ0-9_]{0,250}");
    regex constantPattern(R"(\d+(\.\d+)?)");
    string currentToken;
    int lineCount = 1;
    int columnCount = 1;

    stringstream ss(sourceCode);
    string line;
    while (getline(ss, line, '\n')) {
        istringstream lineStream(line);
        while (lineStream >> currentToken) {
            Atom atomCode = UNKNOWN;
            if (tokenMappings.count(currentToken) > 0) {
                atomCode = tokenMappings.at(currentToken);
            }
            if (atomCode != UNKNOWN) {

            } else if (regex_match(currentToken, identifierPattern)) {
                addTokenToSymbolTable(currentToken);
            } else if (regex_match(currentToken, constantPattern)) {
                addTokenToSymbolTable(currentToken);
            } else {
                if (regex_match(currentToken, identifierAuxPattern) && currentToken.size() > 8) {
                    cerr << "Lexical Error: Identifier too long at line " << lineCount
                        << ", column " << columnCount << ": " << currentToken << endl;
                } else {
                    cerr << "Lexical Error: Unrecognized token at line " << lineCount
                        << ", column " << columnCount << ": " << currentToken << endl;
                }
            }
            columnCount += currentToken.size() + 1;
        }
        lineCount++;
        columnCount = 1;
    }
}

void lexicalAnalysis2(const string& sourceCode) {
    regex identifierPattern("[a-zA-Z_][a-zA-Z0-9_]{0,7}");
    regex identifierAuxPattern("[a-zA-Z_][a-zAZ0-9_]{0,250}");
    regex constantPattern(R"(\d+(\.\d+)?)");
    string currentToken;
    int lineCount = 1;
    int columnCount = 1;

    stringstream ss(sourceCode);
    string line;
    while (getline(ss, line, '\n')) {
        istringstream lineStream(line);
        while (lineStream >> currentToken) {
            if (tokenMappings.find(currentToken) != tokenMappings.end()) {
                addTokenToFIP(tokenMappings.at(currentToken), 0);
            } else if (regex_match(currentToken, identifierPattern)) {
                auto it = find_if(symbolTable.begin(), symbolTable.end(),
                    [currentToken](const pair<string, int>& entry) {
                        return entry.first == currentToken;
                    });
                if (it != symbolTable.end()) {
                    addTokenToFIP(ID, it->second);
                } else {
                    cerr << "Lexical Error: Identifier not found in symbol table at line " << lineCount
                        << ", column " << columnCount << ": " << currentToken << endl;
                }
            } else if (regex_match(currentToken, constantPattern)) {
                auto it = find_if(symbolTable.begin(), symbolTable.end(),
                    [currentToken](const pair<string, int>& entry) {
                        return entry.first == currentToken;
                    });
                if (it != symbolTable.end()) {
                    addTokenToFIP(CONST, it->second);
                } else {
                    cerr << "Lexical Error: Constant not found in symbol table at line " << lineCount
                        << ", column " << columnCount << ": " << currentToken << endl;
                }
            }
            columnCount += currentToken.size() + 1;
        }
        lineCount++;
        columnCount = 1;
    }
}

int main() {
    const string inputFilePath = "input.txt";
    const string outputAtomsFilePath = "output_atoms.txt";
    const string outputSymbolTableFilePath = "output_symbol_table.txt";
    const string outputFipFilePath = "output_fip.txt";

    ifstream file(inputFilePath);
    if (!file.is_open()) {
        cerr << "Error: Could not open the input file." << endl;
        return 1;
    }

    string sourceCode((istreambuf_iterator<char>(file)), (istreambuf_iterator<char>()));
    file.close();

    lexicalAnalysis(sourceCode);
    reorderSymbolTable();
    lexicalAnalysis2(sourceCode);
    writeAtomsToFile(outputAtomsFilePath);
    writeFIP(outputFipFilePath);
    writeTS(outputSymbolTableFilePath);

    cout << "Tables have been written to the files " << outputAtomsFilePath << ", " << outputSymbolTableFilePath << " and " << outputFipFilePath << "." << endl;

    return 0;
}
