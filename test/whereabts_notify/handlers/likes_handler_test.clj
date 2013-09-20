(ns whereabts-notify.handlers.likes-handler-test
	(:use 
		midje.sweet
		whereabts-notify.handlers.likes-handler
		whereabts-notify.likes))

(def like-details {:user-id "asd123" :message-owner-id "qwe123" :message-id "asd123"})
(def likes-channel-msg ["message" "message.likes" like-details])
(def likes-channel-msg-invalid ["message" "message.likes" {:some "thing"}])

(fact "should notify message owner on new like when channel message is valid"
	(likes-handler likes-channel-msg) => anything
	(provided
		(notify-message-owner-on-like like-details) => like-details :times 1))

(fact "should not notify message owner on new like when channel message is not valid"
	(likes-handler likes-channel-msg-invalid) => anything
	(provided
		(notify-message-owner-on-like anything) => anything :times 0))