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
class Yabami_Rest_Category_Controller extends Yabami_Rest_Controller {
	public function __construct() {
		$this->rest_base = 'category';
	}

	public function register_endpoints() {

		register_rest_route( $this->namespace, '/' . $this->rest_base, array(
			array(
				'methods'  => WP_REST_Server::READABLE,
				'callback' => array( $this, 'all' )
			)
		) );

	}

	public function all() {
		$category_model = new Yabami_Model_Category();
		return self::ok($category_model->get_all());
	}
}
