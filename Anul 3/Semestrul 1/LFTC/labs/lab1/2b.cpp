int main() {
    int a ;
    int b ;
    cin >> a ;
    cin >> b ;
    while ( a != b ) {
        if ( a > b )
            a = a – b ;
        else
            b = b – a ;
    }
    cout << a ;
    return 0 ;
}