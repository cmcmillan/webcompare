--
-- PostgreSQL database dump
--

-- Started on 2009-10-13 11:20:57

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1490 (class 1259 OID 19364)
-- Dependencies: 3
-- Name: website; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE website (
    web_id integer NOT NULL,
    website character varying,
    package character varying NOT NULL,
    package_key character varying NOT NULL,
    package_version character varying
);


ALTER TABLE public.website OWNER TO postgres;

--
-- TOC entry 1489 (class 1259 OID 19362)
-- Dependencies: 3 1490
-- Name: website_web_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE website_web_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.website_web_id_seq OWNER TO postgres;

--
-- TOC entry 1768 (class 0 OID 0)
-- Dependencies: 1489
-- Name: website_web_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE website_web_id_seq OWNED BY website.web_id;


--
-- TOC entry 1763 (class 2604 OID 19367)
-- Dependencies: 1490 1489 1490
-- Name: web_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE website ALTER COLUMN web_id SET DEFAULT nextval('website_web_id_seq'::regclass);


--
-- TOC entry 1765 (class 2606 OID 19372)
-- Dependencies: 1490 1490
-- Name: website_prim_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY website
    ADD CONSTRAINT website_prim_key PRIMARY KEY (web_id);


-- Completed on 2009-10-13 11:20:57

--
-- PostgreSQL database dump complete
--

