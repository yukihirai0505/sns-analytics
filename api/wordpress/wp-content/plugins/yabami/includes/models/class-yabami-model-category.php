<?php

/**
 * Fired during plugin activation.
 *
 * This class defines all code necessary to run during the plugin's activation.
 *
 * @since      1.0.0
 * @package    Yabami
 * @subpackage Yabami/includes/models
 * @author     Yabaiwebyasan <yabaiwebyasan@gmail.com>
 */
class Yabami_Model_Category extends Yabami_Model {

	public function __construct() {
		$this->table_name = 'category';
		$this->columns    = array(
			'`category`.`id`',
			'`category`.`name`'
		);
	}

	function get_all() {
		global $wpdb;

		return $wpdb->get_results( "SELECT * FROM category" );
	}
}
