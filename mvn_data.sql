--
-- PostgreSQL database dump
--

-- Started on 2009-10-01 11:52:57

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = true;

--
-- TOC entry 1471 (class 1259 OID 19319)
-- Dependencies: 3
-- Name: mvn_data; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE mvn_data (
    dependency_id oid NOT NULL,
    raw_text character varying,
    dependency boolean,
    trimmed character varying,
    package_name character varying,
    class_name character varying,
    ver character varying
);


ALTER TABLE public.mvn_data OWNER TO postgres;

--
-- TOC entry 1743 (class 0 OID 0)
-- Dependencies: 1471
-- Name: TABLE mvn_data; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE mvn_data IS 'Raw MVN dependency data';


--
-- TOC entry 1740 (class 0 OID 19319)
-- Dependencies: 1471
-- Data for Name: mvn_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY mvn_data (dependency_id, raw_text, dependency, trimmed, package_name, class_name, ver) FROM stdin;
1	p:c:v 	\N	p:c:v	p	c	v
\.


--
-- TOC entry 1739 (class 2606 OID 19326)
-- Dependencies: 1471 1471
-- Name: mvn_data_prim_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mvn_data
    ADD CONSTRAINT mvn_data_prim_key PRIMARY KEY (dependency_id);


-- Completed on 2009-10-01 11:52:57

--
-- PostgreSQL database dump complete
--

