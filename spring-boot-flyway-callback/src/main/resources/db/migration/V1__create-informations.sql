CREATE TABLE informations (
	id serial,
	text text,
	int_array integer[],
	text_array text[],
	json jsonb,
	constraint informations_PKC primary key (id)
);
