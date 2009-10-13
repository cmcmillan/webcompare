--
-- PostgreSQL database dump
--

-- Started on 2009-10-13 15:45:01

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 2266 (class 1259 OID 17216)
-- Dependencies: 3
-- Name: giat_categories; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE giat_categories (
    category_id integer NOT NULL,
    category character varying
);


ALTER TABLE public.giat_categories OWNER TO postgres;

--
-- TOC entry 2267 (class 1259 OID 17222)
-- Dependencies: 3 2266
-- Name: giat_categories_category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE giat_categories_category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.giat_categories_category_id_seq OWNER TO postgres;

--
-- TOC entry 2578 (class 0 OID 0)
-- Dependencies: 2267
-- Name: giat_categories_category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE giat_categories_category_id_seq OWNED BY giat_categories.category_id;


--
-- TOC entry 2579 (class 0 OID 0)
-- Dependencies: 2267
-- Name: giat_categories_category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('giat_categories_category_id_seq', 1, false);


--
-- TOC entry 2571 (class 2604 OID 17224)
-- Dependencies: 2267 2266
-- Name: category_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE giat_categories ALTER COLUMN category_id SET DEFAULT nextval('giat_categories_category_id_seq'::regclass);


--
-- TOC entry 2574 (class 0 OID 17216)
-- Dependencies: 2266
-- Data for Name: giat_categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY giat_categories (category_id, category) FROM stdin;
\.


--
-- TOC entry 2573 (class 2606 OID 17226)
-- Dependencies: 2266 2266
-- Name: giat_categories_prim_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY giat_categories
    ADD CONSTRAINT giat_categories_prim_key PRIMARY KEY (category_id);


--
-- TOC entry 2577 (class 0 OID 0)
-- Dependencies: 2266
-- Name: giat_categories; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE giat_categories FROM PUBLIC;
REVOKE ALL ON TABLE giat_categories FROM postgres;
GRANT ALL ON TABLE giat_categories TO postgres;
GRANT SELECT,REFERENCES ON TABLE giat_categories TO PUBLIC;
GRANT ALL ON TABLE giat_categories TO postgis WITH GRANT OPTION;


-- Completed on 2009-10-13 15:45:01

--
-- PostgreSQL database dump complete
--

