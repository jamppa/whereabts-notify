(ns whereabts-notify.handlers-test
	(:use 
		[midje.sweet]
		[whereabts-notify.handlers]
		[whereabts-notify.replies]))

(def reply-channel-msg ["message" "replies" {}])
(def reply-channel-msg-with-nil ["type" "channel"])

(fact "should define message handlers so that it includes handler for replies"
	message-handlers => {"replies" reply-handler})

(fact "should notify user on new reply in reply handler"
	(reply-handler reply-channel-msg) => reply-channel-msg
	(provided (notify-user-on-reply {}) => anything :times 1))

(fact "should not notify user on new reply if message is nil"
	(reply-handler reply-channel-msg-with-nil) => reply-channel-msg-with-nil
	(provided (notify-user-on-reply nil) => anything :times 0))