CREATE TABLE consultas
(
    id          serial PRIMARY KEY,
    medico_id   bigint NOT NULL,
    paciente_id bigint NOT NULL,
    data        timestamp NOT NULL,

    CONSTRAINT fk_consultas_medico_id FOREIGN KEY (medico_id) REFERENCES medicos (id),
    CONSTRAINT fk_consultas_paciente_id FOREIGN KEY (paciente_id) REFERENCES pacientes (id)
);
