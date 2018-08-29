<?php

use Abraham\TwitterOAuth\TwitterOAuth;

class Yabami_Util_Twitter {

	private $twitter;

	public function __construct( $user_token ) {
		$this->twitter = new TwitterOAuth(
			TWITTER_CONSUMER_KEY,
			TWITTER_CONSUMER_SECRET,
			$user_token->twitter_access_token,
			$user_token->twitter_access_token_secret
		);
	}

	function verify_credentials() {
		return $this->twitter->get( "account/verify_credentials" );
	}

	function get_timeline( $count = 25, $exclude_replies = true ) {
		return $this->twitter->get( "statuses/home_timeline", [
			"count"           => $count,
			"exclude_replies" => $exclude_replies
		] );
	}

	function search_user( $q, $count = 20, $include_entities = true ) {
		return $this->twitter->get( "users/search", [
			"q"                => $q,
			"count"            => $count,
			"include_entities" => $include_entities
		] );
	}
}