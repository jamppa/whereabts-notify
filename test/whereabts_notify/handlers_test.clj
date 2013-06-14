(ns whereabts-notify.handlers-test
	(:use 
		[midje.sweet]
		[whereabts-notify.handlers]))

(def notify-user-on-reply-message [])

(fact "should define message handlers so that it includes handler for replies"
	message-handlers => {"replies" reply-handler})

; (fact "should call user reply notifier with notify message in handler"
; 	(notify-user-on-reply-handler notify-user-on-reply-message) => notify-user-on-reply-message
; 	(provided (notify-user-on-reply) => anything :times 1))