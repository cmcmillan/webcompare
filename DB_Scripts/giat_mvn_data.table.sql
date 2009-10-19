--
-- PostgreSQL database dump
--

-- Started on 2009-10-13 15:45:17

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
-- TOC entry 2272 (class 1259 OID 17260)
-- Dependencies: 2571 3
-- Name: giat_mvn_data; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE giat_mvn_data (
    dependency_id integer NOT NULL,
    raw_text character varying,
    group_id character varying,
    artifact_id character varying,
    artifact_type character varying,
    artifact_version character varying,
    scope character varying,
    classifier character varying DEFAULT ''::character varying
);


ALTER TABLE public.giat_mvn_data OWNER TO postgres;

--
-- TOC entry 2578 (class 0 OID 0)
-- Dependencies: 2272
-- Name: TABLE giat_mvn_data; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE giat_mvn_data IS 'Raw MVN dependency data';


--
-- TOC entry 2579 (class 0 OID 0)
-- Dependencies: 2272
-- Name: COLUMN giat_mvn_data.raw_text; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN giat_mvn_data.raw_text IS 'The raw text supplied to the parser.';


--
-- TOC entry 2580 (class 0 OID 0)
-- Dependencies: 2272
-- Name: COLUMN giat_mvn_data.group_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN giat_mvn_data.group_id IS 'Usually the package containing the artifact';


--
-- TOC entry 2581 (class 0 OID 0)
-- Dependencies: 2272
-- Name: COLUMN giat_mvn_data.artifact_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN giat_mvn_data.artifact_id IS 'Name of the project';


--
-- TOC entry 2582 (class 0 OID 0)
-- Dependencies: 2272
-- Name: COLUMN giat_mvn_data.artifact_type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN giat_mvn_data.artifact_type IS 'Type of the artifact for example : 

java-source, jar, war';


--
-- TOC entry 2583 (class 0 OID 0)
-- Dependencies: 2272
-- Name: COLUMN giat_mvn_data.artifact_version; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN giat_mvn_data.artifact_version IS 'Version of the artifact';


--
-- TOC entry 2584 (class 0 OID 0)
-- Dependencies: 2272
-- Name: COLUMN giat_mvn_data.classifier; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN giat_mvn_data.classifier IS 'Used to distinguish artifacts that were built from the same POM but with different content. Common use case is the classifiers sources and javadoc are used to deploy the project source code and API docs along with the packaged class files.';


--
-- TOC entry 2273 (class 1259 OID 17267)
-- Dependencies: 3 2272
-- Name: mvn_data_dependency_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE mvn_data_dependency_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.mvn_data_dependency_id_seq OWNER TO postgres;

--
-- TOC entry 2586 (class 0 OID 0)
-- Dependencies: 2273
-- Name: mvn_data_dependency_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE mvn_data_dependency_id_seq OWNED BY giat_mvn_data.dependency_id;


--
-- TOC entry 2587 (class 0 OID 0)
-- Dependencies: 2273
-- Name: mvn_data_dependency_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('mvn_data_dependency_id_seq', 1, false);


--
-- TOC entry 2572 (class 2604 OID 17269)
-- Dependencies: 2273 2272
-- Name: dependency_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE giat_mvn_data ALTER COLUMN dependency_id SET DEFAULT nextval('mvn_data_dependency_id_seq'::regclass);


--
-- TOC entry 2575 (class 0 OID 17260)
-- Dependencies: 2272
-- Data for Name: giat_mvn_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY giat_mvn_data (dependency_id, raw_text, group_id, artifact_id, artifact_type, artifact_version, scope, classifier) FROM stdin;
\.


--
-- TOC entry 2574 (class 2606 OID 17271)
-- Dependencies: 2272 2272
-- Name: mvn_data_prim_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY giat_mvn_data
    ADD CONSTRAINT mvn_data_prim_key PRIMARY KEY (dependency_id);


--
-- TOC entry 2585 (class 0 OID 0)
-- Dependencies: 2272
-- Name: giat_mvn_data; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE giat_mvn_data FROM PUBLIC;
REVOKE ALL ON TABLE giat_mvn_data FROM postgres;
GRANT ALL ON TABLE giat_mvn_data TO postgres;
GRANT SELECT,REFERENCES ON TABLE giat_mvn_data TO PUBLIC;
GRANT ALL ON TABLE giat_mvn_data TO postgis WITH GRANT OPTION;


-- Completed on 2009-10-13 15:45:17

--
-- PostgreSQL database dump complete
--

