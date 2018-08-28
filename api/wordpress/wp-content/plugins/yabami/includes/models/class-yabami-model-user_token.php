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
class Yabami_Model_User_Token extends Yabami_Model {

	public function __construct() {
		$this->table_name = 'user_token';
		$this->columns    = array(
			'`user_token`.`uid`',
			'`user_token`.`twitter_access_token`',
		);
	}

	function save( $uid, $twitter_access_token ) {
		global $wpdb;


		return $wpdb->insert( 'user_token', array(
			'name'      => $uid,
			'free_text' => $twitter_access_token
		) );

	}
}
