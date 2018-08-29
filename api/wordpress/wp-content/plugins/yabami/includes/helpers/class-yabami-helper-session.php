<?php

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
class Yabami_Helper_Session {

	private $session_name = 'yabami_auth';
	private $session_variable_name;

	public function __construct( $session_variable_name ) {
		if ( ENV !== 'dev' ) {
			ini_set( 'session.cookie_httponly', 1 );
			ini_set( 'session.cookie_secure', 1 );
		}
		ini_set( 'session.cookie_lifetime', 86400 * 30 );
		@session_name( $this->session_name );
		@session_start();
		$this->session_variable_name = $session_variable_name;
	}

	public function get() {
		return $_SESSION[ $this->session_variable_name ];
	}

	public function set( $data, $with_new_id = false ) {
		if ( $with_new_id ) {
			session_regenerate_id( true );
		}
		$_SESSION[ $this->session_variable_name ] = $data;
	}

	public function clear() {
		$_SESSION = array();
		if ( isset( $_COOKIE[ $this->session_variable_name ] ) ) {
			setcookie( $this->session_variable_name, '', time() - 3600, '/' );
		}
		@session_destroy();
	}
}
