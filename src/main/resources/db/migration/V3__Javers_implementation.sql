-- Create sequences first with exact names Javers expects
CREATE SEQUENCE jv_commit_pk_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE jv_global_id_pk_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE jv_snapshot_pk_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE jv_commit (
    commit_pk BIGINT PRIMARY KEY DEFAULT nextval('jv_commit_pk_seq'),
    author VARCHAR(200),
    commit_date TIMESTAMP,
    commit_date_instant VARCHAR(30),
    commit_id NUMERIC(22,2)
);

CREATE TABLE jv_global_id (
    global_id_pk BIGINT PRIMARY KEY DEFAULT nextval('jv_global_id_pk_seq'),
    local_id VARCHAR(191),
    fragment VARCHAR(200),
    type_name VARCHAR(200),
    owner_id_fk BIGINT,
    FOREIGN KEY (owner_id_fk) REFERENCES jv_global_id(global_id_pk)
);

CREATE TABLE jv_snapshot (
    snapshot_pk BIGINT PRIMARY KEY DEFAULT nextval('jv_snapshot_pk_seq'),
    type VARCHAR(200),
    version BIGINT,
    state TEXT,
    changed_properties TEXT,
    managed_type VARCHAR(200),
    global_id_fk BIGINT NOT NULL,
    commit_fk BIGINT NOT NULL,
    FOREIGN KEY (global_id_fk) REFERENCES jv_global_id(global_id_pk),
    FOREIGN KEY (commit_fk) REFERENCES jv_commit(commit_pk)
);

CREATE TABLE jv_commit_property (
                                    property_name VARCHAR(191) NOT NULL,
                                    property_value VARCHAR(600),
                                    commit_fk BIGINT NOT NULL,
                                    PRIMARY KEY (commit_fk, property_name),
                                    FOREIGN KEY (commit_fk) REFERENCES jv_commit(commit_pk)
);