CREATE TABLE if NOT EXISTS user_cred(
    id                VARCHAR(36) CONSTRAINT user_cred_id_pk PRIMARY KEY DEFAULT gen_random_uuid(),
    username          VARCHAR(50) NOT NULL,
    password          VARCHAR(255) NOT NULL,
    created_at        TIMESTAMP WITH TIME ZONE NOT NULL,
    version           bigint NOT NULL
);
