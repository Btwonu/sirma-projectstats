We have data for multiple employees, working on different projects. The application's purpose is to find the overlap of time employees have spent working together on common projects.

The input data is expected to be in CSV format and includes employee ID (employeeId), project ID (projectId), start date (from), and end date (to).

We have the start and end date for each task, so we can find the time overlap for each employee pair.
For example, we have the following pair, who worked on a project together:

employeeId, projectId, from, to
1, 101, 2022-01-01, 2022-05-20
2, 102, 2022-03-15, 2022-04-30

---

We can visualize the dates like so:

A will be `from` 1
B will be `to` 1
C will be `from` 2
D will be `to` 2

We have 4 scenarios when there is an overlap:

A |--------------| B
C |--------------| D

		A |--------------| B
C |--------------| D

A |------------------------| B
C |--------------| D

	A |--------------| B
C |------------------------| D

So we have a date overlap if:
- A <= D && B >= C

We can calculate the overlap duration, by getting the least amount of distance, when subtracting the `to` dates form the `from` dates:
min(
B - A
B - C
D - C
D - B
);

---

We are rendering the data on the front-end, in the following format:

Top pair programmers:

ID1, ID2
- Total overlap in days
- Projects worked on
- Overlap in days for each project

---

Postgresql is used for persistence.
There are `employee` and `project` tables to store entities and an `employee_project` table, which is used to make assotiation between an employee and a project, by storing the ids and the date range the employee have been working on a project.

In an attempt to minimize the performance overhead, the application uses SQL for the heavy-lifting, namely doing the overlap calculation.

---

## Project structure

### Controllers

Controllers receive client requests and call the appropriate services

#### EmployeeController

Responsible for the listing and creation of employees.

#### DashboardController

Responsible for the data retrieved from the assotiation of our employees and projects, i.e.: overlap data

### Services

Mainly used to retrieve data from the DAOs and pass it along to the controllers

#### DashboardService

#### EmployeeService

#### ProjectService

### DAOs

DAOs are used to map to tables in the database

#### EmployeeDao

#### ProjectDao

#### EmployeeProjectDao
