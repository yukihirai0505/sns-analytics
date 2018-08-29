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
			'`user_token`.`twitter_access_token_secret`',
		);
	}

	function get_by_uid( $uid ) {
		global $wpdb;

		return $wpdb->get_row( "SELECT * FROM user_token WHERE uid = \"${uid}\"" );
	}

	function get_all() {
		global $wpdb;

		return $wpdb->get_results( "SELECT * FROM user_token" );
	}

	function save( $uid, $twitter_access_token, $twitter_access_token_secret ) {
		global $wpdb;
		if ( ! $this->get_by_uid( $uid ) ) {
			return $wpdb->insert( 'user_token', array(
				'uid'                         => $uid,
				'twitter_access_token'        => $twitter_access_token,
				'twitter_access_token_secret' => $twitter_access_token_secret
			) );
		}

		return false;
	}
}
