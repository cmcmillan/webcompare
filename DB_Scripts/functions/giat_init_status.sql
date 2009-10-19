-- Function: giat_init_status(text, text)

-- DROP FUNCTION giat_init_status(text, text);

CREATE OR REPLACE FUNCTION giat_init_status(statusval text, eta text DEFAULT ''::text)
  RETURNS integer AS
$BODY$
DECLARE status_exists boolean;
BEGIN
	IF statusval IS NULL OR statusval='' THEN
		RETURN -1;
	END IF;
	PERFORM statusval FROM giat_status WHERE status = statusval;
	IF FOUND THEN
		IF eta <> '' THEN
			UPDATE giat_status
				SET est_time=eta
			WHERE status = statusval;
		END IF;
	ELSE
		INSERT INTO giat_status(status, est_time) VALUES(statusval,eta);
	END IF;
	RETURN status_code FROM giat_status WHERE status = statusval;
END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION giat_init_status(text, text) OWNER TO geodb;
GRANT EXECUTE ON FUNCTION giat_init_status(text, text) TO geodb;
GRANT EXECUTE ON FUNCTION giat_init_status(text, text) TO postgis_users;
COMMENT ON FUNCTION giat_init_status(text, text) IS 'Add the status and eta to the giat_status table if it does not already exist. If status already exists, then updates eta. Return the statusID for _status.';

select giat_init_status('OK','0 weeks');
select giat_init_status('Need SWAP - Like','2-3 weeks');
select giat_init_status('Need SWAP','4-8 weeks');
select giat_init_status('Need SWAP - NO','8-12 weeks');
select giat_init_status('IAVA','Cannot install');