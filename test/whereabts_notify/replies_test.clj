(ns whereabts-notify.replies-test
	(:use 
		[midje.sweet]
		[whereabts-notify.replies]
		[whereabts-notify.db.users]
		[whereabts-notify.db.replies]
		[whereabts-notify.gcm]))

(def reply {:nick "seppo" :replymessage "cool!"})
(def reply-details {:replied-message "abc" :user-to-notify "123" :reply "567"})
(def reply-gcm-message {
		:registration_ids ["abc"] 
		:data {
			:type "TYPE_MESSAGE_REPLY" 
			:message-id (:replied-message reply-details)
			:reply-nick "seppo"
			:reply-message "cool!"}})

(fact "should notify user on reply by sending gcm message"
	(notify-user-on-reply reply-details) => anything
	(provided (find-user-gcm-id "123") => "abc" :times 1)
	(provided (find-reply "567") => reply :times 1)
	(provided (send-gcm-message reply-gcm-message) => anything :times 1))

(fact "should not notify user on reply by sending gcm message when gcm id is not found for the user"
	(notify-user-on-reply reply-details) => anything
	(provided (find-user-gcm-id "123") => nil :times 1)
	(provided (send-gcm-message anything) => anything :times 0))