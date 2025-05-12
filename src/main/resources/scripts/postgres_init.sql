CREATE TABLE public.dictionaries (
    id SERIAL PRIMARY KEY,
    name character varying(255) NOT NULL
);

ALTER TABLE public.dictionaries OWNER TO postgres;

CREATE TABLE public.records (
    id SERIAL PRIMARY KEY,
    dictionary_id integer NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.records OWNER TO postgres;

INSERT INTO public.dictionaries (name) VALUES
('Countries'),
('Animals');

INSERT INTO public.records (dictionary_id, name, value) VALUES
(1, 'China', '{"population": 1400000000, "capital": "Beijing", "currency": "Yuan"}'),
(1, 'Canada', '{"population": 38000000, "capital": "Ottawa", "currency": "Canadian Dollar"}'),
(2, 'Cat', '{"species": "Felis catus", "lifespan": 15, "diet": "Carnivore"}'),
(2, 'Dog', '{"species": "Canis lupus familiaris", "lifespan": 13, "diet": "Omnivore"}');

ALTER TABLE ONLY public.dictionaries
    ADD CONSTRAINT unique_dictionary_name UNIQUE (name);

ALTER TABLE ONLY public.records
    ADD CONSTRAINT unique_record_name_dictionary_id UNIQUE (name, dictionary_id);

ALTER TABLE ONLY public.records
    ADD CONSTRAINT fk_dictionary FOREIGN KEY (dictionary_id) REFERENCES public.dictionaries(id) ON DELETE CASCADE;