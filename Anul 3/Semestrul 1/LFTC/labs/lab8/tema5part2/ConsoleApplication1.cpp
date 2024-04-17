#include <iostream>
#include <fstream>
#include <string>
#include <stack>
#include <unordered_map>
#include <vector>
#include <cctype>
#include <string>

using namespace std;

class Grammar
{
public:
    void addGrammarRule(const string &nonTerminal, const vector<string> &production);
    bool parse(const string &input);
    void setStartSymbol(const string &startSymbol)
    {
        this->startSymbol = startSymbol;
    }
    string sirProd(stack<string> &alphaStack);

private:
    string startSymbol;                                                
    unordered_map<string, vector<pair<vector<string>, string>>> rules;
};

void Grammar::addGrammarRule(const string &nonTerminal, const vector<string> &production)
{
    string indicativ_rp = nonTerminal + to_string(rules[nonTerminal].size() + 1);
    rules[nonTerminal].push_back(make_pair(production, indicativ_rp));
}

bool isNonterminal(const string& str)
{
    return !str.empty() && isupper(static_cast<unsigned char>(str[0]));
}

bool isTerminal(const string& str)
{
    return !str.empty() && !isupper(static_cast<unsigned char>(str[0]));
}

bool Grammar::parse(const string &input)
{
    stack<string> alphaStack;
    stack<string> betaStack;
    int i = 1;
    string s = "q";
    string t = "t";
    string e = "e";
    string q = "q";
    string r = "r";
    string S = startSymbol;

    betaStack.push(S);

    while ((s != t) && (s != e))
    {
        if (s == "q")
        {
            if (betaStack.empty())
            {
                if (i == input.length() + 1)
                {
                    s = t;
                }
                else
                {
                    s = r;
                }
            }
            else
            {
                if (isNonterminal(betaStack.top()))
                {
                    string A = betaStack.top();
                    betaStack.pop();
                    string indicativ_rp = rules[A][0].second;
                    alphaStack.push(indicativ_rp);
                    for (int j = rules[A][0].first.size() - 1; j >= 0; j--)
                    {
                        betaStack.push(rules[A][0].first[j]);
                    }
                }
                else if (isTerminal(betaStack.top()))
                {
                    if (betaStack.top() == input.substr(i - 1, betaStack.top().length()))
                    {
                        i += betaStack.top().length();
                        string betaTop = betaStack.top();
                        betaStack.pop();
                        alphaStack.push(betaTop);
                    }
                    else
                    {
                        s = r;
                    }
                }
                else
                {
                    s = r;
                }
            }
        }
        else if (s == "r")
        {
            if (!isNonterminal(alphaStack.top()))
            {
                i -= alphaStack.top().length();
                string neterminal = alphaStack.top();
                alphaStack.pop();
                betaStack.push(neterminal);
            }
            else
            {
                string indicativ_rp = alphaStack.top();
                string number = indicativ_rp.substr(indicativ_rp.length() - 1);
                int index = stoi(number);
                indicativ_rp = indicativ_rp.substr(0, indicativ_rp.length() - 1);

                vector<string> production = rules[indicativ_rp][index - 1].first;

                vector<string> symbols;
                int pos = 0;
                bool exists = true;
                while (!betaStack.empty() && pos < production.size())
                {
                    if (betaStack.top() == production[pos])
                    {
                        symbols.push_back(betaStack.top());
                        betaStack.pop();
                        pos++;
                    }
                    else
                    {
                        for (int i = symbols.size() - 1; i >= 0; i--)
                        {
                            betaStack.push(symbols[i]);
                        }
                        exists = false;
                        break;
                    }
                }

                if (exists && index < rules[indicativ_rp].size())
                {
                    s = q;
                    alphaStack.pop();
                    string indicativ_urm = rules[indicativ_rp][index].second;
                    alphaStack.push(indicativ_urm);

                    if (rules[indicativ_rp][index].first[0] == string("ε"))
                    {
                        continue;
                    }
                    for (int k = rules[indicativ_rp][index].first.size() - 1; k >= 0; k--)
                    {
                        betaStack.push(rules[indicativ_rp][index].first[k]);
                    }
                }
                else
                {
                    if (i == 1 && indicativ_rp == S)
                    {
                        s = e;
                    }
                    else
                    {
                        alphaStack.pop();
                        betaStack.push(indicativ_rp);
                    }
                }
            }
        }
    }
    if (s == t)
        cout << sirProd(alphaStack) << endl;
    return s == t;
}

string Grammar::sirProd(stack<string> &alphaStack)
{
    string sir_prod = "";
    while (!alphaStack.empty())
    {
        string alphaTop = alphaStack.top();
        alphaStack.pop();
        if (alphaTop.size() > 1 && isNonterminal(alphaTop))
        {
            sir_prod = alphaTop + " " + sir_prod;
        }
    }
    return sir_prod;
}

int main()
{
    Grammar grammar;
    bool isStartSymbolSet = true;
    ifstream grammarFile("gramatica.txt");
    if (grammarFile.is_open())
    {
        string nonTerminal;
        string symbol;
        vector<string> production;

        while (grammarFile >> nonTerminal >> symbol)
        {
            if (isStartSymbolSet)
            {
                grammar.setStartSymbol(nonTerminal);
                isStartSymbolSet = false;
            }

            string delimiter = "|";
            size_t pos = 0;
            string token;
            while ((pos = symbol.find(delimiter)) != string::npos)
            {
                token = symbol.substr(0, pos);
                production.push_back(token);
                symbol.erase(0, pos + delimiter.length());
            }
            production.push_back(symbol);

            grammar.addGrammarRule(nonTerminal, production);
            production.clear();
        }

        grammarFile.close();
    }
    else
    {
        cerr << "Nu s-a putut deschide fisierul de gramatica." << endl;
        return 1;
    }

    ifstream inputFile("input.txt");
    string inputSequence;
    string symbol;
    while (inputFile >> symbol)
    {
        inputSequence += symbol;
    }
    cout << "Secventa de intrare este: " << inputSequence << endl;
    inputFile.close();

    if (grammar.parse(inputSequence))
    {
        cout << "Secventa de intrare este acceptata." << endl;
    }
    else
    {
        cout << "Secventa de intrare nu este acceptata." << endl;
    }
    return 0;
}
