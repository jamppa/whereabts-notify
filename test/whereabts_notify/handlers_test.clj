(ns whereabts-notify.handlers-test
	(:use 
		[midje.sweet]
		[whereabts-notify.handlers]
		[whereabts-notify.replies]))

(def reply-details {:user-id "123" :message-id "abc" :reply-id "456"})
(def reply-channel-msg ["message" "message.replies" reply-details])
(def reply-channel-msg-invalid ["message" "message.replies" {}])
(def reply-channel-msg-with-nil ["type" "channel"])

(fact "should define message handlers so that it includes handler for replies"
	message-handlers => {"message.replies" reply-handler})

(fact "should notify message owner and repliers when details are valid"
	(reply-handler reply-channel-msg) => reply-channel-msg
	(provided 
		(notify-message-owner reply-details) => anything :times 1
		(notify-message-repliers reply-details) => anything :times 1))

(fact "should not notify message owner and repliers when details are not valid"
	(reply-handler reply-channel-msg-invalid) => reply-channel-msg-invalid
	(provided 
		(notify-message-owner {}) => anything :times 0
		(notify-message-repliers {}) => anything :times 0))

(fact "should not notify message owner and repliers when message is nil"
	(reply-handler reply-channel-msg-with-nil) => reply-channel-msg-with-nil
	(provided 
		(notify-message-owner nil) => anything :times 0
		(notify-message-repliers nil) => anything :times 0))