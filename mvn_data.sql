--
-- PostgreSQL database dump
--

-- Started on 2009-10-02 17:57:30

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1473 (class 1259 OID 19331)
-- Dependencies: 1741 3
-- Name: mvn_data; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE mvn_data (
    dependency_id integer NOT NULL,
    raw_text character varying,
    "groupId" character varying,
    "artifactId" character varying,
    "artifactType" character varying,
    "artifactVersion" character varying,
    scope character varying,
    classifier character varying DEFAULT ''::character varying
);


ALTER TABLE public.mvn_data OWNER TO postgres;

--
-- TOC entry 1747 (class 0 OID 0)
-- Dependencies: 1473
-- Name: TABLE mvn_data; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE mvn_data IS 'Raw MVN dependency data';


--
-- TOC entry 1748 (class 0 OID 0)
-- Dependencies: 1473
-- Name: COLUMN mvn_data.raw_text; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN mvn_data.raw_text IS 'The raw text supplied to the parser.';


--
-- TOC entry 1749 (class 0 OID 0)
-- Dependencies: 1473
-- Name: COLUMN mvn_data."groupId"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN mvn_data."groupId" IS 'Usually the package containing the artifact';


--
-- TOC entry 1750 (class 0 OID 0)
-- Dependencies: 1473
-- Name: COLUMN mvn_data."artifactId"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN mvn_data."artifactId" IS 'Name of the project';


--
-- TOC entry 1751 (class 0 OID 0)
-- Dependencies: 1473
-- Name: COLUMN mvn_data."artifactType"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN mvn_data."artifactType" IS 'Type of the artifact for example : 

java-source, jar, war';


--
-- TOC entry 1752 (class 0 OID 0)
-- Dependencies: 1473
-- Name: COLUMN mvn_data."artifactVersion"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN mvn_data."artifactVersion" IS 'Version of the artifact';


--
-- TOC entry 1753 (class 0 OID 0)
-- Dependencies: 1473
-- Name: COLUMN mvn_data.classifier; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN mvn_data.classifier IS 'Used to distinguish artifacts that were built from the same POM but with different content. Common use case is the classifiers sources and javadoc are used to deploy the project source code and API docs along with the packaged class files.';


--
-- TOC entry 1472 (class 1259 OID 19329)
-- Dependencies: 3 1473
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
-- TOC entry 1754 (class 0 OID 0)
-- Dependencies: 1472
-- Name: mvn_data_dependency_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE mvn_data_dependency_id_seq OWNED BY mvn_data.dependency_id;


--
-- TOC entry 1755 (class 0 OID 0)
-- Dependencies: 1472
-- Name: mvn_data_dependency_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('mvn_data_dependency_id_seq', 1, false);


--
-- TOC entry 1740 (class 2604 OID 19334)
-- Dependencies: 1473 1472 1473
-- Name: dependency_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE mvn_data ALTER COLUMN dependency_id SET DEFAULT nextval('mvn_data_dependency_id_seq'::regclass);


--
-- TOC entry 1744 (class 0 OID 19331)
-- Dependencies: 1473
-- Data for Name: mvn_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY mvn_data (dependency_id, raw_text, "groupId", "artifactId", "artifactType", "artifactVersion", scope, classifier) FROM stdin;
\.


--
-- TOC entry 1743 (class 2606 OID 19339)
-- Dependencies: 1473 1473
-- Name: mvn_data_prim_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mvn_data
    ADD CONSTRAINT mvn_data_prim_key PRIMARY KEY (dependency_id);


-- Completed on 2009-10-02 17:57:30

--
-- PostgreSQL database dump complete
--

