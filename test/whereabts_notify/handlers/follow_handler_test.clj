(ns whereabts-notify.handlers.follow-handler-test
	(:use
		whereabts-notify.handlers.follow-handler
		whereabts-notify.follow
		midje.sweet))

(def follow-details {:follower-id "asdasd" :following-id "asdasd"})
(def follow-channel-message ["message" "users.follow" follow-details])
(def follow-channel-message-invalid ["message" "users.follow" {:invalid ""}])

(fact "should notify user on new follower when channel message is valid"
	(follow-handler follow-channel-message) => anything
	(provided
		(notify-user-on-new-follower follow-details) => anything :times 1))

(fact "should not notify user on new follower when channel message is invalid"
	(follow-handler follow-channel-message-invalid) => anything
	(provided
		(notify-user-on-new-follower {:invalid ""}) => anything :times 0))