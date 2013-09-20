(ns whereabts-notify.follow-test
	(:use
		midje.sweet
		whereabts-notify.follow
		whereabts-notify.gcm
		whereabts-notify.db.users
		whereabts-notify.db.users-test-fixtures))

(def follow-details {:follower-id "asdasd" :following-id "qweqwe"})

(fact "should build gcm message and notify user on new follower"
	(notify-user-on-new-follower follow-details) => anything
	(provided
		(build-follow-gcm-message follow-details) => anything :times 1
		(send-gcm-message anything) => anything :times 1))

(def built-follow-gcm-data {
	:type follow-message-type
	:follower-id (.toString (:user_id user-a-profile))
	:follower-nick (:nick user-a-profile)})

(fact "should build gcm message from details"
	(build-follow-gcm-message follow-details) => anything
	(provided
		(find-profile-by-user-id "asdasd") => anything :times 1
		(find-user-gcm-id "qweqwe") => "QWEasd" :times 1
		(follow-gcm-data anything) => anything :times 1
		(gcm-message ["QWEasd"] anything) => anything :times 1))

(fact "should build follow gcm data from followers profile"
	(follow-gcm-data user-a-profile) => built-follow-gcm-data)