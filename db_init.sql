CREATE TABLE Person (
	idPerson	INTEGER PRIMARY KEY AUTOINCREMENT,
	firstname	TEXT DEFAULT 'NULL',
	lastname	TEXT DEFAULT 'NULL',
	birthdate	DATETIME DEFAULT 'NULL'
);


CREATE TABLE MeasureDefinition (
	idMeasureDefinition	INTEGER PRIMARY KEY AUTOINCREMENT,
	measureType	TEXT DEFAULT 'NULL',
	measureValueType	TEXT DEFAULT 'NULL'
);


CREATE TABLE Measure(
	idMeasureHistory			INTEGER PRIMARY KEY AUTOINCREMENT,
	idPerson							INTEGER DEFAULT 'NULL',
	idMeasureDefinition		INTEGER DEFAULT 'NULL',
	measureValue					TEXT,
	dateRegistered				DATETIME DEFAULT 'NULL',

	FOREIGN KEY( idPerson ) REFERENCES Person ( idPerson ) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY( idMeasureDefinition ) REFERENCES MeasureDefinition ( idMeasureDefinition ) ON DELETE NO ACTION ON UPDATE NO ACTION
);


CREATE TABLE Goal(
	idGoal								INTEGER PRIMARY KEY AUTOINCREMENT,
	idMeasureDefinition 	INTEGER DEFAULT 'NULL',
	idPerson							INTEGER DEFAULT 'NULL',
	operator							TEXT DEFAULT 'NULL',
	value 								TEXT DEFAULT 'NULL',
	reached 							TEXT DEFAULT 'F',
	reachDate							TEXT DEFAULT 'NULL',

	FOREIGN KEY( idPerson ) REFERENCES Person ( idPerson ) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY( idMeasureDefinition ) REFERENCES MeasureDefinition ( idMeasureDefinition ) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE DailyGoal(
	idGoal								INTEGER PRIMARY KEY AUTOINCREMENT,
	idPerson							INTEGER DEFAULT 'NULL',
	value 								TEXT DEFAULT 'NULL',
	question							TEXT DEFAULT 'NULL',

	FOREIGN KEY( idPerson ) REFERENCES Person ( idPerson ) ON DELETE NO ACTION ON UPDATE NO ACTION
);


INSERT INTO person (idPerson, firstname, lastname, birthdate) VALUES ( 1 ,"Andrea","galloni","725670000000");
INSERT INTO person (idPerson, firstname, lastname, birthdate) VALUES ( 2 ,"Edoardo","galloni","520732800000");
INSERT INTO person (idPerson, firstname, lastname, birthdate) VALUES ( 3 ,"Guido","galloni", "-488332800000");
INSERT INTO person (firstname, lastname, birthdate) 		  VALUES ("Giacomo","Mene","771379200000");


INSERT INTO MeasureDefinition (idMeasureDefinition, measureType, measureValueType) VALUES ( 1 ,"height","integer");
INSERT INTO MeasureDefinition (idMeasureDefinition, measureType, measureValueType) VALUES ( 2 ,"weight","double");
INSERT INTO MeasureDefinition (idMeasureDefinition, measureType, measureValueType) VALUES ( 3 ,"bmi","double");
INSERT INTO MeasureDefinition (idMeasureDefinition, measureType, measureValueType) VALUES ( 4 ,"hrate","integer");
INSERT INTO MeasureDefinition (idMeasureDefinition, measureType, measureValueType) VALUES ( 5 ,"pressureMax","integer");
INSERT INTO MeasureDefinition (idMeasureDefinition, measureType, measureValueType) VALUES ( 6 ,"pressureMin","integer");



INSERT INTO Measure (idPerson, idMeasureDefinition, measureValue, dateRegistered ) VALUES (1,1,"55", "802915200000");
INSERT INTO Measure (idPerson, idMeasureDefinition, measureValue, dateRegistered ) VALUES (1,1,"110","834537600000");
INSERT INTO Measure (idPerson, idMeasureDefinition, measureValue, dateRegistered ) VALUES (1,1,"140","866073600000");

INSERT INTO Measure (idPerson, idMeasureDefinition, measureValue, dateRegistered ) VALUES (1,2,"20","789782400000");
INSERT INTO Measure (idPerson, idMeasureDefinition, measureValue, dateRegistered ) VALUES (1,2,"35","826502400000");
INSERT INTO Measure (idPerson, idMeasureDefinition, measureValue, dateRegistered ) VALUES (1,2,"67","857952000000");



INSERT INTO Measure (idPerson, idMeasureDefinition, measureValue, dateRegistered ) VALUES (2,1,"220","881884800000");
INSERT INTO Measure (idPerson, idMeasureDefinition, measureValue, dateRegistered ) VALUES (2,1,"330","913420800000");
INSERT INTO Measure (idPerson, idMeasureDefinition, measureValue, dateRegistered ) VALUES (2,1,"770","944956800000");

INSERT INTO Measure (idPerson, idMeasureDefinition, measureValue, dateRegistered ) VALUES (2,2,"22","881884800000");
INSERT INTO Measure (idPerson, idMeasureDefinition, measureValue, dateRegistered ) VALUES (2,2,"33","913420800000");
INSERT INTO Measure (idPerson, idMeasureDefinition, measureValue, dateRegistered ) VALUES (2,2,"77","866073600000");



INSERT INTO Goal (idGoal, idMeasureDefinition, idPerson, operator, value) VALUES (1,2,1,"lt","77");
INSERT INTO Goal (idGoal, idMeasureDefinition, idPerson, operator, value) VALUES (2,4,1,"lt","60");
INSERT INTO Goal (idGoal, idMeasureDefinition, idPerson, operator, value) VALUES (3,5,1,"lt","120");
INSERT INTO Goal (idGoal, idMeasureDefinition, idPerson, operator, value) VALUES (4,5,1,"gt","90");



INSERT INTO	DailyGoal (idGoal, idPerson, value, question) VALUES (1,1,NULL,"Did You Walk at least 30 min?");
INSERT INTO	DailyGoal (idGoal, idPerson, value, question) VALUES (2,1,NULL,"Did You eat vegetables today?");
INSERT INTO	DailyGoal (idGoal, idPerson, value, question) VALUES (3,1,NULL,"Did You all daily exercises?");


CREATE VIEW CurrentHealth AS SELECT idMeasureHistory, idPerson, idMeasureDefinition, measureType, measureValue, measureValueType, MAX(dateRegistered) AS dateRegistered FROM Measure NATURAL JOIN MeasureDefinition GROUP BY idPerson, idMeasureDefinition;



SELECT * FROM person;

SELECT * FROM MeasureDefinition;

SELECT * FROM Measure;


SELECT firstname, person.idPerson, idMeasureDefinition, MeasureDefinition.measureType, Measure.dateRegistered FROM person NATURAL JOIN (Measure NATURAL JOIN MeasureDefinition);

SELECT * FROM CurrentHealth;

.quit
