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
class Yabami_Rest_User_Token_Controller extends Yabami_Rest_Controller {
	public function __construct() {
		$this->rest_base = 'user_token';
	}

	public function register_endpoints() {

		register_rest_route( $this->namespace, '/' . $this->rest_base . '/healthcheck', array(
			array(
				'methods'  => WP_REST_Server::READABLE,
				'callback' => array( $this, 'healthcheck' )
			)
		) );

		register_rest_route( $this->namespace, '/' . $this->rest_base, array(
			array(
				'methods'  => WP_REST_Server::CREATABLE,
				'callback' => array( $this, 'create' )
			)
		) );
	}

	public function healthcheck() {
		return self::ok();
	}

	public function create( WP_REST_Request $data ) {
		$params                      = $data->get_params();
		$uid                         = $params['uid'];
		$twitter_access_token        = $params['twitterAccessToken'];
		$twitter_access_token_secret = $params['twitterAccessTokenSecret'];
		$user_token                  = new Yabami_Model_User_Token();

		$result = $user_token->save( $uid, $twitter_access_token, $twitter_access_token_secret );
		if ( $result ) {
			$session = new Yabami_Helper_Session( Yabami_Constant_Session::U_ID );
			$session->set( $uid, true );
		}

		return self::ok( $result );
	}
}
