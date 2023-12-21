DROP TABLE IF EXISTS employee CASCADE;
DROP TABLE IF EXISTS project CASCADE;
DROP TABLE IF EXISTS employee_project;

CREATE TABLE employee (
    id BIGINT PRIMARY KEY
);

ALTER TABLE employee
ADD CONSTRAINT unique_employee_id UNIQUE (id);

CREATE TABLE project (
    id BIGINT PRIMARY KEY
);

ALTER TABLE project
ADD CONSTRAINT unique_project_id UNIQUE (id);

CREATE TABLE employee_project (
    employee_id BIGINT REFERENCES employee(id),
    project_id BIGINT REFERENCES project(id),
    from_date DATE NOT NULL,
    to_date DATE NOT NULL,
    PRIMARY KEY (employee_id, project_id, from_date),
    CHECK (from_date <= to_date)
);

ALTER TABLE employee_project
ADD CONSTRAINT unique_employee_project
UNIQUE (employee_id, project_id, from_date);