(ns whereabts-notify.follow-test
	(:use
		midje.sweet
		whereabts-notify.follow
		whereabts-notify.gcm))

(def follow-details {})

(fact "should build gcm message and notify user on new follower"
	(notify-user-on-new-follower follow-details) => anything
	(provided
		(build-follow-gcm-message follow-details) => anything :times 1
		(send-gcm-message anything) => anything :times 1))