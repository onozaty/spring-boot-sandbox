CREATE TABLE users (
	id INT PRIMARY KEY AUTO_INCREMENT,
	login_id VARCHAR(20) NOT NULL UNIQUE,
	encoded_password VARCHAR(255) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	mail_address VARCHAR(100)
);
