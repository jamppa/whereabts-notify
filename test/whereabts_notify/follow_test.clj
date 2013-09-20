(ns whereabts-notify.follow-test
	(:use
		midje.sweet
		whereabts-notify.follow
		whereabts-notify.gcm
		whereabts-notify.db.users))

(def follow-details {:follower-id "asdasd" :following-id "qweqwe"})

(fact "should build gcm message and notify user on new follower"
	(notify-user-on-new-follower follow-details) => anything
	(provided
		(build-follow-gcm-message follow-details) => anything :times 1
		(send-gcm-message anything) => anything :times 1))

(def built-follow-gcm-message {
	:registration_ids ["QWEasd"]
	:data {
			:type follow-message-type
			:follower-id "asdasd"
			:follower-nick "testman"
		}
	})

(fact "should build gcm message from details"
	(build-follow-gcm-message follow-details) => anything
	(provided
		(find-profile-by-user-id "asdasd") => anything :times 1
		(find-user-gcm-id "qweqwe") => anything :times 1
		(follow-gcm-data anything) => anything :times 1
		(gcm-message anything anything) => anything :times 1))