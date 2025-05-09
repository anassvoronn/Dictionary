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
(1, 'China', 'population: 200 million; currency: CN'),
(1, 'Canada', 'population: 120 million; currency: DC'),
(2, 'Cat', 'color: orange'),
(2, 'Dog', 'color: black');

ALTER TABLE ONLY public.dictionaries
    ADD CONSTRAINT unique_dictionary_name UNIQUE (name);

ALTER TABLE ONLY public.records
    ADD CONSTRAINT unique_record_name_dictionary_id UNIQUE (name, dictionary_id);

ALTER TABLE ONLY public.records
    ADD CONSTRAINT fk_dictionary FOREIGN KEY (dictionary_id) REFERENCES public.dictionaries(id) ON DELETE CASCADE;