using System;

namespace client
{
    public enum EmployeeEvent
    {
        BILET_CUMPARAT
    };

    public class EmployeeEventArgs : EventArgs
    {
        public EmployeeEventArgs(EmployeeEvent employeeEvent, object data)
        {
            this.EmployeeEventType = employeeEvent;
            this.Data = data;
        }

        public EmployeeEvent EmployeeEventType { get; }

        public object Data { get; }
    }
}