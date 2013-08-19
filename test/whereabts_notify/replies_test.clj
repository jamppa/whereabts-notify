(ns whereabts-notify.replies-test
	(:use 
		[midje.sweet]
		[whereabts-notify.replies]
		[whereabts-notify.db.users]
		[whereabts-notify.db.replies]
		[whereabts-notify.gcm]))

(def reply {:replymessage "cool!" :user-profile {:nick "jami"} :user_id "321"})
(def reply-details {:message-id "012" :user-id "123" :reply-id "567"})
(def reply-gcm-message {
		:registration_ids ["abc"] 
		:data {
			:type "TYPE_MESSAGE_REPLY" 
			:message-id (:message-id reply-details)
			:reply-nick "jami"
			:reply-message "cool!"}})

(fact "should find reply with userprofile"
	(find-reply-with-user "123") => reply
	(provided 
		(find-reply "123") => reply :times 1
		(with-profile reply) => reply :times 1))

(fact "should notify message owner on new reply"
	(notify-message-owner reply-details) => anything
	(provided
		(find-reply-with-user "567") => reply :times 1
		(find-user-gcm-id "123") => "abc" :times 1
		(notify-message-owner-on-reply reply "abc" "012") => anything :times 1))

(fact "should not notify message owner on new reply if replying to own message"
	(notify-message-owner (merge reply-details {:user-id "321"})) => anything
	(provided
		(find-reply-with-user "567") => reply :times 1
		(find-user-gcm-id "321") => "abc" :times 1
		(notify-message-owner-on-reply reply "abc" "012") => anything :times 0))

