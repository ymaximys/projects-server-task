CREATE TABLE project (
	id serial PRIMARY KEY,
	name VARCHAR (50) UNIQUE NOT NULL,
	status BOOLEAN NOT NULL
);

CREATE TABLE account (
	id SERIAL PRIMARY KEY,
	name VARCHAR (50)  NOT NULL,
	login VARCHAR (50) UNIQUE NOT NULL,
	password VARCHAR (50) NOT NULL
);

CREATE TABLE project_account (
	id SERIAL PRIMARY KEY,
	account_id INT references account(id),
	project_id INT references project(id)
);