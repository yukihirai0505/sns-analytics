<?php

/**
 * Fired during plugin activation.
 *
 * This class defines all code necessary to run during the plugin's activation.
 *
 * @since      1.0.0
 * @package    Yabami
 * @subpackage Yabami/includes/rest-api
 * @author     Yabaiwebyasan <yabaiwebyasan@gmail.com>
 */
abstract class Yabami_Rest_Controller {
	protected $namespace = 'yabami/v1';
	protected $rest_base;

	static function ok( $data = 'ok' ) {
		$response = new WP_REST_Response();
		$response->set_status( 200 );
		$response->set_data( array(
			'data' => $data
		) );

		return $response;
	}

	static function bad( $data = 'bad' ) {
		$response = new WP_REST_Response();
		$response->set_status( 400 );
		$response->set_data( array(
			'data' => $data
		) );

		return $response;
	}

	function get_sign_in_user_token() {
		if ( ! function_exists( 'getallheaders' ) ) {
			function getallheaders() {
				$headers = [];
				foreach ( $_SERVER as $name => $value ) {
					if ( substr( $name, 0, 5 ) == 'HTTP_' ) {
						$headers[ str_replace( ' ', '-', ucwords( strtolower( str_replace( '_', ' ', substr( $name, 5 ) ) ) ) ) ] = $value;
					}
				}

				return $headers;
			}
		}
		$header           = getallheaders();
		$jwt              = str_replace( 'Bearer ', '', $header['Authorization'] );
		$uid              = json_decode( Yabami_Helper_JWT::decode( $jwt )->sub
		)->uid;
		$user_token_model = new Yabami_Model_User_Token();

		return $user_token_model->get_by_uid( $uid );
	}

}
