<?php

use Firebase\JWT\JWT;

/**
 * Fired during plugin activation.
 *
 * This class defines all code necessary to run during the plugin's activation.
 *
 * @since      1.0.0
 * @package    Yabami
 * @subpackage Yabami/includes/helpers
 * @author     Yabaiwebyasan <yabaiwebyasan@gmail.com>
 */
class Yabami_Helper_JWT {
	public static function encode( $params ) {
		return JWT::encode( array( 'sub' => json_encode( $params ) ), JWT_AUTH_SECRET_KEY );
	}

	public static function decode( $jwt ) {
		return JWT::decode( $jwt, JWT_AUTH_SECRET_KEY, array( 'HS256' ) );
	}
}
