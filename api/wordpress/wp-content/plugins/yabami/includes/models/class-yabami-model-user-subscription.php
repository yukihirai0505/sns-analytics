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
class Yabami_Model_User_Subscription extends Yabami_Model {

	public function __construct() {
		$this->table_name = 'user_subscription';
		$this->columns    = array(
			'`user_subscription`.`uid`',
			'`user_subscription`.`twitter_account_id`'
		);
	}

	function get_by_uid( $uid ) {
		global $wpdb;

		return $wpdb->get_results( "SELECT * FROM user_subscription WHERE uid = \"${uid}\"" );
	}

	function save( $uid, $twitter_account_id ) {
		global $wpdb;

		return $wpdb->insert( $this->table_name, array(
			'uid'                => $uid,
			'twitter_account_id' => $twitter_account_id
		) );
	}
}
