(ns whereabts-notify.likes-test
	(:use 
		midje.sweet
		whereabts-notify.likes
		whereabts-notify.gcm
		whereabts-notify.db.users))

(def like-details {:user-id "asd123" :message-owner-id "qwe123" :message-id "zxc123"})

(fact "should notify user on new like, when not liking own message"
	(notify-message-owner-on-like like-details) => anything
	(provided
		(liking-own-message? like-details) => false :times 1
		(build-like-gcm-message like-details) => anything :times 1
		(send-gcm-message anything) => anything :times 1))

(fact "should not be liking own message when user-id and message-owner-id are not the same"
	(liking-own-message? like-details) => false)

(def like-details-same-user (assoc-in like-details [:message-owner-id] "asd123"))
(fact "should be liking own message when user-id and message-owner-id are the same"
	(liking-own-message? like-details-same-user) => true)

(def built-like-gcm-message {
	:registration_ids ["user-gcm-id"]
	:data {
		:type like-message-type
		:message-id (:message-id like-details)
		:nick "jamppa"
		}
	})

(fact "should build like gcm message from details"
	(build-like-gcm-message like-details) => built-like-gcm-message
	(provided 
		(find-profile-by-user-id "asd123") => {:nick "jamppa"} :times 1
		(find-user-gcm-id "qwe123") => "user-gcm-id" :times 1))