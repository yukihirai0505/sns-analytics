<?php

/**
 * Fired during plugin activation.
 *
 * This class defines all code necessary to run during the plugin's activation.
 *
 * @since      1.0.0
 * @package    Yabami
 * @subpackage Yabami/includes/rest-api/endpoints
 * @author     Yabaiwebyasan <yabaiwebyasan@gmail.com>
 */
class Yabami_Rest_Twitter_Controller extends Yabami_Rest_Controller {
	public function __construct() {
		$this->rest_base = 'twitter';
	}

	public function register_endpoints() {

		register_rest_route( $this->namespace, '/' . $this->rest_base . '/timeline', array(
			array(
				'methods'  => WP_REST_Server::READABLE,
				'callback' => array( $this, 'timeline' )
			)
		) );

	}

	public function timeline() {
		$user_token_model = new Yabami_Model_User_Token();
		$user_token       = $user_token_model->get_all()[0];

		return Yabami_Util_Twitter::get_timeline( $user_token->twitter_access_token, $user_token->twitter_access_token_secret );
	}
}
