(ns whereabts-notify.handlers-test
	(:use 
		[midje.sweet]
		[whereabts-notify.handlers]
		[whereabts-notify.replies]))

(def reply-details {:user-to-notify "123" :replied-message "abc"})
(def reply-channel-msg ["message" "message.replies" reply-details])
(def reply-channel-msg-invalid ["message" "message.replies" {}])
(def reply-channel-msg-with-nil ["type" "channel"])

(fact "should define message handlers so that it includes handler for replies"
	message-handlers => {"message.replies" reply-handler})

(fact "should notify user on new reply in reply handler when details are valid"
	(reply-handler reply-channel-msg) => reply-channel-msg
	(provided (notify-user-on-reply reply-details) => anything :times 1))

(fact "should not notify user on new reply when details are not valid"
	(reply-handler reply-channel-msg-invalid) => reply-channel-msg-invalid
	(provided (notify-user-on-reply {}) => anything :times 0))

(fact "should not notify user on new reply if message is nil"
	(reply-handler reply-channel-msg-with-nil) => reply-channel-msg-with-nil
	(provided (notify-user-on-reply nil) => anything :times 0))