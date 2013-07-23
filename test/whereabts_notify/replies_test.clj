(ns whereabts-notify.replies-test
	(:use 
		[midje.sweet]
		[whereabts-notify.replies]
		[whereabts-notify.db.users]
		[whereabts-notify.db.replies]
		[whereabts-notify.gcm]))

(def reply {:replymessage "cool!" :user-profile {:nick "jami"}})
(def reply-details {:message-id "abc" :user-id "123" :reply-id "567"})
(def reply-gcm-message {
		:registration_ids ["abc"] 
		:data {
			:type "TYPE_MESSAGE_REPLY" 
			:message-id (:message-id reply-details)
			:reply-nick "jami"
			:reply-message "cool!"}})

(fact "should find reply with userprofile"
	(find-reply-with-user "123") => reply
	(provided (find-reply "123") => reply :times 1)
	(provided (with-profile reply) => reply :times 1))

(fact "should notify user on reply by sending gcm message"
	(notify-user-on-reply reply-details) => anything
	(provided (find-user-gcm-id "123") => "abc" :times 1)
	(provided (find-reply-with-user "567") => reply :times 1)
	(provided (send-gcm-message reply-gcm-message) => anything :times 1))

(fact "should not notify user on reply by sending gcm message when gcm id is not found for the user"
	(notify-user-on-reply reply-details) => anything
	(provided (find-user-gcm-id "123") => nil :times 1)
	(provided (send-gcm-message anything) => anything :times 0))