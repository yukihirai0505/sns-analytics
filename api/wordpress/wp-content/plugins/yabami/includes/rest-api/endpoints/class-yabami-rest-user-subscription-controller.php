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
class Yabami_Rest_User_Subscription_Controller extends Yabami_Rest_Controller {
	public function __construct() {
		$this->rest_base = 'user_subscription';
	}

	public function register_endpoints() {

		register_rest_route( $this->namespace, '/' . $this->rest_base, array(
			array(
				'methods'  => WP_REST_Server::READABLE,
				'callback' => array( $this, 'get' )
			)
		) );

		register_rest_route( $this->namespace, '/' . $this->rest_base . '/save', array(
			array(
				'methods'  => WP_REST_Server::CREATABLE,
				'callback' => array( $this, 'create' )
			)
		) );
	}

	public function get( WP_REST_Request $data ) {
		$uid   = $this->get_sign_in_user_token()->uid;
		$model = new Yabami_Model_User_Subscription();

		return self::ok( $model->get_by_uid( $uid ) );
	}

	public function create( WP_REST_Request $data ) {
		$params             = $data->get_params();
		$uid                = $this->get_sign_in_user_token()->uid;
		$twitter_account_id = $params['twitterAccountId'];
		$model              = new Yabami_Model_User_Subscription();

		return self::ok( $model->save( $uid, $twitter_account_id ) );
	}
}
