
/**
 * Insert the cat if it doesn't already exist in the giat_categories table
 * Return the category_id
 */
CREATE OR REPLACE FUNCTION giat_init_category(cat TEXT) RETURNS integer AS
$$
BEGIN
	INSERT INTO giat_categories(category) 
		SELECT cat WHERE not exists(
			SELECT category FROM giat_categories WHERE category <> cat);
	RETURN category_id FROM giat_categories WHERE category = cat;
END;
$$LANGUAGE 'plpgsql';


/**
 * Insert the statusVal if it doesn't already exist in the giat_status table
 * Return the status_code
 */
CREATE OR REPLACE FUNCTION giat_init_status(statusVal TEXT) RETURNS integer AS
$$
BEGIN
	INSERT INTO giat_status(status) 
		SELECT statusVal WHERE not exists(
			SELECT status FROM giat_status WHERE status <> statusVal);
	RETURN status_code FROM giat_categories WHERE status = statusVal;
END;
$$LANGUAGE 'plpgsql';



/**
 * Insert the statusVal and eta if it doesn't already exist in the giat_status table
 * If statusVal already exists then update the eta in the giat_status table
 * Return the status_code
 */
CREATE OR REPLACE FUNCTION giat_init_status(statusVal TEXT, eta TEXT) RETURNS integer AS
$$
DECLARE status_exists boolean;
BEGIN
	
	PERFORM statusVal FROM giat_status WHERE status = statusVal;
	IF FOUND THEN
		UPDATE giat_status
			SET est_time=eta
		WHERE status = statusVal
	ELSE
		INSERT INTO giat_status(status, est_time) VALUES(statusVal,eta);
		--SELECT statusVal, eta WHERE not exists(
		--	SELECT status FROM giat_status WHERE status <> statusVal);
	END IF;
	RETURN status_code FROM giat_categories WHERE status = statusVal;
END;
$$LANGUAGE 'plpgsql';

/**
 * Insert the required tool if artifactNameVal and artifactVersionVal are not already in the database
 * Return the rid
 */
CREATE OR REPLACE FUNCTION giat_init_required_tools(artifactNameVal as TEXT, artifactVersionVal as TEXT, websiteVal as TEXT, statusCodeVal as INTEGER, categoryIdVal as INTEGER) RETURNS integer AS
$$
DECLARE status_exists boolean;
BEGIN
	
	INSERT INTO giat_required_tools(artifact_name, artifact_version, website, status_code, category_id)
		VALUES (artifactNameVal, artifactVersion, websiteVal, statusCodeVal, categoryIdVal)
		SELECT statusVal WHERE not exists(
			artifact_name, artifact_version FROM required_tools WHERE artifact_name <> artifactNameVal AND artifact_version <> artifactVersionVal);
	RETURN rid FROM giat_required_tools WHERE artifact_name = artifactNameVal AND artifact_version = artifactVersionVal;
END;
$$LANGUAGE 'plpgsql';

/**
 * Insert the required tool if artifactNameVal and artifactVersionVal are not already in the database
 * Return the rid
 */
CREATE OR REPLACE FUNCTION giat_init_required_tools(priorityVal as INTEGER, artifactNameVal as TEXT, artifactVersionVal as TEXT, websiteVal as TEXT, statusCodeVal as INTEGER, categoryIdVal as INTEGER) RETURNS integer AS
$$
DECLARE status_exists boolean;
BEGIN
	
	INSERT INTO giat_required_tools(priority, artifact_name, artifact_version, website, status_code, category_id)
		VALUES (priorityVal, artifactNameVal, artifactVersion, websiteVal, statusCodeVal, categoryIdVal)
		SELECT statusVal WHERE not exists(
			artifact_name, artifact_version FROM required_tools WHERE artifact_name <> artifactNameVal AND artifact_version <> artifactVersionVal);
	RETURN rid FROM giat_required_tools WHERE artifact_name = artifactNameVal AND artifact_version = artifactVersionVal;
END;
$$LANGUAGE 'plpgsql';