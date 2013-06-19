(ns whereabts-notify.replies-test
	(:use 
		[midje.sweet]
		[whereabts-notify.replies]
		[whereabts-notify.db.users]
		[whereabts-notify.gcm]))

(def user-to-notify {:gcm-id "abc"})
(def reply-details {:replied-message "abc" :user-to-notify "123"})
(def reply-gcm-message {
		:registration_ids [(:gcm-id user-to-notify)] 
		:data {:type 0 :message-id (:replied-message reply-details)}})

(fact "should notify user on reply by sending gcm message"
	(notify-user-on-reply reply-details) => anything
	(provided (find-user-gcm-id "123") => "abc" :times 1)
	(provided (send-gcm-message reply-gcm-message) => anything :times 1))