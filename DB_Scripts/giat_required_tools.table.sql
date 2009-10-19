--
-- PostgreSQL database dump
--

-- Started on 2009-10-13 15:46:57

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
-- TOC entry 2274 (class 1259 OID 17272)
-- Dependencies: 3
-- Name: giat_tools_website; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE giat_tools_website (
    web_id integer NOT NULL,
    website character varying,
    package character varying NOT NULL,
    package_key character varying NOT NULL,
    package_version character varying
);


ALTER TABLE public.giat_tools_website OWNER TO postgres;

--
-- TOC entry 2577 (class 0 OID 0)
-- Dependencies: 2274
-- Name: TABLE giat_tools_website; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE giat_tools_website IS 'Website links and information for GIAT required tools.';


--
-- TOC entry 2275 (class 1259 OID 17278)
-- Dependencies: 3 2274
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
-- TOC entry 2579 (class 0 OID 0)
-- Dependencies: 2275
-- Name: website_web_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE website_web_id_seq OWNED BY giat_tools_website.web_id;


--
-- TOC entry 2580 (class 0 OID 0)
-- Dependencies: 2275
-- Name: website_web_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('website_web_id_seq', 1, false);


--
-- TOC entry 2571 (class 2604 OID 17280)
-- Dependencies: 2275 2274
-- Name: web_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE giat_tools_website ALTER COLUMN web_id SET DEFAULT nextval('website_web_id_seq'::regclass);


--
-- TOC entry 2574 (class 0 OID 17272)
-- Dependencies: 2274
-- Data for Name: giat_tools_website; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY giat_tools_website (web_id, website, package, package_key, package_version) FROM stdin;
\.


--
-- TOC entry 2573 (class 2606 OID 17282)
-- Dependencies: 2274 2274
-- Name: website_prim_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY giat_tools_website
    ADD CONSTRAINT website_prim_key PRIMARY KEY (web_id);


--
-- TOC entry 2578 (class 0 OID 0)
-- Dependencies: 2274
-- Name: giat_tools_website; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE giat_tools_website FROM PUBLIC;
REVOKE ALL ON TABLE giat_tools_website FROM postgres;
GRANT ALL ON TABLE giat_tools_website TO postgres;
GRANT SELECT,REFERENCES ON TABLE giat_tools_website TO PUBLIC;
GRANT ALL ON TABLE giat_tools_website TO postgis WITH GRANT OPTION;


-- Completed on 2009-10-13 15:46:57

--
-- PostgreSQL database dump complete
--

