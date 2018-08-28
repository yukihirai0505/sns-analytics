<?php

use Abraham\TwitterOAuth\TwitterOAuth;

class Yabami_Util_Twitter {

	public static function get_timeline($access_token, $access_token_secret) {
		$connection = new TwitterOAuth(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET, $access_token, $access_token_secret);
		// check whether the account is valid or not
		$content = $connection->get("account/verify_credentials");
		return $connection->get("statuses/home_timeline", ["count" => 25, "exclude_replies" => true]);
	}
}