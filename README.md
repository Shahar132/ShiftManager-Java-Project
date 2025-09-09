
# WorkManager (Shift Management System)

A Java-based application for managing employees and their shifts in a workplace.  
Implements OOP and design patterns for modularity and scalability.

## Features
- **Role-based login** (Worker / Supervisor / Administrator)
- **Shift management**: create, update, delete, assign employees
- **Check-in / Check-out** tracking with duration calculation
- **Employee management**: add, update, delete, search
- **Shift search** by day, week, month, or employee
- **Notifications** for shift changes
- **Safe exit** via option `-1` (with confirmation)

> For new users: **username = First Name**, **password = Person ID**.

## Demo Credentials (testing)
- **Administrator** — Username: `Avi`, Password: `123456789`
- **Supervisor** — Username: `John`, Password: `987654321`
- **Worker** — Username: `Alice`, Password: `111111111`


```

## Project Structure
WorkManager/
├─ AbstractClasses/
│ └─ Person.java
├─ Enums/
│ └─ Rank.java
├─ Factories/
│ └─ EmployeeFactory.java
├─ Interfaces/
│ ├─ ICheckInOutManagement.java
│ ├─ IEmployeeManagement.java
│ ├─ IFunctionsLab.java
│ └─ IShiftManagement.java
├─ Manegers/ 
│ ├─ DataBase.java
│ ├─ EmployeeDataBase.java
│ ├─ ShiftManager.java
│ └─ ShiftMemento.java
├─ ObserverPattern/
│ ├─ Observer.java
│ └─ Subject.java
├─ SingleObjectClasses/
│ ├─ CheckInOutRecord.java
│ ├─ Date.java
│ ├─ Employee.java
│ └─ Shift.java
├─ WorkPlaceManeger/ 
  └─ ShiftManagementSystem.java # Main entry point

```

## Design Patterns Used
- **Factory** — `EmployeeFactory` for creating employees  
- **Singleton** — `DataBase` instance  
- **Observer** — notifications to supervisors/admins  
- **Memento** — `ShiftMemento` for saving/restoring shift state  
