CREATE TABLE IF NOT EXISTS public.dictionaries
(
    id integer NOT NULL DEFAULT nextval('dictionaries_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT dictionaries_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.dictionaries
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.records
(
    id integer NOT NULL DEFAULT nextval('records_id_seq'::regclass),
    dictionary_id integer NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    value character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT records_pkey PRIMARY KEY (id),
    CONSTRAINT fk_dictionary FOREIGN KEY (dictionary_id)
        REFERENCES public.dictionaries (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.records
    OWNER to postgres;