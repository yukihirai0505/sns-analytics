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

		register_rest_route( $this->namespace, '/' . $this->rest_base . '/embed', array(
			array(
				'methods'  => WP_REST_Server::READABLE,
				'callback' => array( $this, 'embed' )
			)
		) );

		register_rest_route( $this->namespace, '/' . $this->rest_base . '/search_user', array(
			array(
				'methods'  => WP_REST_Server::READABLE,
				'callback' => array( $this, 'search_user' )
			)
		) );

		register_rest_route( $this->namespace, '/' . $this->rest_base . '/timeline', array(
			array(
				'methods'  => WP_REST_Server::READABLE,
				'callback' => array( $this, 'timeline' )
			)
		) );

		register_rest_route( $this->namespace, '/' . $this->rest_base . '/beneficial', array(
			array(
				'methods'  => WP_REST_Server::READABLE,
				'callback' => array( $this, 'beneficial_tweets' )
			)
		) );
	}

	public function embed( WP_REST_Request $data ) {
		$params = $data->get_params();
		$url    = urldecode( $params['url'] );
		$res    = json_decode( wp_remote_get( 'https://publish.twitter.com/oembed?url=' . $url )['body'] );

		return self::ok( $res->html );
	}

	public function search_user( WP_REST_Request $data ) {
		$params     = $data->get_params();
		$query      = urldecode( $params['q'] );
		$user_token = self::get_sign_in_user_token();
		$twitter    = $this->get_twitter_client( $user_token );

		return self::ok( $twitter->search_user( $query ) );
	}

	public function timeline( WP_REST_Request $data ) {
		$user_token = self::get_sign_in_user_token();
		$twitter    = $this->get_twitter_client( $user_token );

		return self::ok( $twitter->get_timeline() );
	}

	public function beneficial_tweets( WP_REST_Request $data ) {
		$user_token        = self::get_sign_in_user_token();
		$twitter           = $this->get_twitter_client( $user_token );
		$user_subscription = new Yabami_Model_User_Subscription();
		$tweets            = [];
		foreach ( $user_subscription->get_by_uid( $user_token->uid ) as $user_subscription ) {
			$tweets = array_merge(
				$tweets,
				$twitter->search_tweets( $user_subscription->twitter_account_id )->statuses
			);
		}

		return self::ok( $tweets );
	}

	private function get_twitter_client( $user_token ) {
		return new Yabami_Util_Twitter( $user_token );
	}

}
