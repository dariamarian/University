using System;

namespace proiectSCcsharp.services
{
    public class MyException : Exception
    {
        public MyException(string message) : base(message)
        {
        }
    }
}