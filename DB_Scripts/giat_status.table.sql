--
-- PostgreSQL database dump
--

-- Started on 2009-10-13 15:45:58

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
-- TOC entry 2268 (class 1259 OID 17227)
-- Dependencies: 3
-- Name: giat_status; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE giat_status (
    status_code integer NOT NULL,
    status character varying,
    est_time character varying
);


ALTER TABLE public.giat_status OWNER TO postgres;

--
-- TOC entry 2269 (class 1259 OID 17233)
-- Dependencies: 2268 3
-- Name: giat_status_status_code_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE giat_status_status_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.giat_status_status_code_seq OWNER TO postgres;

--
-- TOC entry 2578 (class 0 OID 0)
-- Dependencies: 2269
-- Name: giat_status_status_code_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE giat_status_status_code_seq OWNED BY giat_status.status_code;


--
-- TOC entry 2579 (class 0 OID 0)
-- Dependencies: 2269
-- Name: giat_status_status_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('giat_status_status_code_seq', 1, false);


--
-- TOC entry 2571 (class 2604 OID 17235)
-- Dependencies: 2269 2268
-- Name: status_code; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE giat_status ALTER COLUMN status_code SET DEFAULT nextval('giat_status_status_code_seq'::regclass);


--
-- TOC entry 2574 (class 0 OID 17227)
-- Dependencies: 2268
-- Data for Name: giat_status; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY giat_status (status_code, status, est_time) FROM stdin;
\.


--
-- TOC entry 2573 (class 2606 OID 17237)
-- Dependencies: 2268 2268
-- Name: giat_status_prim_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY giat_status
    ADD CONSTRAINT giat_status_prim_key PRIMARY KEY (status_code);


--
-- TOC entry 2577 (class 0 OID 0)
-- Dependencies: 2268
-- Name: giat_status; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE giat_status FROM PUBLIC;
REVOKE ALL ON TABLE giat_status FROM postgres;
GRANT ALL ON TABLE giat_status TO postgres;
GRANT SELECT,REFERENCES ON TABLE giat_status TO PUBLIC;
GRANT ALL ON TABLE giat_status TO postgis WITH GRANT OPTION;


-- Completed on 2009-10-13 15:45:58

--
-- PostgreSQL database dump complete
--

