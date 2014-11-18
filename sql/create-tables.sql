CREATE TABLE asiakas (
	id SERIAL PRIMARY KEY,
	nimi VARCHAR(50) NOT NULL,
	tunnus VARCHAR(20) NOT NULL,
	salasana VARCHAR(20) NOT NULL,
	syntymaaika DATE NOT NULL,
	henkilotunnus VARCHAR(11) NOT NULL,
	osoite VARCHAR(75) NOT NULL
);

CREATE TABLE laakari (
	id SERIAL PRIMARY KEY,
	nimi VARCHAR(50) NOT NULL,
	tunnus VARCHAR(20) NOT NULL,
	salasana VARCHAR(20) NOT NULL
);

CREATE TABLE potilasraportti (
	potilasraportti_id INTEGER REFERENCES asiakas(id) on DELETE CASCADE
ON UPDATE CASCADE,
	lisaysajankohta TIMESTAMP NOT NULL,
	raportti VARCHAR(4000)
);

CREATE TABLE hoito-ohje (
	hoito-ohje_id INTEGER REFERENCES asiakas(id) on DELETE CASCADE 
ON UPDATE CASCADE,
	lisaysajankohta TIMESTAMP NOT NULL,
	ohje VARCHAR(4000)
);

CREATE TABLE varattava_aika (
	viikonpaiva DATE NOT NULL,
	aika VARCHAR(11) NOT NULL,
	laakari VARCHAR(20),
	asiakas VARCHAR(20),
	PRIMARY KEY(viikonpaiva, aika),
	FOREIGN KEY (laakari) REFERENCES laakari on DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (asiakas) REFERENCES asiakas on DELETE CASCADE ON UPDATE CASCADE
);


	
