--
-- PostgreSQL database dump
--

-- Started on 2009-10-07 14:29:59

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1491 (class 1259 OID 19386)
-- Dependencies: 3
-- Name: giat_categories; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE giat_categories (
    category_id integer NOT NULL,
    category character varying
);


ALTER TABLE public.giat_categories OWNER TO postgres;

--
-- TOC entry 1490 (class 1259 OID 19384)
-- Dependencies: 3 1491
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
-- TOC entry 1765 (class 0 OID 0)
-- Dependencies: 1490
-- Name: giat_categories_category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE giat_categories_category_id_seq OWNED BY giat_categories.category_id;


--
-- TOC entry 1760 (class 2604 OID 19389)
-- Dependencies: 1490 1491 1491
-- Name: category_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE giat_categories ALTER COLUMN category_id SET DEFAULT nextval('giat_categories_category_id_seq'::regclass);


--
-- TOC entry 1762 (class 2606 OID 19394)
-- Dependencies: 1491 1491
-- Name: giat_categories_prim_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY giat_categories
    ADD CONSTRAINT giat_categories_prim_key PRIMARY KEY (category_id);


-- Completed on 2009-10-07 14:30:00

--
-- PostgreSQL database dump complete
--

