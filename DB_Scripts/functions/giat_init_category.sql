-- Function: giat_init_category(text)

-- DROP FUNCTION giat_init_category(text);

CREATE OR REPLACE FUNCTION giat_init_category(cat text)
  RETURNS integer AS
$BODY$
BEGIN
	IF cat IS NULL or cat='' THEN
		RETURN -1;
	END IF;
	INSERT INTO giat_categories(category) 
		SELECT cat WHERE not exists(
			SELECT category FROM giat_categories WHERE category <> cat);
	RETURN category_id FROM giat_categories WHERE category = cat;
END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;
ALTER FUNCTION giat_init_category(text) OWNER TO geodb;
GRANT EXECUTE ON FUNCTION giat_init_category(text) TO geodb;
GRANT EXECUTE ON FUNCTION giat_init_category(text) TO postgis_users WITH GRANT OPTION;
COMMENT ON FUNCTION giat_init_category(text) IS 'Add the category to the giat_categories table if it does not already exist. Return the categoryID for cat.';
