--
-- PostgreSQL database dump
--

-- Started on 2009-10-13 11:20:16

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1487 (class 1259 OID 19350)
-- Dependencies: 1763 3
-- Name: mvn_data; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE mvn_data (
    dependency_id integer NOT NULL,
    raw_text character varying,
    group_id character varying,
    artifact_id character varying,
    artifact_type character varying,
    artifact_version character varying,
    scope character varying,
    classifier character varying DEFAULT ''::character varying
);


ALTER TABLE public.mvn_data OWNER TO postgres;

--
-- TOC entry 1769 (class 0 OID 0)
-- Dependencies: 1487
-- Name: TABLE mvn_data; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE mvn_data IS 'Raw MVN dependency data';


--
-- TOC entry 1770 (class 0 OID 0)
-- Dependencies: 1487
-- Name: COLUMN mvn_data.raw_text; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN mvn_data.raw_text IS 'The raw text supplied to the parser.';


--
-- TOC entry 1771 (class 0 OID 0)
-- Dependencies: 1487
-- Name: COLUMN mvn_data.group_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN mvn_data.group_id IS 'Usually the package containing the artifact';


--
-- TOC entry 1772 (class 0 OID 0)
-- Dependencies: 1487
-- Name: COLUMN mvn_data.artifact_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN mvn_data.artifact_id IS 'Name of the project';


--
-- TOC entry 1773 (class 0 OID 0)
-- Dependencies: 1487
-- Name: COLUMN mvn_data.artifact_type; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN mvn_data.artifact_type IS 'Type of the artifact for example : 

java-source, jar, war';


--
-- TOC entry 1774 (class 0 OID 0)
-- Dependencies: 1487
-- Name: COLUMN mvn_data.artifact_version; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN mvn_data.artifact_version IS 'Version of the artifact';


--
-- TOC entry 1775 (class 0 OID 0)
-- Dependencies: 1487
-- Name: COLUMN mvn_data.classifier; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN mvn_data.classifier IS 'Used to distinguish artifacts that were built from the same POM but with different content. Common use case is the classifiers sources and javadoc are used to deploy the project source code and API docs along with the packaged class files.';


--
-- TOC entry 1488 (class 1259 OID 19357)
-- Dependencies: 1487 3
-- Name: mvn_data_dependency_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE mvn_data_dependency_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.mvn_data_dependency_id_seq OWNER TO postgres;

--
-- TOC entry 1776 (class 0 OID 0)
-- Dependencies: 1488
-- Name: mvn_data_dependency_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE mvn_data_dependency_id_seq OWNED BY mvn_data.dependency_id;


--
-- TOC entry 1764 (class 2604 OID 19359)
-- Dependencies: 1488 1487
-- Name: dependency_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE mvn_data ALTER COLUMN dependency_id SET DEFAULT nextval('mvn_data_dependency_id_seq'::regclass);


--
-- TOC entry 1766 (class 2606 OID 19361)
-- Dependencies: 1487 1487
-- Name: mvn_data_prim_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mvn_data
    ADD CONSTRAINT mvn_data_prim_key PRIMARY KEY (dependency_id);


-- Completed on 2009-10-13 11:20:16

--
-- PostgreSQL database dump complete
--

