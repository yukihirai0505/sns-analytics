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

	function get_user($twitter_account_id) {
		return $this->twitter->get('users/show', [
			'user_id' => $twitter_account_id
		]);
	}

	function search_tweets($twitter_account_id) {
		$screen_name = strtolower($this->get_user($twitter_account_id)->screen_name);
		$date = date("Y-m-d",strtotime("-1 week"));
		return $this->twitter->get('search/tweets', [
			//  OR min_faves:50
			'q' => "from:${screen_name} since:${date} min_retweets:25",
			'count' => 100
		]);
	}

	function get_user_timeline($twitter_account_id) {
		return $this->twitter->get('statuses/user_timeline', [
			'user_id' => $twitter_account_id,
			'count' => 200
		]);
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