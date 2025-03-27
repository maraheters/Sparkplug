CREATE TABLE car_model
(
    id              SERIAL NOT NULL,
    name            VARCHAR(255),
    manufacturer_id BIGINT,

    CONSTRAINT pk_car_model PRIMARY KEY (id)
);

CREATE TABLE drivetrain
(
    id              SERIAL NOT NULL,
    type            VARCHAR(255),

    CONSTRAINT pk_drivetrain PRIMARY KEY (id)
);

CREATE TABLE engine
(
    id              SERIAL NOT NULL,
    fuel_type       VARCHAR(255),
    type            VARCHAR(255),
    horsepower      INTEGER,
    torque          INTEGER,
    modification_id BIGINT,

    CONSTRAINT pk_engine PRIMARY KEY (id)
);

CREATE TABLE generation
(
    id           SERIAL NOT NULL,
    name         VARCHAR(255),
    start_year   INTEGER,
    car_model_id BIGINT,

    CONSTRAINT pk_generation PRIMARY KEY (id)
);

CREATE TABLE manufacturer
(
    id      SERIAL NOT NULL,
    name    VARCHAR(255),
    country VARCHAR(255),

    CONSTRAINT pk_manufacturer PRIMARY KEY (id)
);

CREATE TABLE modification
(
    id              SERIAL NOT NULL,
    name            VARCHAR(255),
    generation_id   BIGINT,
    drivetrain_id   BIGINT,
    engine_id       BIGINT,
    transmission_id BIGINT,

    CONSTRAINT pk_modification PRIMARY KEY (id)
);

CREATE TABLE transmission
(
    id              SERIAL NOT NULL,
    type            VARCHAR(255),
    number_of_gears INTEGER,
    modification_id BIGINT,

    CONSTRAINT pk_transmission PRIMARY KEY (id)
);

ALTER TABLE car_model
    ADD CONSTRAINT fk_car_model FOREIGN KEY (manufacturer_id) REFERENCES manufacturer (id);

ALTER TABLE engine
    ADD CONSTRAINT fk_modification FOREIGN KEY (modification_id) REFERENCES modification (id);

ALTER TABLE generation
    ADD CONSTRAINT fk_car_model FOREIGN KEY (car_model_id) REFERENCES car_model (id);

ALTER TABLE modification
    ADD CONSTRAINT fk_drivetrain FOREIGN KEY (drivetrain_id) REFERENCES drivetrain (id);

ALTER TABLE modification
    ADD CONSTRAINT fk_engine FOREIGN KEY (engine_id) REFERENCES engine (id);

ALTER TABLE modification
    ADD CONSTRAINT fk_generation FOREIGN KEY (generation_id) REFERENCES generation (id);

ALTER TABLE modification
    ADD CONSTRAINT fk_transmission FOREIGN KEY (transmission_id) REFERENCES transmission (id);

ALTER TABLE transmission
    ADD CONSTRAINT fk_modification FOREIGN KEY (modification_id) REFERENCES modification (id);