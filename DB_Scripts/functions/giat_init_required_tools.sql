-- Function: giat_init_required_tools(text, text, text, integer, integer, integer)

-- DROP FUNCTION giat_init_required_tools(text, text, text, integer, integer, integer);

CREATE OR REPLACE FUNCTION giat_init_required_tools(artifactnameval text, artifactversionval text, websiteval text, statuscodeval integer, categoryidval integer, priorityval integer DEFAULT (-9999))
  RETURNS integer AS
$BODY$
DECLARE status_exists boolean;
BEGIN
	IF artifactNameval IS NULL OR artifactNameval='' THEN
		RETURN -1;
	END IF;
	INSERT INTO giat_required_tools(priority,artifact_name, artifact_version, website, status_code, category_id)
		SELECT priorityval, artifactNameval, artifactVersionval, websiteval, statusCodeval, categoryIdval 
			WHERE not exists(SELECT * FROM required_tools 
				WHERE artifact_name = artifactNameval AND artifact_version = artifactVersionval);
	RETURN rid FROM giat_required_tools WHERE artifact_name = artifactNameval AND artifact_version = artifactVersionval;
END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION giat_init_required_tools(text, text, text, integer, integer, integer) OWNER TO geodb;
GRANT EXECUTE ON FUNCTION giat_init_required_tools(text, text, text, integer, integer, integer) TO geodb;
GRANT EXECUTE ON FUNCTION giat_init_required_tools(text, text, text, integer, integer, integer) TO postgis_users;
COMMENT ON FUNCTION giat_init_required_tools(text, text, text, integer, integer, integer) IS 'Add the required tool to giat_required_tools if needed. Return rid.';
